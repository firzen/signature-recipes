# AWS V4 Signature Example

## Signature Algorithm

HMAC-SHA256 (AWS Signature Version 4)

## String to Sign

```
AWS4-HMAC-SHA256
20240101T000000Z
20240101/us-east-1/s3/aws4_request
hex(SHA256(canonical_request))
```

## Secret

wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY

## Expected Signature

Authorization: AWS4-HMAC-SHA256 Credential=AKIAIOSFODNN7EXAMPLE/20240101/us-east-1/s3/aws4_request, SignedHeaders=host;x-amz-date, Signature=abc123...

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Four-step signing process: canonical request → string to sign → signature → authorization header.
- Keys are derived using HMAC-SHA256 in order: kSecret → kDate → kRegion → kService → kSigning.
- Timestamp must be in ISO 8601 format (YYYYMMDD'T'HHMMSS'Z').
- Headers must be lowercase and sorted alphabetically.