package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"net/url"
	"sort"
	"strconv"
	"time"
)

func signRequest(params map[string]string, secretKey string) string {
	keys := make([]string, 0, len(params))
	for key := range params {
		keys = append(keys, key)
	}
	sort.Strings(keys)

	var queryString string
	for i, key := range keys {
		if i > 0 {
			queryString += "&"
		}
		queryString += url.QueryEscape(key) + "=" + url.QueryEscape(params[key])
	}

	h := hmac.New(sha256.New, []byte(secretKey))
	h.Write([]byte(queryString))
	signature := hex.EncodeToString(h.Sum(nil))

	return queryString + "&signature=" + signature
}

func buildSignedRequest(apiKey, secretKey string, params map[string]string) map[string]string {
	if _, ok := params["timestamp"]; !ok {
		params["timestamp"] = strconv.FormatInt(time.Now().UnixMilli(), 10)
	}

	signedQuery := signRequest(params, secretKey)

	return map[string]string{
		"header": "X-MBX-APIKEY: " + apiKey,
		"query":  signedQuery,
	}
}

func main() {
	apiKey := "your_api_key"
	secretKey := "your_secret_key"

	params := map[string]string{
		"symbol":   "BTCUSDT",
		"quantity": "0.001",
		"price":    "40000.00",
	}

	request := buildSignedRequest(apiKey, secretKey, params)

	fmt.Println("Headers:")
	fmt.Println("  " + request["header"])
	fmt.Println("\nQuery String:")
	fmt.Println(request["query"])
}