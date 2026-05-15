package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/base64"
	"fmt"
	"net/url"
	"time"
)

func generateSignature(timestamp int64, secret string) string {
	stringToSign := fmt.Sprintf("%d\n%s", timestamp, secret)
	h := hmac.New(sha256.New, []byte(secret))
	h.Write([]byte(stringToSign))
	return base64.StdEncoding.EncodeToString(h.Sum(nil))
}

func buildWebhookUrl(webhookUrl, secret string) string {
	timestamp := time.Now().Unix()
	signature := generateSignature(timestamp, secret)
	return webhookUrl + "&timestamp=" + fmt.Sprintf("%d", timestamp) + "&sign=" + url.QueryEscape(signature)
}

func main() {
	webhookUrl := "https://open.feishu.cn/open-apis/bot/v2/hook/your-webhook-id"
	secret := "your-feishu-bot-secret"

	signedUrl := buildWebhookUrl(webhookUrl, secret)

	fmt.Println("Signed Webhook URL:")
	fmt.Println(signedUrl)
	fmt.Println()
	fmt.Println("Timestamp:", time.Now().Unix())
	fmt.Println("Signature:", generateSignature(time.Now().Unix(), secret))
}