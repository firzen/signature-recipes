# Signature Recipes

A collection of minimal, dependency-light API signature generation examples across different platforms and programming languages.

**20+ platforms** | **5 programming languages** | **No external dependencies** | **No package managers required**

No Composer, npm, pip, or other package managers needed. All examples use only standard library functions.

This repository focuses only on the signing logic:
- How to build the string to sign
- How to generate HMAC / RSA / SHA signatures
- How to format headers or query parameters
- How to verify the output with test vectors

It is **not** a full SDK.

## Design Principles

1. **Zero dependencies** - Uses only standard library, no Composer, npm, pip, or any package managers required
2. **One file per example** - Easy to find and understand
3. **Easy to copy and run** - Self-contained examples
4. **Fixed test input and expected output** - Verifiable results
5. **No real API keys** - Safe to use and share
6. **No full SDK abstraction** - Focused on signing logic only
7. **Clear comments for each signing step** - Educational purpose

## Supported Platforms

| Category | Platform | Version | Algorithm |
|----------|----------|---------|-----------|
| **Cloud** | [AWS Signature](platforms/aws-v4/) | v4 | HMAC-SHA256 |
| | [AWS Bedrock SigV4](platforms/aws-bedrock-sigv4/) | v4 | HMAC-SHA256 |
| | [Alibaba Cloud API](platforms/aliyun-api-v1/) | v1 | HMAC-SHA1 |
| **AI/LLM** | [OpenAI API](platforms/openai-v1/) | v1 | Bearer Token |
| | [OpenAI Webhook](platforms/openai-webhook-v1/) | v1 | HMAC-SHA256 |
| | [Azure OpenAI Webhook](platforms/azure-openai-webhook-v1/) | v1 | HMAC-SHA256 |
| | [Google Vertex AI JWT](platforms/google-vertex-ai-jwt/) | v1 | RSA-SHA256 |
| **AI Tools** | [Replicate Webhook](platforms/replicate-webhook-v1/) | v1 | HMAC-SHA256 |
| | [FAL Webhook](platforms/fal-webhook-v1/) | v1 | HMAC-SHA256 |
| | [ElevenLabs Webhook](platforms/elevenlabs-webhook-v1/) | v1 | HMAC-SHA256 |
| **Payments** | [Alipay OpenAPI](platforms/alipay-openapi-v1/) | v1 | RSA-SHA256 |
| | [WeChat Pay](platforms/wechat-pay-v2/) | v2 | MD5 |
| | [WeChat Pay](platforms/wechat-pay-v3/) | v3 | HMAC-SHA256 |
| | [Stripe API](platforms/stripe-v1/) | v1 | HMAC-SHA256 |
| **Messaging** | [Slack Request Verification](platforms/slack-request-verification-v1/) | v1 | HMAC-SHA256 |
| | [DingTalk Webhook](platforms/dingtalk-webhook-v1/) | v1 | HMAC-SHA256 |
| **E-commerce** | [Shopify Webhook](platforms/shopify-webhook-v2024-01/) | v2024-01 | HMAC-SHA256 |
| **Trading** | [Binance API](platforms/binance-v3/) | v3 | HMAC-SHA256 |
| **Developer** | [GitHub Webhook](platforms/github-webhook-v1/) | v1 | HMAC-SHA256 |
| **Login** | [Telegram Login](platforms/telegram-login-v1/) | v1 | HMAC-SHA256 |

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript / Node.js

## Quick Start

Each platform folder contains language-specific implementations:

```
platforms/{platform}-{version}/{language}/sign.{ext}
```

**Example:** Run the AWS Signature v4 example in Python:

```bash
cd platforms/aws-v4/python
python sign.py
```

## Project Structure

```
signature-recipes/
├── LICENSE
├── CONTRIBUTING.md
├── README.md
├── docs/
│   ├── signing-basics.md
│   ├── common-mistakes.md
│   ├── test-vectors.md
│   └── readme-template.md
└── platforms/
    ├── aws-v4/
    ├── aws-bedrock-sigv4/
    ├── aliyun-api-v1/
    ├── alipay-openapi-v1/
    ├── azure-openai-webhook-v1/
    ├── binance-v3/
    ├── dingtalk-webhook-v1/
    ├── elevenlabs-webhook-v1/
    ├── fal-webhook-v1/
    ├── github-webhook-v1/
    ├── google-vertex-ai-jwt/
    ├── openai-v1/
    ├── openai-webhook-v1/
    ├── replicate-webhook-v1/
    ├── shopify-webhook-v2024-01/
    ├── slack-request-verification-v1/
    ├── stripe-v1/
    ├── telegram-login-v1/
    ├── wechat-pay-v2/
    └── wechat-pay-v3/
```

## Documentation

- [Signing Basics](docs/signing-basics.md) - Introduction to API signature concepts
- [Common Mistakes](docs/common-mistakes.md) - Common pitfalls and how to avoid them
- [Test Vectors](docs/test-vectors.md) - Test data for verification
- [README Template](docs/readme-template.md) - Guidelines for adding new platforms

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines on adding new platforms or languages.

## License

MIT License - see [LICENSE](LICENSE) for details.