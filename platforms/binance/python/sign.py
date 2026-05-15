import hmac
import hashlib
import urllib.parse
import time

def sign_request(params: dict, secret_key: str) -> str:
    sorted_params = sorted(params.items())
    
    query_string = '&'.join(f"{urllib.parse.quote(k)}={urllib.parse.quote(str(v))}" for k, v in sorted_params)
    
    signature = hmac.new(secret_key.encode('utf-8'), query_string.encode('utf-8'), hashlib.sha256).hexdigest()
    
    return f"{query_string}&signature={signature}"

def build_signed_request(api_key: str, secret_key: str, params: dict) -> dict:
    if 'timestamp' not in params:
        params['timestamp'] = int(time.time() * 1000)
    
    signed_query = sign_request(params, secret_key)
    
    return {
        'headers': {'X-MBX-APIKEY': api_key},
        'query': signed_query
    }

if __name__ == "__main__":
    api_key = "your_api_key"
    secret_key = "your_secret_key"
    
    params = {
        'symbol': 'BTCUSDT',
        'quantity': '0.001',
        'price': '40000.00'
    }
    
    request = build_signed_request(api_key, secret_key, params)
    
    print("Headers:")
    for key, value in request['headers'].items():
        print(f"  {key}: {value}")
    
    print("\nQuery String:")
    print(request['query'])