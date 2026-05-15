package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/hex"
	"fmt"
)

func validateAccessToken(accessToken, clientSecret string) string {
	h := hmac.New(sha256.New, []byte(clientSecret))
	h.Write([]byte(accessToken))
	return hex.EncodeToString(h.Sum(nil))
}

func main() {
	clientSecret := "your-github-client-secret"
	accessToken := "gho_..."

	signature := validateAccessToken(accessToken, clientSecret)

	fmt.Println("HMAC Signature:")
	fmt.Println(signature)
}