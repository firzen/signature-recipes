package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/base64"
	"fmt"
)

func generateSignature(payload, apiSecret string) string {
	h := hmac.New(sha256.New, []byte(apiSecret))
	h.Write([]byte(payload))
	return base64.StdEncoding.EncodeToString(h.Sum(nil))
}

func verifyWebhookSignature(payload, signatureHeader, apiSecret string) bool {
	expectedSignature := generateSignature(payload, apiSecret)
	return expectedSignature == signatureHeader
}

func main() {
	apiSecret := "your_shopify_api_secret"
	payload := `{"id":123,"email":"test@example.com"}`
	signature := generateSignature(payload, apiSecret)

	fmt.Println("Generated Signature (X-Shopify-Hmac-Sha256):")
	fmt.Println(signature)
	fmt.Println()

	isValid := verifyWebhookSignature(payload, signature, apiSecret)
	fmt.Println("Signature verification result:", map[bool]string{true: "VALID", false: "INVALID"}[isValid])
}