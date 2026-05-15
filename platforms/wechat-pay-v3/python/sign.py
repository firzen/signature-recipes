import hmac
import hashlib
import random
import string
import time

def generate_nonce_str(length: int = 32) -> str:
    chars = string.ascii_letters + string.digits
    return ''.join(random.choice(chars) for _ in range(length))

def build_string_to_sign(method: str, uri: str, timestamp: str, nonce: str, body: str) -> str:
    return f"{method}\n{uri}\n{timestamp}\n{nonce}\n{body}\n"

def generate_signature(method: str, uri: str, timestamp: str, nonce: str, body: str, api_v3_key: str) -> str:
    string_to_sign = build_string_to_sign(method, uri, timestamp, nonce, body)
    return hmac.new(api_v3_key.encode('utf-8'), string_to_sign.encode('utf-8'), hashlib.sha256).hexdigest()

def build_authorization_header(mchid: str, api_v3_key: str, serial_no: str, method: str, uri: str, body: str) -> str:
    timestamp = str(int(time.time()))
    nonce = generate_nonce_str()
    signature = generate_signature(method, uri, timestamp, nonce, body, api_v3_key)
    
    return f'WECHATPAY2-SHA256-RSA2048 mchid="{mchid}",nonce_str="{nonce}",signature="{signature}",timestamp="{timestamp}",serial_no="{serial_no}"'

if __name__ == "__main__":
    mchid = "1234567890"
    api_v3_key = "your_api_v3_key"
    serial_no = "your_certificate_serial_number"
    method = "POST"
    uri = "/v3/pay/transactions/app"
    body = '{"mchid":"1234567890","out_trade_no":"202401010001","amount":{"total":100},"description":"Test"}'
    
    authorization = build_authorization_header(mchid, api_v3_key, serial_no, method, uri, body)
    
    print("Authorization Header:")
    print(authorization)