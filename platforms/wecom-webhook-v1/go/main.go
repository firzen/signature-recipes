package main

import (
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"time"
)

func generateSignature(timestamp int64, secret string) string {
	stringToSign := fmt.Sprintf("%d\n%s", timestamp, secret)
	h := sha256.Sum256([]byte(stringToSign))
	return hex.EncodeToString(h[:])
}

func buildWebhookUrl(webhookUrl, secret string) string {
	timestamp := time.Now().Unix()
	signature := generateSignature(timestamp, secret)
	return webhookUrl + "&timestamp=" + fmt.Sprintf("%d", timestamp) + "&sign=" + signature
}

func main() {
	webhookUrl := "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=your-webhook-key"
	secret := "your-wecom-bot-secret"

	signedUrl := buildWebhookUrl(webhookUrl, secret)

	fmt.Println("Signed Webhook URL:")
	fmt.Println(signedUrl)
	fmt.Println()
	fmt.Println("Timestamp:", time.Now().Unix())
	fmt.Println("Signature:", generateSignature(time.Now().Unix(), secret))
}