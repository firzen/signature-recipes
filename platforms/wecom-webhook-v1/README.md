# WeCom Webhook v1 Signature Example

## Signature Algorithm

SHA-256

## String to Sign

`timestamp + "\n" + secret`

## Secret

WeCom Bot Secret (obtained from WeCom Admin Console)

## Expected Signature

SHA-256(timestamp + "\n" + secret)

## API Version

v1

## Documentation

- [WeCom Bot Webhook](https://developer.work.weixin.qq.com/document/path/91770)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- WeCom webhook signatures use SHA-256.
- The timestamp and signature are added to the URL query parameters.
- The secret is obtained when creating a bot in WeCom.