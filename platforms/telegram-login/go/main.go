package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"sort"
)

func buildDataCheckString(data map[string]string) string {
	keys := make([]string, 0, len(data))
	for k := range data {
		if k != "hash" {
			keys = append(keys, k)
		}
	}
	sort.Strings(keys)

	var checkString string
	for i, k := range keys {
		if i > 0 {
			checkString += "\n"
		}
		checkString += k + "=" + data[k]
	}
	return checkString
}

func verifyLogin(data map[string]string, botToken string) bool {
	secretKey := sha256.Sum256([]byte(botToken))
	dataCheckString := buildDataCheckString(data)

	h := hmac.New(sha256.New, secretKey[:])
	h.Write([]byte(dataCheckString))
	expectedHash := hex.EncodeToString(h.Sum(nil))

	return expectedHash == data["hash"]
}

func main() {
	botToken := "your_bot_token_without_bot_prefix"

	data := map[string]string{
		"auth_date":  "1710000000",
		"first_name": "John",
		"id":         "123456789",
		"last_name":  "Doe",
		"username":   "johndoe",
	}

	secretKey := sha256.Sum256([]byte(botToken))
	dataCheckString := buildDataCheckString(data)

	h := hmac.New(sha256.New, secretKey[:])
	h.Write([]byte(dataCheckString))
	hash := hex.EncodeToString(h.Sum(nil))

	data["hash"] = hash

	fmt.Println("Data Check String:")
	fmt.Println(dataCheckString)
	fmt.Println()

	fmt.Println("Generated Hash:")
	fmt.Println(hash)
	fmt.Println()

	isValid := verifyLogin(data, botToken)
	fmt.Println("Verification result:", map[bool]string{true: "VALID", false: "INVALID"}[isValid])
}