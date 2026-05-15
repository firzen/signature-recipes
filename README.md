# Signature Recipes

A collection of minimal, dependency-light API signature generation examples across different platforms and programming languages.

This repository focuses only on the signing logic:
- how to build the string to sign
- how to generate HMAC / RSA / SHA signatures
- how to format headers or query parameters
- how to verify the output with test vectors

It is not a full SDK.

@todo
signature-recipes/
├── README.md
├── LICENSE
├── CONTRIBUTING.md
├── docs/
│   ├── signing-basics.md
│   ├── common-mistakes.md
│   └── test-vectors.md
├── platforms/
│   ├── openai/
│   │   ├── README.md
│   │   ├── php/
│   │   │   └── sign.php
│   │   ├── java/
│   │   │   └── SignExample.java
│   │   ├── go/
│   │   │   └── main.go
│   │   ├── python/
│   │   │   └── sign.py
│   │   └── nodejs/
│   │       └── sign.js
│   ├── stripe/
│   │   ├── README.md
│   │   ├── php/
│   │   ├── java/
│   │   └── go/
│   ├── binance/
│   │   ├── README.md
│   │   ├── php/
│   │   ├── java/
│   │   └── go/
│   ├── aws-v4/
│   │   ├── README.md
│   │   ├── php/
│   │   ├── java/
│   │   └── go/
│   └── wechat-pay/
│       ├── README.md
│       ├── php/
│       ├── java/
│       └── go/
└── tests/
    ├── test-vectors.json
    └── README.md
