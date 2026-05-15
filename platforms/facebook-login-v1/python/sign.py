import hmac
import hashlib

def validate_access_token(access_token: str, app_secret: str) -> str:
    return hmac.new(app_secret.encode('utf-8'), access_token.encode('utf-8'), hashlib.sha256).hexdigest()

def verify_app_secret_proof(access_token: str, app_secret: str, app_secret_proof: str) -> bool:
    expected_proof = validate_access_token(access_token, app_secret)
    return hmac.compare_digest(expected_proof, app_secret_proof)

if __name__ == "__main__":
    app_secret = "your-facebook-app-secret"
    access_token = "EAA..."
    
    app_secret_proof = validate_access_token(access_token, app_secret)
    
    print("App Secret Proof:")
    print(app_secret_proof)
    print()
    
    is_valid = verify_app_secret_proof(access_token, app_secret, app_secret_proof)
    print(f"Verification result: {'VALID' if is_valid else 'INVALID'}")