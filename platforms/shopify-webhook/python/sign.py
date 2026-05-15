import hmac
import hashlib
import base64

def generate_signature(payload: str, api_secret: str) -> str:
    hash_bytes = hmac.new(api_secret.encode('utf-8'), payload.encode('utf-8'), hashlib.sha256).digest()
    return base64.b64encode(hash_bytes).decode('utf-8')

def verify_webhook_signature(payload: str, signature_header: str, api_secret: str) -> bool:
    expected_signature = generate_signature(payload, api_secret)
    return hmac.compare_digest(expected_signature, signature_header)

if __name__ == "__main__":
    api_secret = "your_shopify_api_secret"
    payload = '{"id":123,"email":"test@example.com"}'
    signature = generate_signature(payload, api_secret)
    
    print("Generated Signature (X-Shopify-Hmac-Sha256):")
    print(signature)
    print()
    
    is_valid = verify_webhook_signature(payload, signature, api_secret)
    print(f"Signature verification result: {'VALID' if is_valid else 'INVALID'}")