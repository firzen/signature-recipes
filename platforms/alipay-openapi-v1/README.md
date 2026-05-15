# Alipay OpenAPI v1 Signature Example

## Signature Algorithm

RSA-SHA256 (with private key)

## String to Sign

sorted_params_query_string

## Secret

App Private Key

## Expected Signature

Base64(RSA-SHA256(string_to_sign, private_key))

## API Version

v1

## Documentation

- [支付宝开放平台](https://open.alipay.com/)
- [签名机制](https://opendocs.alipay.com/common/02mse3)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Alipay OpenAPI uses RSA-SHA256 for request signing.
- Parameters must be sorted alphabetically by key.
- The signature is added as a parameter: `sign=xxx`.
- The signature type is specified: `sign_type=RSA2`.
- Use PKCS#8 format for private key.