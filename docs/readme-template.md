# README 编写规范

## 概述

本规范用于统一 signature-recipes 项目中各平台的 README.md 文件格式，确保文档风格一致、信息完整。

## 文件结构

每个平台的 README.md 应包含以下章节：

### 1. 标题

```markdown
# {Platform Name} API Signature Example
```

### 2. Signature Algorithm（签名算法）

```markdown
## Signature Algorithm

{算法名称}
```

### 3. String to Sign（待签名字符串格式）

```markdown
## String to Sign

{待签名字符串格式说明}
```

### 4. Secret（密钥说明）

```markdown
## Secret

{密钥名称及获取方式说明}
```

### 5. Expected Signature（预期签名格式）

```markdown
## Expected Signature

{签名格式说明}
```

### 6. API Version（接口版本）

```markdown
## API Version

{接口版本号}
```

### 7. Documentation（官方文档地址）

```markdown
## Documentation

- [官方文档]({文档URL})
- [签名说明]({签名说明URL})
```

### 8. Supported Languages（支持语言）

```markdown
## Supported Languages

- PHP
- Java
- Go
- Python
- TypeScript
```

### 9. Notes（注意事项）

```markdown
## Notes

- {注意事项1}
- {注意事项2}
- {注意事项3}
```

## 命名规范

### 平台文件夹命名

平台文件夹名称应采用小写字母和连字符，格式为：

```
{platform}-{version}
```

示例：
- `aws-v4` - AWS Signature Version 4
- `wechat-pay-v3` - 微信支付 API v3
- `openai-v1` - OpenAI API v1

### 文件命名

各语言实现文件应统一命名：

| 语言 | 文件路径 |
|------|----------|
| PHP | `php/sign.php` |
| Java | `java/SignExample.java` |
| Go | `go/main.go` |
| Python | `python/sign.py` |
| TypeScript | `typescript/sign.ts` |

## 内容规范

### 语言要求

- README.md 使用中文编写
- 代码注释使用英文编写（与代码保持一致）

### 格式要求

- 使用 Markdown 标准语法
- 代码块使用正确的语言标识
- 链接使用完整 URL
- 使用无序列表组织多项内容

### 版本号格式

- 版本号使用 `v{major}` 格式，如 `v1`, `v3`, `v4`
- 无版本号的平台可省略版本号后缀

## 示例

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