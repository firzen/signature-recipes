# WeChat Pay API v2 Signature Example

## Signature Algorithm

MD5

## String to Sign

sorted_params_query_string (key=value&key=value)

## Secret

API Key

## Expected Signature

MD5(sorted_params + "&key=" + api_key)

## API Version

v2

## Documentation

- [微信支付 API v2](https://pay.weixin.qq.com/wiki/doc/api/index.html)
- [签名规范](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Parameters must be sorted alphabetically.
- Empty parameters should be excluded.
- The final signature is uppercase.
- API Key is set in WeChat Pay merchant backend.