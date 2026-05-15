# README Template Guidelines

## Overview

This guideline is used to standardize the format of README.md files for each platform in the signature-recipes project, ensuring consistent documentation style and complete information.

## File Structure

Each platform's README.md should include the following sections:

### 1. Title

```markdown
# {Platform Name} API Signature Example
```

### 2. Signature Algorithm

```markdown
## Signature Algorithm

{Algorithm Name}
```

### 3. String to Sign

```markdown
## String to Sign

{Description of string to sign format}
```

### 4. Secret

```markdown
## Secret

{Secret name and how to obtain it}
```

### 5. Expected Signature

```markdown
## Expected Signature

{Signature format description}
```

### 6. API Version

```markdown
## API Version

{API version number}
```

### 7. Documentation

```markdown
## Documentation

- [Official Documentation]({Documentation URL})
- [Signature Guide]({Signature Guide URL})
```

### 8. Supported Languages

```markdown
## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript
```

### 9. Notes

```markdown
## Notes

- {Note 1}
- {Note 2}
- {Note 3}
```

## Naming Conventions

### Platform Folder Naming

Platform folder names should use lowercase letters and hyphens, following the format:

```
{platform}-{version}
```

Examples:
- `aws-v4` - AWS Signature Version 4
- `wechat-pay-v3` - WeChat Pay API v3
- `openai-v1` - OpenAI API v1

### File Naming

Language implementation files should follow these naming conventions:

| Language | File Path |
|----------|-----------|
| PHP | `php/sign.php` |
| Java | `java/SignExample.java` |
| Go | `go/main.go` |
| Python | `python/sign.py` |
| TypeScript | `typescript/sign.ts` |

## Content Guidelines

### Language Requirements

- README.md should be written in English
- Code comments should be written in English (consistent with code)

### Format Requirements

- Use standard Markdown syntax
- Use correct language identifiers for code blocks
- Use full URLs for links
- Use unordered lists for multiple items

### Version Number Format

- Version numbers use the `v{major}` format, e.g., `v1`, `v3`, `v4`
- Platforms without version numbers can omit the version suffix

## Example

```markdown
# Stripe API Signature Example

## Signature Algorithm

HMAC-SHA256

## String to Sign

POST\n/api/v1/charges\n{request_body}

## Secret

Stripe Webhook Secret

## Expected Signature

HMAC-SHA256(request_body, webhook_secret)

## API Version

v1

## Documentation

- [Stripe Webhook Signatures](https://stripe.com/docs/webhooks/signatures)

## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript

## Notes

- Always verify webhook signatures before processing
- The signature header is `Stripe-Signature`
- Use the webhook secret from your Stripe dashboard
```