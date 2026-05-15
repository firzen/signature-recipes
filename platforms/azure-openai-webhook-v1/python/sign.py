import hmac
import hashlib
import time

def generate_signature(timestamp: str, payload: str, webhook_secret: str) -> str:
    string_to_sign = f"{timestamp}.{payload}"
    signature = hmac.new(webhook_secret.encode('utf-8'), string_to_sign.encode('utf-8'), hashlib.sha256).hexdigest()
    return f"{timestamp}.{signature}"

def verify_webhook_signature(payload: str, signature_header: str, webhook_secret: str) -> bool:
    parts = signature_header.split('.', 1)
    timestamp = parts[0]
    actual_signature = parts[1]
    
    string_to_sign = f"{timestamp}.{payload}"
    expected_signature = hmac.new(webhook_secret.encode('utf-8'), string_to_sign.encode('utf-8'), hashlib.sha256).hexdigest()
    
    return hmac.compare_digest(expected_signature, actual_signature)

if __name__ == "__main__":
    webhook_secret = "your_azure_openai_webhook_secret"
    payload = '{"eventType":"completion","data":{"id":"cmpl-xxx"}}'
    timestamp = str(int(time.time()))
    signature_header = generate_signature(timestamp, payload, webhook_secret)
    
    print("Generated Signature Header (Azure-Signature):")
    print(signature_header)
    print()
    
    is_valid = verify_webhook_signature(payload, signature_header, webhook_secret)
    print(f"Signature verification result: {'VALID' if is_valid else 'INVALID'}")