# ElevenLabs Webhook v1 Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

`{timestamp}.{payload}`

## Secret

Webhook Secret (from ElevenLabs API settings)

## Expected Signature

HMAC-SHA256(timestamp + "." + payload, webhook_secret)

## API Version

v1

## Documentation

- [ElevenLabs Webhooks](https://elevenlabs.io/docs/api-reference/webhooks)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- ElevenLabs webhooks use HMAC-SHA256 to sign the request body.
- The signature header is `X-ElevenLabs-Signature`.
- The header format is: `t={timestamp},v1={signature}`.
- Always verify the timestamp to prevent replay attacks.