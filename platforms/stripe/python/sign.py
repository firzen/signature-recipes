import hmac
import hashlib

def create_authorization_header(secret_key):
    return f"Bearer {secret_key}"

def hmac_sha256(data: str, secret: str) -> str:
    return hmac.new(secret.encode('utf-8'), data.encode('utf-8'), hashlib.sha256).hexdigest()

def verify_webhook_signature(payload: str, signature_header: str, webhook_secret: str) -> bool:
    parts = signature_header.split(',')
    timestamp = None
    signature = None
    
    for part in parts:
        key_value = part.split('=', 1)
        if key_value[0] == 't':
            timestamp = key_value[1]
        elif key_value[0] == 'v1':
            signature = key_value[1]
    
    if timestamp is None or signature is None:
        return False
    
    signed_payload = f"{timestamp}.{payload}"
    expected_signature = hmac_sha256(signed_payload, webhook_secret)
    
    return hmac.compare_digest(expected_signature, signature)

if __name__ == "__main__":
    secret_key = "sk_test_your_secret_key"
    webhook_secret = "whsec_your_webhook_secret"
    
    print(f"Authorization Header: {create_authorization_header(secret_key)}")
    print()
    
    payload = '{"id":"evt_123","object":"event"}'
    timestamp = "1710000000"
    signed_payload = f"{timestamp}.{payload}"
    calculated_signature = hmac_sha256(signed_payload, webhook_secret)
    signature_header = f"t={timestamp},v1={calculated_signature}"
    
    print("Generated Signature Header:")
    print(signature_header)
    print()
    
    is_valid = verify_webhook_signature(payload, signature_header, webhook_secret)
    print(f"Signature verification result: {'VALID' if is_valid else 'INVALID'}")