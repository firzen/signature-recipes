# Feishu Webhook v1 Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

`timestamp\nsecret`

## Secret

Feishu Bot Secret (obtained from Feishu Developer Console)

## Expected Signature

Base64 encoded HMAC-SHA256(timestamp + "\n" + secret, secret)

## API Version

v1

## Documentation

- [Feishu Bot Webhook](https://open.feishu.cn/document/ukTMukTMukTM/ucTM5YjL3ETO24yNxkjN)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Feishu webhook signatures use HMAC-SHA256.
- The timestamp and signature are added to the URL query parameters.
- The secret is obtained when creating a custom bot in Feishu.