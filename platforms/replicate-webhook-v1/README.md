# Replicate Webhook v1 Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

request_body

## Secret

Webhook Secret (from Replicate dashboard)

## Expected Signature

HMAC-SHA256(request_body, webhook_secret)

## API Version

v1

## Documentation

- [Replicate Webhooks](https://replicate.com/docs/webhooks)
- [Webhook Security](https://replicate.com/docs/webhooks#security)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Replicate webhooks use HMAC-SHA256 to sign the request body.
- The signature header is `X-Replicate-Signature`.
- The signature is hex-encoded.
- Always verify the signature before processing webhook payloads.