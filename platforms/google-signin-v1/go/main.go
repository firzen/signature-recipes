package main

import (
	"encoding/base64"
	"encoding/json"
	"fmt"
	"strings"
	"time"
)

func base64urlDecode(data string) ([]byte, error) {
	if rem := len(data) % 4; rem != 0 {
		data += strings.Repeat("=", 4-rem)
	}
	return base64.URLEncoding.DecodeString(data)
}

type Payload struct {
	Sub   string `json:"sub"`
	Aud   string `json:"aud"`
	Exp   int64  `json:"exp"`
	Email string `json:"email"`
}

func verifyGoogleIdToken(idToken, clientId string) (*Payload, error) {
	parts := strings.Split(idToken, ".")
	if len(parts) != 3 {
		return nil, fmt.Errorf("invalid token format")
	}

	payloadBytes, err := base64urlDecode(parts[1])
	if err != nil {
		return nil, err
	}

	var payload Payload
	if err := json.Unmarshal(payloadBytes, &payload); err != nil {
		return nil, err
	}

	if payload.Aud != clientId {
		return nil, fmt.Errorf("invalid audience")
	}

	if payload.Exp < time.Now().Unix() {
		return nil, fmt.Errorf("token expired")
	}

	return &payload, nil
}

func main() {
	clientId := "your-google-client-id.apps.googleusercontent.com"
	idToken := "eyJhbGciOiJSUzI1NiIsImtpZCI6..."

	payload, err := verifyGoogleIdToken(idToken, clientId)
	if err != nil {
		fmt.Println("Token verification failed:", err)
		return
	}

	fmt.Println("Token verified successfully!")
	fmt.Println("User ID:", payload.Sub)
	fmt.Println("Email:", payload.Email)
}