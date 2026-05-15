package main

import (
	"fmt"
)

func createAuthorizationHeader(apiKey string) string {
	return "Bearer " + apiKey
}

type Request struct {
	Headers map[string]string
	Body    map[string]interface{}
}

func buildRequest(apiKey, model, prompt string, maxTokens int) Request {
	headers := map[string]string{
		"Authorization": createAuthorizationHeader(apiKey),
		"Content-Type":  "application/json",
	}

	body := map[string]interface{}{
		"model":       model,
		"prompt":      prompt,
		"max_tokens":  maxTokens,
	}

	return Request{Headers: headers, Body: body}
}

func main() {
	apiKey := "sk-your-api-key"
	request := buildRequest(apiKey, "gpt-3.5-turbo", "Hello, world!", 100)

	fmt.Println("Headers:")
	for key, value := range request.Headers {
		fmt.Printf("  %s: %s\n", key, value)
	}

	fmt.Println("\nBody:")
	for key, value := range request.Body {
		fmt.Printf("  %s: %v\n", key, value)
	}
}