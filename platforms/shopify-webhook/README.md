# Shopify Webhook Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

request_body

## Secret

API Secret Key

## Expected Signature

Base64(HMAC-SHA256(request_body, api_secret))

## API Version

v2024-01

## Documentation

- [Shopify Webhook Verification](https://shopify.dev/docs/api/admin-rest/2024-01/topics/webhooks#verify-webhook)
- [API Authentication](https://shopify.dev/docs/api/admin-rest/2024-01/introduction/authentication)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Shopify webhooks use HMAC-SHA256 to sign the request body.
- The signature header is `X-Shopify-Hmac-Sha256`.
- The signature is Base64 encoded.
- Always verify the signature before processing webhook payloads.