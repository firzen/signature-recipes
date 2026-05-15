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

func generateSignature(timestamp, payload, webhookSecret string) string {
	stringToSign := timestamp + "." + payload
	h := hmac.New(sha256.New, []byte(webhookSecret))
	h.Write([]byte(stringToSign))
	return "t=" + timestamp + ",v1=" + hex.EncodeToString(h.Sum(nil))
}

func verifyWebhookSignature(payload, signatureHeader, webhookSecret string) bool {
	parts := strings.Split(signatureHeader, ",")
	timestamp := strings.Replace(parts[0], "t=", "", 1)
	actualSignature := strings.Replace(parts[1], "v1=", "", 1)

	stringToSign := timestamp + "." + payload
	h := hmac.New(sha256.New, []byte(webhookSecret))
	h.Write([]byte(stringToSign))
	expectedSignature := hex.EncodeToString(h.Sum(nil))

	return expectedSignature == actualSignature
}

func main() {
	webhookSecret := "your_fal_webhook_secret"
	payload := `{"id":"xxx","status":"completed","output":{"result":"hello"}}`
	timestamp := strconv.FormatInt(time.Now().Unix(), 10)
	signatureHeader := generateSignature(timestamp, payload, webhookSecret)

	fmt.Println("Generated Signature Header:")
	fmt.Println(signatureHeader)
	fmt.Println()

	isValid := verifyWebhookSignature(payload, signatureHeader, webhookSecret)
	fmt.Println("Signature verification result:", map[bool]string{true: "VALID", false: "INVALID"}[isValid])
}