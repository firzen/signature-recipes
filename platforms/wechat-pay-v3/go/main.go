package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"math/rand"
	"strconv"
	"time"
)

func generateNonceStr() string {
	chars := "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
	b := make([]byte, 32)
	r := rand.New(rand.NewSource(time.Now().UnixNano()))
	for i := range b {
		b[i] = chars[r.Intn(len(chars))]
	}
	return string(b)
}

func buildStringToSign(method, uri, timestamp, nonce, body string) string {
	return fmt.Sprintf("%s\n%s\n%s\n%s\n%s\n", method, uri, timestamp, nonce, body)
}

func generateSignature(method, uri, timestamp, nonce, body, apiV3Key string) string {
	stringToSign := buildStringToSign(method, uri, timestamp, nonce, body)
	h := hmac.New(sha256.New, []byte(apiV3Key))
	h.Write([]byte(stringToSign))
	return hex.EncodeToString(h.Sum(nil))
}

func buildAuthorizationHeader(mchid, apiV3Key, serialNo, method, uri, body string) string {
	timestamp := strconv.FormatInt(time.Now().Unix(), 10)
	nonce := generateNonceStr()
	signature := generateSignature(method, uri, timestamp, nonce, body, apiV3Key)

	return fmt.Sprintf(
		`WECHATPAY2-SHA256-RSA2048 mchid="%s",nonce_str="%s",signature="%s",timestamp="%s",serial_no="%s"`,
		mchid, nonce, signature, timestamp, serialNo,
	)
}

func main() {
	mchid := "1234567890"
	apiV3Key := "your_api_v3_key"
	serialNo := "your_certificate_serial_number"
	method := "POST"
	uri := "/v3/pay/transactions/app"
	body := `{"mchid":"1234567890","out_trade_no":"202401010001","amount":{"total":100},"description":"Test"}`

	authorization := buildAuthorizationHeader(mchid, apiV3Key, serialNo, method, uri, body)

	fmt.Println("Authorization Header:")
	fmt.Println(authorization)
}