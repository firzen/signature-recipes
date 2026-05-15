# AWS Bedrock Signature Version 4 Example

## Signature Algorithm

HMAC-SHA256 (AWS Signature Version 4)

## String to Sign

```
AWS4-HMAC-SHA256
{timestamp}
{date}/{region}/bedrock/aws4_request
{hex(sha256(canonical_request))}
```

## Secret

AWS Secret Access Key

## Expected Signature

HMAC-SHA256(key, string_to_sign)

## API Version

Signature Version 4

## Documentation

- [AWS Bedrock API](https://docs.aws.amazon.com/bedrock/latest/APIReference/API_Operations.html)
- [AWS Signature Version 4](https://docs.aws.amazon.com/general/latest/gr/signature-version-4.html)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- AWS Bedrock uses AWS Signature Version 4 for API authentication.
- Service name is `bedrock`.
- Always use UTC timestamps.
- Include all required headers in the canonical request.