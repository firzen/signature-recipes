# DingTalk Webhook Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

timestamp + "\n" + secret

## Secret

Webhook Secret (SECxxx...)

## Expected Signature

Base64(HMAC-SHA256(timestamp + "\n" + secret))

## API Version

v1

## Documentation

- [钉钉机器人文档](https://developers.dingtalk.com/document/robots/custom-robot-access)
- [签名机制](https://developers.dingtalk.com/document/robots/custom-robot-access#title-72m-8ag-pqw)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- DingTalk webhooks use timestamp and secret to generate signature.
- The signature URL parameter format is: `sign=xxx`.
- Always include timestamp in the request URL: `timestamp=xxx`.
- The signature is Base64 encoded after HMAC-SHA256.