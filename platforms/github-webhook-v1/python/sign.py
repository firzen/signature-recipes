import hmac
import hashlib

def hmac_sha256(data: str, secret: str) -> str:
    return f"sha256={hmac.new(secret.encode('utf-8'), data.encode('utf-8'), hashlib.sha256).hexdigest()}"

def verify_webhook_signature(payload: str, signature_header: str, webhook_secret: str) -> bool:
    expected_signature = hmac_sha256(payload, webhook_secret)
    return hmac.compare_digest(expected_signature, signature_header)

if __name__ == "__main__":
    webhook_secret = "your_webhook_secret"
    payload = '{"action":"created","ref":"refs/heads/main"}'
    
    signature_header = hmac_sha256(payload, webhook_secret)
    print("Generated Signature Header:")
    print(signature_header)
    print()
    
    is_valid = verify_webhook_signature(payload, signature_header, webhook_secret)
    print(f"Signature verification result: {'VALID' if is_valid else 'INVALID'}")