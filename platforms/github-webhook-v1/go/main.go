package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/hex"
	"fmt"
)

func hmacSha256(data, secret string) string {
	h := hmac.New(sha256.New, []byte(secret))
	h.Write([]byte(data))
	return "sha256=" + hex.EncodeToString(h.Sum(nil))
}

func verifyWebhookSignature(payload, signatureHeader, webhookSecret string) bool {
	expectedSignature := hmacSha256(payload, webhookSecret)
	return expectedSignature == signatureHeader
}

func main() {
	webhookSecret := "your_webhook_secret"
	payload := `{"action":"created","ref":"refs/heads/main"}`
	
	signatureHeader := hmacSha256(payload, webhookSecret)
	fmt.Println("Generated Signature Header:")
	fmt.Println(signatureHeader)
	fmt.Println()
	
	isValid := verifyWebhookSignature(payload, signatureHeader, webhookSecret)
	fmt.Println("Signature verification result:", map[bool]string{true: "VALID", false: "INVALID"}[isValid])
}