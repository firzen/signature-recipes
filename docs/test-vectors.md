# Test Vectors

This document provides test vectors for verifying signature implementations.

## OpenAI API

**Input:**
- api_key: "sk-1234567890abcdef"
- model: "gpt-3.5-turbo"
- prompt: "Hello"
- max_tokens: 100

**Expected Signature Header:**
`Authorization: Bearer sk-1234567890abcdef`

## Stripe API

**Input:**
- secret_key: "sk_test_12345"
- timestamp: 1609459200
- body: '{"amount":1000,"currency":"usd"}'

**Expected Signature (simplified):**
HMAC-SHA256 of timestamp + "." + body

## Binance API

**Input:**
- secret_key: "abc123"
- params: {"symbol": "BTCUSDT", "quantity": 1, "timestamp": 1609459200}

**Expected Signature:**
HMAC-SHA256 of query string with secret

## AWS V4

**Input:**
- access_key: "AKIAIOSFODNN7EXAMPLE"
- secret_key: "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY"
- region: "us-east-1"
- service: "s3"
- method: "GET"
- host: "examplebucket.s3.amazonaws.com"
- uri: "/"
- timestamp: "20130524T000000Z"

**Expected Signature:**
Derived signature using AWS V4 algorithm

## WeChat Pay

**Input:**
- appid: "wx1234567890"
- mch_id: "1234567890"
- nonce_str: "abcdefghijklmnop"
- body: "Test Payment"
- out_trade_no: "202401010001"
- total_fee: 100
- spbill_create_ip: "192.168.1.1"
- notify_url: "https://example.com/notify"
- trade_type: "APP"
- key: "your_secret_key"

**Expected Signature:**
MD5 of sorted parameters + key