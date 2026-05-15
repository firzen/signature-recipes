# Common Mistakes

This document lists common mistakes to avoid when implementing API signatures.

## Parameter Ordering

**Mistake:** Not sorting parameters alphabetically.

**Solution:** Always sort parameters by key name before creating the signature.

## URL Encoding

**Mistake:** Inconsistent URL encoding.

**Solution:** Use consistent encoding (usually RFC 3986) for all parameters.

## Timestamp Issues

**Mistake:** Not including a timestamp or using incorrect time zones.

**Solution:** Always include a UTC timestamp and validate it server-side.

## Case Sensitivity

**Mistake:** Mixing uppercase and lowercase in parameter names.

**Solution:** Be consistent - most APIs expect lowercase parameter names.

## Empty Parameters

**Mistake:** Including empty parameters in the signature.

**Solution:** Exclude empty parameters unless explicitly required.

## Secret Key Handling

**Mistake:** Hardcoding secret keys in client-side code.

**Solution:** Keep secret keys on the server side only.

## Hash Algorithm

**Mistake:** Using weak hash algorithms like MD5 or SHA-1.

**Solution:** Use SHA-256 or stronger.

## Replay Attacks

**Mistake:** Not implementing nonce/timestamp validation.

**Solution:** Implement server-side nonce tracking and timestamp expiration.

## Canonical Request Format

**Mistake:** Incorrectly formatting the canonical request (especially for AWS V4).

**Solution:** Follow the exact format specified by the API documentation.

## Base64 Encoding

**Mistake:** Forgetting to base64 encode the signature when required.

**Solution:** Check the API documentation for encoding requirements.

## Webhook Verification

**Mistake:** Not verifying webhook signatures.

**Solution:** Always verify webhook signatures before processing the payload.

## RSA Key Format

**Mistake:** Using the wrong key format (PEM vs DER).

**Solution:** Use the correct key format as specified by the API.

## Time Synchronization

**Mistake:** Server and client clocks are out of sync.

**Solution:** Use NTP to synchronize clocks or allow a reasonable time window.

## Query String Building

**Mistake:** Incorrectly building query strings with missing or extra ampersands.

**Solution:** Carefully construct query strings with proper separators.

## Binary Data Handling

**Mistake:** Not properly handling binary data in signatures.

**Solution:** Convert binary data to hex or base64 before signing.

## Encoding Special Characters

**Mistake:** Not encoding special characters in parameters.

**Solution:** URL-encode all special characters according to RFC 3986.

## Case Studies

### AWS Signature V4 Common Issues
- Forgetting to include the host header
- Incorrect credential scope format
- Missing the terminating newline in canonical headers

### WeChat Pay Common Issues
- Including empty parameters in signature
- Incorrect MD5 case (should be uppercase)
- Missing the key parameter at the end

### Stripe Webhook Common Issues
- Not including the timestamp in the signature
- Incorrect header parsing
- Using the wrong webhook secret