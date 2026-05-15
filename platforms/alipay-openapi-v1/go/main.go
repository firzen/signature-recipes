package main

import (
	"crypto/rand"
	"crypto/rsa"
	"crypto/sha256"
	"crypto/x509"
	"encoding/base64"
	"encoding/pem"
	"fmt"
	"sort"
	"time"
)

func generateSignature(params map[string]string, privateKeyPEM string) (string, error) {
	keys := make([]string, 0, len(params))
	for k := range params {
		if k != "sign" {
			keys = append(keys, k)
		}
	}
	sort.Strings(keys)

	var signString string
	for i, k := range keys {
		v := params[k]
		if v != "" {
			if i > 0 {
				signString += "&"
			}
			signString += k + "=" + v
		}
	}

	block, _ := pem.Decode([]byte(privateKeyPEM))
	if block == nil {
		return "", fmt.Errorf("failed to decode PEM block")
	}

	privateKey, err := x509.ParsePKCS8PrivateKey(block.Bytes)
	if err != nil {
		return "", err
	}

	rsaKey, ok := privateKey.(*rsa.PrivateKey)
	if !ok {
		return "", fmt.Errorf("not an RSA private key")
	}

	hash := sha256.Sum256([]byte(signString))
	signature, err := rsa.SignPKCS1v15(rand.Reader, rsaKey, crypto.SHA256, hash[:])
	if err != nil {
		return "", err
	}

	return base64.StdEncoding.EncodeToString(signature), nil
}

func main() {
	privateKey := `-----BEGIN PRIVATE KEY-----
your_private_key_here
-----END PRIVATE KEY-----`

	params := map[string]string{
		"app_id":       "your_app_id",
		"method":       "alipay.trade.app.pay",
		"charset":      "UTF-8",
		"sign_type":    "RSA2",
		"timestamp":    time.Now().Format("2006-01-02 15:04:05"),
		"version":      "1.0",
		"biz_content": `{"out_trade_no":"202401010001","total_amount":"0.01","subject":"Test"}`,
	}

	signature, _ := generateSignature(params, privateKey)
	params["sign"] = signature

	fmt.Println("Request Parameters:")
	keys := make([]string, 0, len(params))
	for k := range params {
		keys = append(keys, k)
	}
	sort.Strings(keys)
	for _, k := range keys {
		fmt.Printf("  %s: %s\n", k, params[k])
	}
}