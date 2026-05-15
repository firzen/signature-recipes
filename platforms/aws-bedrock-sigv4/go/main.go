package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"sort"
	"time"
)

func hmacSha256(data, key []byte) []byte {
	h := hmac.New(sha256.New, key)
	h.Write(data)
	return h.Sum(nil)
}

func hashSha256(data []byte) []byte {
	h := sha256.New()
	h.Write(data)
	return h.Sum(nil)
}

func buildCanonicalRequest(method, uri, queryString string, headers map[string]string, payload string) string {
	var sortedKeys []string
	for k := range headers {
		sortedKeys = append(sortedKeys, k)
	}
	sort.Strings(sortedKeys)

	var canonicalHeaders string
	var signedHeaders string
	for i, k := range sortedKeys {
		if i > 0 {
			signedHeaders += ";"
		}
		signedHeaders += k
		canonicalHeaders += fmt.Sprintf("%s:%s\n", k, headers[k])
	}

	payloadHash := hex.EncodeToString(hashSha256([]byte(payload)))

	return fmt.Sprintf("%s\n%s\n%s\n%s\n%s\n%s", method, uri, queryString, canonicalHeaders, signedHeaders, payloadHash)
}

func buildStringToSign(timestamp, date, region, service, canonicalRequest string) string {
	canonicalRequestHash := hex.EncodeToString(hashSha256([]byte(canonicalRequest)))
	return fmt.Sprintf("AWS4-HMAC-SHA256\n%s\n%s/%s/%s/aws4_request\n%s", timestamp, date, region, service, canonicalRequestHash)
}

func generateSignature(secretKey, date, region, service, stringToSign string) string {
	kDate := hmacSha256([]byte(date), []byte("AWS4"+secretKey))
	kRegion := hmacSha256([]byte(region), kDate)
	kService := hmacSha256([]byte(service), kRegion)
	kSigning := hmacSha256([]byte("aws4_request"), kService)
	return hex.EncodeToString(hmacSha256([]byte(stringToSign), kSigning))
}

func main() {
	accessKey := "your_aws_access_key"
	secretKey := "your_aws_secret_key"
	region := "us-east-1"
	service := "bedrock"
	method := "POST"
	uri := "/model/anthropic.claude-3-sonnet-20240229/v1/complete"
	payload := `{"prompt":"Hello","max_tokens_to_sample":100}`

	timestamp := time.Now().UTC().Format("20060102T150405Z")
	date := timestamp[:8]

	headers := map[string]string{
		"host":        "bedrock." + region + ".amazonaws.com",
		"x-amz-date":  timestamp,
		"content-type": "application/json",
	}

	canonicalRequest := buildCanonicalRequest(method, uri, "", headers, payload)
	stringToSign := buildStringToSign(timestamp, date, region, service, canonicalRequest)
	signature := generateSignature(secretKey, date, region, service, stringToSign)

	fmt.Println("Canonical Request:\n", canonicalRequest)
	fmt.Println("\nString to Sign:\n", stringToSign)
	fmt.Println("\nSignature:\n", signature)
}