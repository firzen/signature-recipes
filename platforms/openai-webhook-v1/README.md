# OpenAI Webhook v1 Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

`{timestamp}.{payload}`

## Secret

Webhook Secret (from OpenAI API settings)

## Expected Signature

sha256=HMAC-SHA256(timestamp + "." + payload, webhook_secret)

## API Version

v1

## Documentation

- [OpenAI Webhooks](https://platform.openai.com/docs/api-reference/webhooks)
- [Webhook Signatures](https://platform.openai.com/docs/api-reference/webhooks/signing)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- OpenAI webhooks use HMAC-SHA256 to sign the request body.
- The signature header format is: `t={timestamp},v1={signature}`.
- Always verify the timestamp to prevent replay attacks.
- Get the webhook secret from your OpenAI API settings.