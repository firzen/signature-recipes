import hmac
import hashlib
import urllib.parse

def build_query_string(params: dict) -> str:
    sorted_keys = sorted(params.keys())
    return '&'.join([f"{k}={urllib.parse.quote(params[k])}" for k in sorted_keys])

def sign_query_string(params: dict, app_secret: str) -> str:
    query_string = build_query_string(params)
    return hmac.new(app_secret.encode('utf-8'), query_string.encode('utf-8'), hashlib.sha256).hexdigest()

if __name__ == "__main__":
    app_id = "your-wechat-app-id"
    app_secret = "your-wechat-app-secret"
    code = "001xxx"

    params = {
        'appid': app_id,
        'secret': app_secret,
        'js_code': code,
        'grant_type': 'authorization_code'
    }

    signature = sign_query_string(params, app_secret)

    print("Query String:")
    print(build_query_string(params))
    print()
    print("Signature:")
    print(signature)