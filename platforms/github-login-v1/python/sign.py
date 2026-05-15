import hmac
import hashlib

def validate_access_token(access_token: str, client_secret: str) -> str:
    return hmac.new(client_secret.encode('utf-8'), access_token.encode('utf-8'), hashlib.sha256).hexdigest()

if __name__ == "__main__":
    client_secret = "your-github-client-secret"
    access_token = "gho_..."
    
    signature = validate_access_token(access_token, client_secret)
    
    print("HMAC Signature:")
    print(signature)