# AWS Signature Version 4 Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

```
AWS4-HMAC-SHA256
{timestamp}
{date}/{region}/{service}/aws4_request
{hex(sha256(canonical_request))}
```

## Secret

AWS Secret Access Key

## Expected Signature

HMAC-SHA256(key, string_to_sign)

## API Version

Signature Version 4

## Documentation

- [AWS Signature Version 4](https://docs.aws.amazon.com/general/latest/gr/signature-version-4.html)
- [Signing Requests](https://docs.aws.amazon.com/general/latest/gr/sigv4_signing.html)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- AWS Signature Version 4 is the current standard.
- Four-step signing process: create canonical request, create string to sign, calculate signature, create authorization header.
- Always use UTC timestamps.
- Include all required headers in the canonical request.