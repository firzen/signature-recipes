import hashlib
import time

def generate_signature(timestamp: int, secret: str) -> str:
    string_to_sign = f"{timestamp}\n{secret}"
    return hashlib.sha256(string_to_sign.encode('utf-8')).hexdigest()

def build_webhook_url(webhook_url: str, secret: str) -> str:
    timestamp = int(time.time())
    signature = generate_signature(timestamp, secret)
    return f"{webhook_url}&timestamp={timestamp}&sign={signature}"

if __name__ == "__main__":
    webhook_url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=your-webhook-key"
    secret = "your-wecom-bot-secret"
    
    signed_url = build_webhook_url(webhook_url, secret)
    
    print("Signed Webhook URL:")
    print(signed_url)
    print()
    print(f"Timestamp: {int(time.time())}")
    print(f"Signature: {generate_signature(int(time.time()), secret)}")