# WeChat Login v1 Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

`appid={appid}&secret={secret}&js_code={code}&grant_type=authorization_code`

## Secret

WeChat App Secret

## Expected Signature

HMAC-SHA256(query_string, app_secret)

## API Version

v1

## Documentation

- [微信登录](https://developers.weixin.qq.com/doc/oplatform/Mobile_App/WeChat_Login/Development_Guide.html)
- [微信小程序登录](https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- WeChat Login uses OAuth 2.0 for authentication.
- Get access token using code via WeChat's API.
- The app secret is obtained from WeChat Developer Platform.