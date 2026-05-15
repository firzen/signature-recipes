package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/hex"
	"fmt"
)

func validateAccessToken(accessToken, appSecret string) string {
	h := hmac.New(sha256.New, []byte(appSecret))
	h.Write([]byte(accessToken))
	return hex.EncodeToString(h.Sum(nil))
}

func verifyAppSecretProof(accessToken, appSecret, appSecretProof string) bool {
	expectedProof := validateAccessToken(accessToken, appSecret)
	return expectedProof == appSecretProof
}

func main() {
	appSecret := "your-facebook-app-secret"
	accessToken := "EAA..."

	appSecretProof := validateAccessToken(accessToken, appSecret)

	fmt.Println("App Secret Proof:")
	fmt.Println(appSecretProof)
	fmt.Println()

	isValid := verifyAppSecretProof(accessToken, appSecret, appSecretProof)
	fmt.Println("Verification result:", map[bool]string{true: "VALID", false: "INVALID"}[isValid])
}