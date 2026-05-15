# WeChat Pay Signature Example

## Signature Algorithm

MD5

## String to Sign

appid=wx1234567890&body=Test+Payment&mch_id=1234567890&nonce_str=abcdef...&notify_url=https%3A%2F%2Fexample.com%2Fnotify&out_trade_no=202401010001&spbill_create_ip=192.168.1.1&total_fee=100&trade_type=APP&key=your_secret_key

## Secret

your_secret_key

## Expected Signature

ABC123DEF456... (uppercase MD5 hex)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Parameters must be sorted alphabetically by key.
- Exclude empty parameters and the `sign` parameter itself.
- Append `&key=your_secret_key` at the end.
- The final signature must be converted to uppercase.
- URL encoding follows RFC 3986 standards.