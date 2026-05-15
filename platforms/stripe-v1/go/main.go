package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"strconv"
	"strings"
	"time"
)

func createAuthorizationHeader(secretKey string) string {
	return "Bearer " + secretKey
}

func hmacSha256(data, secret string) string {
	h := hmac.New(sha256.New, []byte(secret))
	h.Write([]byte(data))
	return hex.EncodeToString(h.Sum(nil))
}

func verifyWebhookSignature(payload, signatureHeader, webhookSecret string) bool {
	var timestamp, signature string

	parts := strings.Split(signatureHeader, ",")
	for _, part := range parts {
		kv := strings.SplitN(part, "=", 2)
		if len(kv) == 2 {
			if kv[0] == "t" {
				timestamp = kv[1]
			} else if kv[0] == "v1" {
				signature = kv[1]
			}
		}
	}

	if timestamp == "" || signature == "" {
		return false
	}

	signedPayload := timestamp + "." + payload
	expectedSignature := hmacSha256(signedPayload, webhookSecret)

	return expectedSignature == signature
}

func main() {
	secretKey := "sk_test_your_secret_key"
	webhookSecret := "whsec_your_webhook_secret"

	fmt.Println("Authorization Header:", createAuthorizationHeader(secretKey))
	fmt.Println()

	payload := `{"id":"evt_123","object":"event"}`
	timestamp := strconv.FormatInt(time.Now().Unix(), 10)
	signedPayload := timestamp + "." + payload
	calculatedSignature := hmacSha256(signedPayload, webhookSecret)
	signatureHeader := "t=" + timestamp + ",v1=" + calculatedSignature

	fmt.Println("Generated Signature Header:")
	fmt.Println(signatureHeader)
	fmt.Println()

	isValid := verifyWebhookSignature(payload, signatureHeader, webhookSecret)
	fmt.Println("Signature verification result:", map[bool]string{true: "VALID", false: "INVALID"}[isValid])
}