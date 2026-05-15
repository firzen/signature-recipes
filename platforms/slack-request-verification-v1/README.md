# Slack Request Verification v1 Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

v0:{timestamp}:{request_body}

## Secret

Signing Secret

## Expected Signature

v0=HMAC-SHA256(v0:{timestamp}:{request_body}, signing_secret)

## API Version

v1

## Documentation

- [Slack Request Verification](https://api.slack.com/authentication/verifying-requests-from-slack)
- [Signing Secrets](https://api.slack.com/authentication/signing-secrets)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Slack uses request verification with timestamp and body.
- The signature header format is: `v0=xxx`.
- Always verify the timestamp to prevent replay attacks (Slack recommends 5 minutes).
- The signing secret is found in Slack API settings.