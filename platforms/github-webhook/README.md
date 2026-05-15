# GitHub Webhook Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

`X-Hub-Signature-256: sha256=HMAC-SHA256(request_body, webhook_secret)`

## Secret

Webhook Secret

## Expected Signature

sha256=HMAC-SHA256(request_body, webhook_secret)

## API Version

v1

## Documentation

- [GitHub Webhook Signatures](https://docs.github.com/en/webhooks/securing-your-webhooks)
- [Webhook Events](https://docs.github.com/en/webhooks-and-events/webhooks/webhook-events-and-payloads)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- GitHub webhooks use HMAC-SHA256 to sign the request body.
- The signature header format is: `sha256=signature`.
- Always verify the signature before processing webhook payloads.
- The entire request body must be used for verification.