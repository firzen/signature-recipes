package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"strconv"
	"time"
)

func generateSignature(timestamp, body, signingSecret string) string {
	stringToSign := "v0:" + timestamp + ":" + body
	h := hmac.New(sha256.New, []byte(signingSecret))
	h.Write([]byte(stringToSign))
	return "v0=" + hex.EncodeToString(h.Sum(nil))
}

func verifyRequest(timestamp, body, signatureHeader, signingSecret string) bool {
	expectedSignature := generateSignature(timestamp, body, signingSecret)
	return expectedSignature == signatureHeader
}

func main() {
	signingSecret := "your_slack_signing_secret"
	timestamp := strconv.FormatInt(time.Now().Unix(), 10)
	body := `{"token":"abc123","team_id":"T123"}`
	signature := generateSignature(timestamp, body, signingSecret)

	fmt.Println("Timestamp:", timestamp)
	fmt.Println("Generated Signature:", signature)
	fmt.Println()

	isValid := verifyRequest(timestamp, body, signature, signingSecret)
	fmt.Println("Signature verification result:", map[bool]string{true: "VALID", false: "INVALID"}[isValid])
}