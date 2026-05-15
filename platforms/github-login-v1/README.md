# GitHub Login v1 Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

`client_secret|access_token`

## Secret

GitHub Client Secret

## Expected Signature

HMAC-SHA256(client_secret, access_token)

## API Version

v3

## Documentation

- [GitHub OAuth](https://docs.github.com/en/apps/oauth-apps/building-oauth-apps/authorizing-oauth-apps)
- [GitHub Apps](https://docs.github.com/en/apps/creating-github-apps/authenticating-with-a-github-app/about-authentication-with-a-github-app)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- GitHub OAuth uses access tokens for authentication.
- Always validate access tokens using GitHub's API.
- The client secret is obtained from GitHub Developer Settings.