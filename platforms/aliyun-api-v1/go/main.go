package main

import (
	"crypto/hmac"
	"crypto/sha1"
	"encoding/base64"
	"fmt"
	"net/url"
	"sort"
	"strings"
	"time"
)

func percentEncode(s string) string {
	return strings.ReplaceAll(url.QueryEscape(s), "+", "%20")
}

func buildCanonicalizedQueryString(params map[string]string) string {
	keys := make([]string, 0, len(params))
	for k := range params {
		if k != "Signature" {
			keys = append(keys, k)
		}
	}
	sort.Strings(keys)

	var result []string
	for _, k := range keys {
		result = append(result, percentEncode(k)+"="+percentEncode(params[k]))
	}
	return strings.Join(result, "&")
}

func sign(accessKeySecret, method, host, path string, params map[string]string) string {
	canonicalizedQueryString := buildCanonicalizedQueryString(params)
	stringToSign := method + "\n" + host + "\n" + path + "\n" + canonicalizedQueryString
	
	h := hmac.New(sha1.New, []byte(accessKeySecret))
	h.Write([]byte(stringToSign))
	
	return base64.StdEncoding.EncodeToString(h.Sum(nil))
}

func main() {
	accessKeyId := "your_access_key_id"
	accessKeySecret := "your_access_key_secret"
	method := "GET"
	host := "ecs.aliyuncs.com"
	path := "/"

	params := map[string]string{
		"Format":          "JSON",
		"Version":         "2014-05-26",
		"AccessKeyId":     accessKeyId,
		"SignatureMethod":  "HMAC-SHA1",
		"Timestamp":       time.Now().UTC().Format("2006-01-02T15:04:05Z"),
		"SignatureVersion": "1.0",
		"SignatureNonce":   fmt.Sprintf("%d", time.Now().UnixNano()),
		"Action":          "DescribeRegions",
	}

	signature := sign(accessKeySecret, method, host, path, params)
	params["Signature"] = signature

	fmt.Println("Signature:")
	fmt.Println(signature)
	fmt.Println()
	fmt.Println("Full URL:")
	fmt.Println("https://" + host + path + "?" + buildCanonicalizedQueryString(params))
}