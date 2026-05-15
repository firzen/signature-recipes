# OpenAI API Signature Example

## Signature Algorithm

Bearer Token (API Key)

## String to Sign

Not applicable for Bearer token authentication

## Secret

sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

## Expected Signature

Authorization: Bearer sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

## Supported Languages

- PHP
- Java
- Go
- Python
- Node.js

## Notes

- The API key is sent directly in the Authorization header.
- No additional signature calculation is required for API requests.
- Keep your API key secure and never expose it in client-side code.