# Google Vertex AI JWT Example

## Signature Algorithm

RSASSA-PKCS1-v1_5 with SHA-256 (RS256)

## String to Sign

JWT claims encoded as JSON and base64url encoded:
```json
{
  "alg": "RS256",
  "typ": "JWT"
}
.{
  "iss": "service-account@project.iam.gserviceaccount.com",
  "sub": "service-account@project.iam.gserviceaccount.com",
  "aud": "https://aiplatform.googleapis.com/",
  "exp": 1710000000,
  "iat": 1710000000
}
```

## Secret

Google Cloud Service Account Private Key (PEM format)

## Expected Signature

JWT token with RS256 signature

## API Version

v1

## Documentation

- [Google Vertex AI Authentication](https://cloud.google.com/vertex-ai/docs/generative-ai/auth)
- [Creating JWTs](https://cloud.google.com/apis/docs/system-parameters#jwt)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Google Vertex AI uses JWT tokens signed with RSA-SHA256.
- The JWT is used as a Bearer token in the Authorization header.
- The private key is obtained from Google Cloud Console.
- Token expires after typically 1 hour (3600 seconds).