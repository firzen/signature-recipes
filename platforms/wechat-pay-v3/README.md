# WeChat Pay API v3 Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

HTTP_METHOD + "\n" + URI + "\n" + TIMESTAMP + "\n" + NONCE + "\n" + BODY + "\n"

## Secret

API v3 Key

## Expected Signature

HMAC-SHA256(string_to_sign, api_v3_key)

## API Version

v3

## Documentation

- [微信支付 API v3](https://pay.weixin.qq.com/wiki/doc/apiv3/index.shtml)
- [签名生成](https://pay.weixin.qq.com/wiki/doc/apiv3/wechatpay/wechatpay4_0.shtml)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- WeChat Pay API v3 uses HMAC-SHA256 for request signing.
- The signature is placed in the `Authorization` header.
- Format: `WECHATPAY2-SHA256-RSA2048 mchid="xxx",nonce_str="xxx",signature="xxx",timestamp="xxx",serial_no="xxx"`
- API v3 Key is set in WeChat Pay merchant backend.