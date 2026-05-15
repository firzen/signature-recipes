# FAL Webhook v1 Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

`{timestamp}.{payload}`

## Secret

Webhook Secret (from FAL dashboard)

## Expected Signature

sha256=HMAC-SHA256(timestamp + "." + payload, webhook_secret)

## API Version

v1

## Documentation

- [FAL Webhooks](https://fal.ai/docs/guides/webhooks)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- FAL webhooks use HMAC-SHA256 to sign the request body.
- The signature header format is: `t={timestamp},v1={signature}`.
- Always verify the timestamp to prevent replay attacks.