import hmac
import hashlib
import time

def generate_signature(timestamp: str, payload: str, webhook_secret: str) -> str:
    string_to_sign = f"{timestamp}.{payload}"
    signature = hmac.new(webhook_secret.encode('utf-8'), string_to_sign.encode('utf-8'), hashlib.sha256).hexdigest()
    return f"t={timestamp},v1={signature}"

def verify_webhook_signature(payload: str, signature_header: str, webhook_secret: str) -> bool:
    parts = signature_header.split(',')
    timestamp = parts[0].replace('t=', '')
    actual_signature = parts[1].replace('v1=', '')
    
    string_to_sign = f"{timestamp}.{payload}"
    expected_signature = hmac.new(webhook_secret.encode('utf-8'), string_to_sign.encode('utf-8'), hashlib.sha256).hexdigest()
    
    return hmac.compare_digest(expected_signature, actual_signature)

if __name__ == "__main__":
    webhook_secret = "your_fal_webhook_secret"
    payload = '{"id":"xxx","status":"completed","output":{"result":"hello"}}'
    timestamp = str(int(time.time()))
    signature_header = generate_signature(timestamp, payload, webhook_secret)
    
    print("Generated Signature Header:")
    print(signature_header)
    print()
    
    is_valid = verify_webhook_signature(payload, signature_header, webhook_secret)
    print(f"Signature verification result: {'VALID' if is_valid else 'INVALID'}")