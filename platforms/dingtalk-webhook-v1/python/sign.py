import hmac
import hashlib
import base64
import time
import urllib.parse

def generate_signature(timestamp: str, secret: str) -> str:
    string_to_sign = f"{timestamp}\n{secret}"
    hash_bytes = hmac.new(secret.encode('utf-8'), string_to_sign.encode('utf-8'), hashlib.sha256).digest()
    return base64.b64encode(hash_bytes).decode('utf-8')

if __name__ == "__main__":
    secret = "SECyour_secret"
    timestamp = str(int(time.time() * 1000))
    signature = generate_signature(timestamp, secret)
    
    print(f"Timestamp: {timestamp}")
    print(f"Signature: {signature}")
    print()
    print("Webhook URL with signature:")
    print(f"https://oapi.dingtalk.com/robot/send?access_token=xxx&timestamp={timestamp}&sign={urllib.parse.quote(signature)}")