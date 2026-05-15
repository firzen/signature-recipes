# Signing Basics

This document explains the fundamentals of API signature generation.

## What is API Signing?

API signing is a security mechanism used to authenticate requests and ensure data integrity. It involves creating a unique signature using a secret key that only the client and server know.

## Common Signing Techniques

### HMAC (Hash-based Message Authentication Code)

HMAC is the most common signing method. It combines:
- A secret key
- A message (usually request parameters)
- A hash function (SHA-256, SHA-512, etc.)

**Platforms using HMAC:**
- Stripe API
- Binance API
- AWS Signature v4
- Slack Request Verification
- GitHub Webhook
- DingTalk Webhook
- Shopify Webhook

### RSA Signatures

Used when asymmetric cryptography is needed. The client signs with a private key, and the server verifies with a public key.

**Platforms using RSA:**
- Alipay OpenAPI
- Google Vertex AI JWT

### JWT (JSON Web Tokens)

Used for authentication and authorization. JWT tokens are signed with either HMAC or RSA.

**Platforms using JWT:**
- Google Vertex AI
- Google Sign-In

### Bearer Tokens

Simple token-based authentication where the token is passed in the Authorization header.

**Platforms using Bearer Tokens:**
- OpenAI API

## Best Practices

1. Always use HTTPS to prevent MITM attacks
2. Use strong hash algorithms (SHA-256 or higher)
3. Include a timestamp to prevent replay attacks
4. Include a nonce for additional security
5. Keep your secret keys secure
6. Never expose secrets in client-side code
7. Validate signatures server-side before processing

## Signature Generation Steps

### Step 1: Collect Parameters
Gather all request parameters, including query parameters, form data, and headers.

### Step 2: Sort Parameters
Sort parameters alphabetically by key name. Most APIs require this.

### Step 3: Create Canonical String
Build a query string or canonical request format.

### Step 4: Apply Signature Algorithm
Use HMAC, RSA, or other algorithm with the secret key.

### Step 5: Include Signature in Request
Add the signature to headers, query parameters, or request body.

## Supported Platforms (24+)

This project includes signature examples for:
- Cloud: AWS, Alibaba Cloud
- AI/LLM: OpenAI, Azure OpenAI, Google Vertex AI
- Payments: Stripe, Alipay, WeChat Pay
- Messaging: Slack, DingTalk
- E-commerce: Shopify
- Trading: Binance
- Developer: GitHub
- Login: Google Sign-In, Facebook Login, GitHub Login, WeChat Login, Telegram Login

## Supported Languages

All platforms have implementations in:
- PHP (5.6+)
- Java
- Go
- Python
- TypeScript/Node.js

All examples use only standard library functions with no external dependencies.