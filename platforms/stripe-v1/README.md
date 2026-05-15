# Stripe API v1 Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

`Stripe-Signature: t={timestamp},v1={signature}`

## Secret

Stripe Webhook Secret

## Expected Signature

v1=HMAC-SHA256(timestamp + "." + request_body, webhook_secret)

## API Version

v1

## Documentation

- [Stripe Webhook Signatures](https://stripe.com/docs/webhooks/signatures)
- [API Authentication](https://stripe.com/docs/api/authentication)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Stripe webhooks use HMAC-SHA256 to sign the request body.
- The signature header contains timestamp and signature.
- Always verify the timestamp to prevent replay attacks.
- Get the webhook secret from your Stripe dashboard.