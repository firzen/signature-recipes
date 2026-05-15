# Facebook Login v1 Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

`app_secret|access_token`

## Secret

Facebook App Secret

## Expected Signature

HMAC-SHA256(app_secret, access_token)

## API Version

v18.0

## Documentation

- [Facebook Login](https://developers.facebook.com/docs/facebook-login)
- [Access Token Debugger](https://developers.facebook.com/docs/graph-api/debugging/)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Facebook Login uses HMAC-SHA256 to validate access tokens.
- The app secret is obtained from Facebook Developer Console.
- Always verify access tokens server-side.
- Token validation can also be done via Facebook's debug endpoint.