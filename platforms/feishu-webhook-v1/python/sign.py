import hmac
import hashlib
import base64
import time
import urllib.parse

def generate_signature(timestamp: int, secret: str) -> str:
    string_to_sign = f"{timestamp}\n{secret}"
    return base64.b64encode(hmac.new(secret.encode('utf-8'), string_to_sign.encode('utf-8'), hashlib.sha256).digest()).decode('utf-8')

def build_webhook_url(webhook_url: str, secret: str) -> str:
    timestamp = int(time.time())
    signature = generate_signature(timestamp, secret)
    return f"{webhook_url}&timestamp={timestamp}&sign={urllib.parse.quote(signature)}"

if __name__ == "__main__":
    webhook_url = "https://open.feishu.cn/open-apis/bot/v2/hook/your-webhook-id"
    secret = "your-feishu-bot-secret"
    
    signed_url = build_webhook_url(webhook_url, secret)
    
    print("Signed Webhook URL:")
    print(signed_url)
    print()
    print(f"Timestamp: {int(time.time())}")
    print(f"Signature: {generate_signature(int(time.time()), secret)}")