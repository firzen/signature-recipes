# Google Sign-In v1 Signature Example

## Signature Algorithm

RSASSA-PKCS1-v1_5 with SHA-256 (RS256)

## String to Sign

JWT ID Token verification using Google's public keys

## Secret

Google's public keys (fetched from https://www.googleapis.com/oauth2/v3/certs)

## Expected Signature

JWT ID Token signed with RS256

## API Version

v1

## Documentation

- [Google Sign-In](https://developers.google.com/identity/sign-in/web/backend-auth)
- [Verify ID Tokens](https://developers.google.com/identity/protocols/oauth2/openid-connect#validatinganidtoken)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Google Sign-In uses JWT ID tokens signed with RSA-SHA256.
- Verify tokens using Google's public keys.
- The audience must match your OAuth 2.0 client ID.
- Token expires after 1 hour.