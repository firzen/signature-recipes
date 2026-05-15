# Stripe API Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

t=1710000000,v1=HMAC-SHA256(timestamp + "." + request_body, webhook_secret)

## Secret

whsec_test_webhook_secret_abc123

## Expected Signature

t=1710000000,v1=abc123def456...

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Webhook signatures verify request authenticity from Stripe.
- The signature header format is: `t=timestamp,v1=signature`.
- Always validate the timestamp to prevent replay attacks.
- API requests use Bearer token authentication, not HMAC signatures.