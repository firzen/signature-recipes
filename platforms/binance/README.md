# Binance API Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

timestamp=1710000000000&symbol=BTCUSDT&side=BUY&type=LIMIT

## Secret

API Secret Key

## Expected Signature

HMAC-SHA256(query_string, api_secret)

## API Version

v3

## Documentation

- [Binance API Documentation](https://binance-docs.github.io/apidocs/spot/en/#signed-trade-user-data-endpoints)
- [Signature Guide](https://binance-docs.github.io/apidocs/spot/en/#signed-endpoint-security)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Query parameters must be sorted or kept in the exact order required by the API.
- Do not URL encode the final signature unless the API requires it.
- Always include timestamp parameter.
- Use recvWindow parameter to control request time tolerance.