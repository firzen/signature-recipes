package main

import (
	"crypto/rand"
	"crypto/rsa"
	"crypto/sha256"
	"crypto/x509"
	"encoding/base64"
	"encoding/json"
	"encoding/pem"
	"fmt"
	"time"
)

func base64urlEncode(data []byte) string {
	return base64.RawURLEncoding.EncodeToString(data)
}

func generateJWT(privateKeyPem string, iss, aud string, expiresIn int64) (string, error) {
	block, _ := pem.Decode([]byte(privateKeyPem))
	if block == nil {
		return "", fmt.Errorf("failed to decode PEM block")
	}

	privateKey, err := x509.ParsePKCS8PrivateKey(block.Bytes)
	if err != nil {
		return "", err
	}

	rsaKey, ok := privateKey.(*rsa.PrivateKey)
	if !ok {
		return "", fmt.Errorf("private key is not RSA")
	}

	header := map[string]string{
		"alg": "RS256",
		"typ": "JWT",
	}
	headerBytes, _ := json.Marshal(header)
	encodedHeader := base64urlEncode(headerBytes)

	now := time.Now().Unix()
	payload := map[string]interface{}{
		"iss": iss,
		"sub": iss,
		"aud": aud,
		"exp": now + expiresIn,
		"iat": now,
	}
	payloadBytes, _ := json.Marshal(payload)
	encodedPayload := base64urlEncode(payloadBytes)

	dataToSign := encodedHeader + "." + encodedPayload

	hash := sha256.Sum256([]byte(dataToSign))
	signature, err := rsa.SignPKCS1v15(rand.Reader, rsaKey, crypto.SHA256, hash[:])
	if err != nil {
		return "", err
	}

	encodedSignature := base64urlEncode(signature)

	return encodedHeader + "." + encodedPayload + "." + encodedSignature, nil
}

func main() {
	privateKey := `-----BEGIN PRIVATE KEY-----
your_private_key_here
-----END PRIVATE KEY-----`
	issuer := "your-service-account@your-project.iam.gserviceaccount.com"
	audience := "https://aiplatform.googleapis.com/"

	jwt, err := generateJWT(privateKey, issuer, audience, 3600)
	if err != nil {
		fmt.Println("Error generating JWT:", err)
		return
	}

	fmt.Println("Generated JWT:")
	fmt.Println(jwt)
	fmt.Println()
	fmt.Println("Use this JWT in Authorization header:")
	fmt.Println("Authorization: Bearer", jwt)
}