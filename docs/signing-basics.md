# Signing Basics

This document explains the fundamentals of API signature generation.

## What is API Signing?

API signing is a security mechanism used to authenticate requests and ensure data integrity. It involves creating a unique signature using a secret key that only the client and server know.

## Common Signing Techniques

### HMAC (Hash-based Message Authentication Code)

HMAC is the most common signing method. It combines:
- A secret key
- A message (usually request parameters)
- A hash function (SHA-256, SHA-512, etc.)

### RSA Signatures

Used when asymmetric cryptography is needed. The client signs with a private key, and the server verifies with a public key.

## Best Practices

1. Always use HTTPS to prevent MITM attacks
2. Use strong hash algorithms (SHA-256 or higher)
3. Include a timestamp to prevent replay attacks
4. Include a nonce for additional security
5. Keep your secret keys secure

## Signature Generation Steps

1. Collect all request parameters
2. Sort parameters alphabetically
3. Create a query string or canonical request
4. Append the secret key
5. Apply the hash function
6. Include the signature in the request