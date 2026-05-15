# Binance API Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

timestamp=1710000000000&symbol=BTCUSDT&side=BUY&type=LIMIT

## Secret

test_secret

## Expected Signature

abc123def456...

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Query parameters must be sorted alphabetically by key.
- Do not URL encode the final signature.
- The signature is appended as a query parameter: `&signature=xxx`.
- Timestamp must be in milliseconds.