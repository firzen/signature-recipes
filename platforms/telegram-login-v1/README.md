# Telegram Login Widget v1 Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

data_check_string (sorted key=value pairs)

## Secret

Bot Token (without "bot" prefix)

## Expected Signature

HMAC-SHA256(data_check_string, SHA256(bot_token))

## API Version

v1

## Documentation

- [Telegram Login Widget](https://core.telegram.org/widgets/login)
- [Login Widget Documentation](https://core.telegram.org/widgets/login#checking-authorization)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Telegram Login Widget uses HMAC-SHA256 to verify authentication data.
- The data_check_string format: `auth_date=xxx\nfirst_name=xxx\nid=xxx\nlast_name=xxx\nphoto_url=xxx\nusername=xxx`
- The bot token without "bot" prefix is used as the secret.
- Always verify the auth_date to prevent replay attacks.