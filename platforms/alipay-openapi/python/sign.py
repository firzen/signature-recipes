import base64
import hashlib
import rsa
import time

def generate_signature(params: dict, private_key_pem: str) -> str:
    sorted_keys = sorted(k for k in params.keys() if k != 'sign')
    
    sign_string = ''
    for i, k in enumerate(sorted_keys):
        v = params[k]
        if v:
            if i > 0:
                sign_string += '&'
            sign_string += f"{k}={v}"
    
    private_key = rsa.PrivateKey.load_pkcs1(private_key_pem.encode('utf-8'), format='PEM')
    hash_bytes = hashlib.sha256(sign_string.encode('utf-8')).digest()
    signature = rsa.sign_hash(hash_bytes, private_key, 'SHA-256')
    
    return base64.b64encode(signature).decode('utf-8')

if __name__ == "__main__":
    private_key = """-----BEGIN RSA PRIVATE KEY-----
your_private_key_here
-----END RSA PRIVATE KEY-----"""
    
    params = {
        'app_id': 'your_app_id',
        'method': 'alipay.trade.app.pay',
        'charset': 'UTF-8',
        'sign_type': 'RSA2',
        'timestamp': time.strftime('%Y-%m-%d %H:%M:%S'),
        'version': '1.0',
        'biz_content': '{"out_trade_no":"202401010001","total_amount":"0.01","subject":"Test"}'
    }
    
    params['sign'] = generate_signature(params, private_key)
    
    print("Request Parameters:")
    for k in sorted(params.keys()):
        print(f"  {k}: {params[k]}")