import hmac
import hashlib

def generate_signature(payload: str, webhook_secret: str) -> str:
    return hmac.new(webhook_secret.encode('utf-8'), payload.encode('utf-8'), hashlib.sha256).hexdigest()

def verify_webhook_signature(payload: str, signature_header: str, webhook_secret: str) -> bool:
    expected_signature = generate_signature(payload, webhook_secret)
    return hmac.compare_digest(expected_signature, signature_header)

if __name__ == "__main__":
    webhook_secret = "your_replicate_webhook_secret"
    payload = '{"id":"xxx","version":"xxx","status":"succeeded"}'
    signature = generate_signature(payload, webhook_secret)
    
    print("Generated Signature (X-Replicate-Signature):")
    print(signature)
    print()
    
    is_valid = verify_webhook_signature(payload, signature, webhook_secret)
    print(f"Signature verification result: {'VALID' if is_valid else 'INVALID'}")