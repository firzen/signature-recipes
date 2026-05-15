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

## WeChat Pay v2

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
MD5 of sorted parameters + key (uppercase)

## WeChat Pay v3

**Input:**
- mchid: "1234567890"
- serial_no: "certificate_serial_number"
- timestamp: 1609459200
- nonce_str: "abcdefghijklmnop"
- body: '{"amount":{"total":100}}'
- private_key: "your_private_key"

**Expected Signature:**
SHA-256 with RSA private key

## Alipay OpenAPI

**Input:**
- app_id: "2021001234567890"
- method: "alipay.trade.create"
- charset: "UTF-8"
- sign_type: "RSA2"
- timestamp: "2024-01-01 12:00:00"
- version: "1.0"
- biz_content: '{"out_trade_no":"202401010001","total_amount":"100.00","subject":"Test"}'
- private_key: "your_rsa_private_key"

**Expected Signature:**
SHA256withRSA of sorted parameters

## GitHub Webhook

**Input:**
- secret: "your_webhook_secret"
- payload: '{"action":"created"}'

**Expected Signature:**
`sha256=HMAC-SHA256(payload, secret)`

## Slack Request Verification

**Input:**
- signing_secret: "your_slack_signing_secret"
- timestamp: 1609459200
- body: '{"type":"event_callback"}'

**Expected Signature:**
`v0=HMAC-SHA256("v0:" + timestamp + ":" + body, signing_secret)`

## Shopify Webhook

**Input:**
- api_secret: "your_shopify_api_secret"
- payload: '{"order":{"id":123}}'

**Expected Signature:**
Base64 encoded HMAC-SHA256(payload, api_secret)

## DingTalk Webhook

**Input:**
- secret: "your_dingtalk_secret"
- timestamp: 1609459200

**Expected Signature:**
Base64 encoded HMAC-SHA256(timestamp + "\n" + secret, secret)

## Telegram Login

**Input:**
- bot_token: "123456:ABC-DEF1234ghIkl-zyx57W2v1u123ew11"
- data: {"id":123456,"first_name":"John"}

**Expected Signature:**
HMAC-SHA256 of sorted data with bot_token

## Google Sign-In

**Input:**
- id_token: "eyJhbGciOiJSUzI1NiIsImtpZCI6..."
- client_id: "your-client-id.apps.googleusercontent.com"

**Expected Verification:**
JWT verification using Google's public keys

## Facebook Login

**Input:**
- app_secret: "your_facebook_app_secret"
- access_token: "EAA..."

**Expected Signature:**
HMAC-SHA256(access_token, app_secret)

## Alibaba Cloud API

**Input:**
- access_key: "your_access_key"
- secret_key: "your_secret_key"
- action: "DescribeInstances"
- version: "2014-05-26"
- region: "cn-hangzhou"
- timestamp: "2024-01-01T12:00:00Z"

**Expected Signature:**
HMAC-SHA1 of sorted query string

## OpenAI Webhook

**Input:**
- webhook_secret: "whsec_your_webhook_secret"
- timestamp: 1609459200
- payload: '{"event":"completion"}'

**Expected Signature:**
HMAC-SHA256(timestamp + "." + payload, webhook_secret)

## Azure OpenAI Webhook

**Input:**
- webhook_secret: "your_azure_webhook_secret"
- timestamp: 1609459200
- payload: '{"event":"completion"}'

**Expected Signature:**
HMAC-SHA256(timestamp + "." + payload, webhook_secret)

## Replicate Webhook

**Input:**
- webhook_secret: "your_replicate_webhook_secret"
- payload: '{"status":"completed"}'

**Expected Signature:**
HMAC-SHA256(payload, webhook_secret)

## FAL Webhook

**Input:**
- webhook_secret: "your_fal_webhook_secret"
- timestamp: 1609459200
- payload: '{"status":"completed"}'

**Expected Signature:**
HMAC-SHA256(timestamp + "." + payload, webhook_secret)

## ElevenLabs Webhook

**Input:**
- webhook_secret: "your_elevenlabs_webhook_secret"
- timestamp: 1609459200
- payload: '{"status":"completed"}'

**Expected Signature:**
HMAC-SHA256(timestamp + "." + payload, webhook_secret)

## Google Vertex AI JWT

**Input:**
- private_key: "your_rsa_private_key"
- client_email: "your-service-account@project.iam.gserviceaccount.com"
- audience: "https://aiplatform.googleapis.com/"
- expires_in: 3600

**Expected Signature:**
RS256 JWT token

## AWS Bedrock SigV4

**Input:**
- access_key: "AKIAIOSFODNN7EXAMPLE"
- secret_key: "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY"
- region: "us-east-1"
- service: "bedrock"
- method: "POST"
- host: "bedrock-runtime.us-east-1.amazonaws.com"
- uri: "/model/anthropic.claude-v2/invoke"
- timestamp: "20240101T120000Z"

**Expected Signature:**
AWS V4 signature for Bedrock service