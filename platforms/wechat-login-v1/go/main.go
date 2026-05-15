package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"net/url"
	"sort"
)

func buildQueryString(params map[string]string) string {
	var keys []string
	for k := range params {
		keys = append(keys, k)
	}
	sort.Strings(keys)

	var result []string
	for _, k := range keys {
		result = append(result, k+"="+url.QueryEscape(params[k]))
	}

	return join(result, "&")
}

func join(strs []string, sep string) string {
	result := ""
	for i, s := range strs {
		if i > 0 {
			result += sep
		}
		result += s
	}
	return result
}

func signQueryString(params map[string]string, appSecret string) string {
	queryString := buildQueryString(params)
	h := hmac.New(sha256.New, []byte(appSecret))
	h.Write([]byte(queryString))
	return hex.EncodeToString(h.Sum(nil))
}

func main() {
	appId := "your-wechat-app-id"
	appSecret := "your-wechat-app-secret"
	code := "001xxx"

	params := map[string]string{
		"appid":      appId,
		"secret":     appSecret,
		"js_code":    code,
		"grant_type": "authorization_code",
	}

	signature := signQueryString(params, appSecret)

	fmt.Println("Query String:")
	fmt.Println(buildQueryString(params))
	fmt.Println()
	fmt.Println("Signature:")
	fmt.Println(signature)
}