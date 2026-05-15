# Azure OpenAI Webhook v1 Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

`{timestamp}.{payload}`

## Secret

Webhook Secret (from Azure AI Studio)

## Expected Signature

HMAC-SHA256(timestamp + "." + payload, webhook_secret)

## API Version

v1

## Documentation

- [Azure OpenAI Webhooks](https://learn.microsoft.com/en-us/azure/ai-services/openai/how-to/webhooks)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Azure OpenAI webhooks use HMAC-SHA256 to sign the request body.
- The signature header is `Azure-Signature`.
- The header format is: `{timestamp}.{signature}`.
- Always verify the timestamp to prevent replay attacks.