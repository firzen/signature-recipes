import hashlib
import random
import string

def generate_nonce_str(length: int = 32) -> str:
    chars = string.ascii_letters + string.digits
    return ''.join(random.choice(chars) for _ in range(length))

def sign_params(params: dict, key: str) -> str:
    sorted_params = sorted(params.items())
    
    sign_string = ''
    for k, v in sorted_params:
        if v and k != 'sign':
            if sign_string:
                sign_string += '&'
            sign_string += f"{k}={v}"
    
    sign_string += f"&key={key}"
    
    return hashlib.md5(sign_string.encode('utf-8')).hexdigest().upper()

def build_payment_request(appid: str, mch_id: str, key: str, body: str, out_trade_no: str, 
                          total_fee: int, spbill_create_ip: str, notify_url: str, trade_type: str) -> dict:
    params = {
        'appid': appid,
        'mch_id': mch_id,
        'nonce_str': generate_nonce_str(),
        'body': body,
        'out_trade_no': out_trade_no,
        'total_fee': str(total_fee),
        'spbill_create_ip': spbill_create_ip,
        'notify_url': notify_url,
        'trade_type': trade_type
    }
    
    params['sign'] = sign_params(params, key)
    
    return params

if __name__ == "__main__":
    appid = "wx1234567890abcdef"
    mch_id = "1234567890"
    key = "your_secret_key"
    
    params = build_payment_request(
        appid, mch_id, key,
        "Test Payment",
        "202401010001",
        100,
        "192.168.1.1",
        "https://example.com/notify",
        "APP"
    )
    
    print("Request Parameters:")
    for k, v in sorted(params.items()):
        print(f"  {k}: {v}")