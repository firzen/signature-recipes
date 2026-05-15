package main

import (
	"crypto/md5"
	"encoding/hex"
	"fmt"
	"math/rand"
	"sort"
	"strconv"
	"time"
)

func generateNonceStr() string {
	chars := "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
	r := rand.New(rand.NewSource(time.Now().UnixNano()))
	b := make([]byte, 32)
	for i := range b {
		b[i] = chars[r.Intn(len(chars))]
	}
	return string(b)
}

func signParams(params map[string]string, key string) string {
	keys := make([]string, 0, len(params))
	for k := range params {
		keys = append(keys, k)
	}
	sort.Strings(keys)

	var signString string
	for _, k := range keys {
		v := params[k]
		if v != "" && k != "sign" {
			if signString != "" {
				signString += "&"
			}
			signString += k + "=" + v
		}
	}

	signString += "&key=" + key

	h := md5.New()
	h.Write([]byte(signString))
	return hex.EncodeToString(h.Sum(nil))
}

func buildPaymentRequest(appid, mchId, key, body, outTradeNo string, totalFee int, spbillCreateIp, notifyUrl, tradeType string) map[string]string {
	params := map[string]string{
		"appid":            appid,
		"mch_id":           mchId,
		"nonce_str":        generateNonceStr(),
		"body":             body,
		"out_trade_no":     outTradeNo,
		"total_fee":        strconv.Itoa(totalFee),
		"spbill_create_ip": spbillCreateIp,
		"notify_url":       notifyUrl,
		"trade_type":       tradeType,
	}

	params["sign"] = signParams(params, key)

	return params
}

func main() {
	appid := "wx1234567890abcdef"
	mchId := "1234567890"
	key := "your_secret_key"

	params := buildPaymentRequest(
		appid, mchId, key,
		"Test Payment",
		"202401010001",
		100,
		"192.168.1.1",
		"https://example.com/notify",
		"APP",
	)

	fmt.Println("Request Parameters:")
	keys := make([]string, 0, len(params))
	for k := range params {
		keys = append(keys, k)
	}
	sort.Strings(keys)
	for _, k := range keys {
		fmt.Printf("  %s: %s\n", k, params[k])
	}
}