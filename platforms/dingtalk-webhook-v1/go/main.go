package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/base64"
	"fmt"
	"net/url"
	"strconv"
	"time"
)

func generateSignature(timestamp, secret string) string {
	stringToSign := timestamp + "\n" + secret
	h := hmac.New(sha256.New, []byte(secret))
	h.Write([]byte(stringToSign))
	return base64.StdEncoding.EncodeToString(h.Sum(nil))
}

func main() {
	secret := "SECyour_secret"
	timestamp := strconv.FormatInt(time.Now().UnixMilli(), 10)
	signature := generateSignature(timestamp, secret)

	fmt.Println("Timestamp:", timestamp)
	fmt.Println("Signature:", signature)
	fmt.Println()
	fmt.Println("Webhook URL with signature:")
	fmt.Printf("https://oapi.dingtalk.com/robot/send?access_token=xxx&timestamp=%s&sign=%s\n", timestamp, url.QueryEscape(signature))
}