package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/hex"
	"fmt"
)

func generateSignature(payload, webhookSecret string) string {
	h := hmac.New(sha256.New, []byte(webhookSecret))
	h.Write([]byte(payload))
	return hex.EncodeToString(h.Sum(nil))
}

func verifyWebhookSignature(payload, signatureHeader, webhookSecret string) bool {
	expectedSignature := generateSignature(payload, webhookSecret)
	return expectedSignature == signatureHeader
}

func main() {
	webhookSecret := "your_replicate_webhook_secret"
	payload := `{"id":"xxx","version":"xxx","status":"succeeded"}`
	signature := generateSignature(payload, webhookSecret)

	fmt.Println("Generated Signature (X-Replicate-Signature):")
	fmt.Println(signature)
	fmt.Println()

	isValid := verifyWebhookSignature(payload, signature, webhookSecret)
	fmt.Println("Signature verification result:", map[bool]string{true: "VALID", false: "INVALID"}[isValid])
}