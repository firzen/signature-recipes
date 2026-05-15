import base64
import hashlib
import hmac
import time
import urllib.parse

def percent_encode(s: str) -> str:
    return urllib.parse.quote(s, safe='-_.~').replace('+', '%20').replace('*', '%2A')

def build_canonicalized_query_string(params: dict) -> str:
    sorted_keys = sorted([k for k in params.keys() if k != 'Signature'])
    return '&'.join([f"{percent_encode(k)}={percent_encode(params[k])}" for k in sorted_keys])

def sign(access_key_secret: str, method: str, host: str, path: str, params: dict) -> str:
    canonicalized_query_string = build_canonicalized_query_string(params)
    string_to_sign = f"{method}\n{host}\n{path}\n{canonicalized_query_string}"
    
    signature = hmac.new(access_key_secret.encode('utf-8'), string_to_sign.encode('utf-8'), hashlib.sha1).digest()
    return base64.b64encode(signature).decode('utf-8')

if __name__ == "__main__":
    access_key_id = "your_access_key_id"
    access_key_secret = "your_access_key_secret"
    method = "GET"
    host = "ecs.aliyuncs.com"
    path = "/"

    params = {
        "Format": "JSON",
        "Version": "2014-05-26",
        "AccessKeyId": access_key_id,
        "SignatureMethod": "HMAC-SHA1",
        "Timestamp": time.strftime("%Y-%m-%dT%H:%M:%SZ", time.gmtime()),
        "SignatureVersion": "1.0",
        "SignatureNonce": str(int(time.time() * 1000000)),
        "Action": "DescribeRegions"
    }

    signature = sign(access_key_secret, method, host, path, params)
    params["Signature"] = signature

    print("Signature:")
    print(signature)
    print()
    print("Full URL:")
    print(f"https://{host}{path}?{build_canonicalized_query_string(params)}")