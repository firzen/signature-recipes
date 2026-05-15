# Aliyun API v1 Signature Example

## Signature Algorithm

HMAC-SHA1 with Base64 encoding

## String to Sign

```
{METHOD}\n${HOST}\n${PATH}\n${SORTED_QUERY_STRING}
```

## Secret

Access Key Secret

## Expected Signature

Base64(HMAC-SHA1(canonicalized_query_string, access_key_secret))

## API Version

v1

## Documentation

- [Aliyun Signature Algorithm](https://help.aliyun.com/document_detail/35747.html)
- [RAM API Signature](https://help.aliyun.com/document_detail/28616.html)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Aliyun uses HMAC-SHA1 signature algorithm.
- Query parameters must be sorted alphabetically and URL-encoded.
- The signature is Base64 encoded after HMAC-SHA1.
- Include the signature in the `Signature` query parameter.