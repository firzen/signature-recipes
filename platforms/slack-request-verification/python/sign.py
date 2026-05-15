import hmac
import hashlib
import time

def generate_signature(timestamp: str, body: str, signing_secret: str) -> str:
    string_to_sign = f"v0:{timestamp}:{body}"
    return f"v0={hmac.new(signing_secret.encode('utf-8'), string_to_sign.encode('utf-8'), hashlib.sha256).hexdigest()}"

def verify_request(timestamp: str, body: str, signature_header: str, signing_secret: str) -> bool:
    expected_signature = generate_signature(timestamp, body, signing_secret)
    return hmac.compare_digest(expected_signature, signature_header)

if __name__ == "__main__":
    signing_secret = "your_slack_signing_secret"
    timestamp = str(int(time.time()))
    body = '{"token":"abc123","team_id":"T123"}'
    signature = generate_signature(timestamp, body, signing_secret)
    
    print(f"Timestamp: {timestamp}")
    print(f"Generated Signature: {signature}")
    print()
    
    is_valid = verify_request(timestamp, body, signature, signing_secret)
    print(f"Signature verification result: {'VALID' if is_valid else 'INVALID'}")