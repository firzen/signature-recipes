package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/hex"
	"fmt"
	"sort"
	"strings"
	"time"
)

func hashSha256(data string) string {
	h := sha256.New()
	h.Write([]byte(data))
	return hex.EncodeToString(h.Sum(nil))
}

func hmacSha256(data, key []byte) []byte {
	h := hmac.New(sha256.New, key)
	h.Write(data)
	return h.Sum(nil)
}

func buildCanonicalRequest(method, uri, query string, headers map[string]string, payload string) string {
	keys := make([]string, 0, len(headers))
	for key := range headers {
		keys = append(keys, key)
	}
	sort.Strings(keys)

	var canonicalHeaders, signedHeaders string
	for _, key := range keys {
		lowerKey := strings.ToLower(key)
		canonicalHeaders += lowerKey + ":" + strings.TrimSpace(headers[key]) + "\n"
		signedHeaders += lowerKey + ";"
	}
	signedHeaders = strings.TrimSuffix(signedHeaders, ";")

	payloadHash := hashSha256(payload)

	return fmt.Sprintf("%s\n%s\n%s\n%s\n%s\n%s",
		method, uri, query, canonicalHeaders, signedHeaders, payloadHash)
}

func buildStringToSign(algorithm, timestamp, credentialScope, canonicalRequest string) string {
	canonicalRequestHash := hashSha256(canonicalRequest)
	return fmt.Sprintf("%s\n%s\n%s\n%s", algorithm, timestamp, credentialScope, canonicalRequestHash)
}

func calculateSignature(stringToSign, secretKey, date, region, service string) string {
	kDate := hmacSha256([]byte(date), []byte("AWS4"+secretKey))
	kRegion := hmacSha256([]byte(region), kDate)
	kService := hmacSha256([]byte(service), kRegion)
	kSigning := hmacSha256([]byte("aws4_request"), kService)
	return hex.EncodeToString(hmacSha256([]byte(stringToSign), kSigning))
}

func buildAuthorizationHeader(accessKey, secretKey, region, service, method, uri, query string, headers map[string]string, payload, timestamp string) string {
	algorithm := "AWS4-HMAC-SHA256"
	date := timestamp[:8]
	credentialScope := fmt.Sprintf("%s/%s/%s/aws4_request", date, region, service)

	canonicalRequest := buildCanonicalRequest(method, uri, query, headers, payload)
	stringToSign := buildStringToSign(algorithm, timestamp, credentialScope, canonicalRequest)
	signature := calculateSignature(stringToSign, secretKey, date, region, service)

	keys := make([]string, 0, len(headers))
	for key := range headers {
		keys = append(keys, key)
	}
	sort.Strings(keys)
	var signedHeaders string
	for _, key := range keys {
		signedHeaders += strings.ToLower(key) + ";"
	}
	signedHeaders = strings.TrimSuffix(signedHeaders, ";")

	return fmt.Sprintf("%s Credential=%s/%s, SignedHeaders=%s, Signature=%s",
		algorithm, accessKey, credentialScope, signedHeaders, signature)
}

func main() {
	accessKey := "AKIAIOSFODNN7EXAMPLE"
	secretKey := "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY"
	region := "us-east-1"
	service := "s3"
	method := "GET"
	host := "examplebucket.s3.amazonaws.com"
	uri := "/"
	timestamp := time.Now().UTC().Format("20060102T150405Z")

	headers := map[string]string{
		"Host":       host,
		"X-Amz-Date": timestamp,
	}

	authorization := buildAuthorizationHeader(accessKey, secretKey, region, service, method, uri, "", headers, "", timestamp)

	fmt.Println("Authorization Header:")
	fmt.Println(authorization)
	fmt.Println()
	fmt.Println("X-Amz-Date:", timestamp)
}