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