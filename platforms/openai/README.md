# OpenAI API Signature Example

## Signature Algorithm

Bearer Token (API Key)

## String to Sign

Authorization: Bearer {api_key}

## Secret

OpenAI API Key

## Expected Signature

Bearer sk-xxx...

## API Version

v1

## Documentation

- [OpenAI API Documentation](https://platform.openai.com/docs/api-reference/authentication)
- [API Authentication](https://platform.openai.com/docs/api-reference/authentication)

## Supported Languages

- PHP
- Java
- Go
- Python
- Node.js

## Notes

- OpenAI API uses Bearer Token authentication.
- The API key should be placed in the Authorization header.
- Never expose your API key in client-side code.
- Rate limits apply based on your API tier.