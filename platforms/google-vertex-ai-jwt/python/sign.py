import base64
import hashlib
import json
import time
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.asymmetric import padding
from cryptography.hazmat.primitives.serialization import load_pem_private_key
from cryptography.hazmat.backends import default_backend

def base64url_encode(data: bytes) -> str:
    return base64.urlsafe_b64encode(data).decode('utf-8').rstrip('=')

def generate_jwt(private_key_pem: bytes, iss: str, aud: str, expires_in: int = 3600) -> str:
    header = json.dumps({'alg': 'RS256', 'typ': 'JWT'}, separators=(',', ':'))
    now = int(time.time())
    payload = json.dumps({
        'iss': iss,
        'sub': iss,
        'aud': aud,
        'exp': now + expires_in,
        'iat': now
    }, separators=(',', ':'))
    
    encoded_header = base64url_encode(header.encode('utf-8'))
    encoded_payload = base64url_encode(payload.encode('utf-8'))
    
    data_to_sign = f"{encoded_header}.{encoded_payload}"
    
    private_key = load_pem_private_key(private_key_pem, password=None, backend=default_backend())
    signature = private_key.sign(
        data_to_sign.encode('utf-8'),
        padding.PKCS1v15(),
        hashes.SHA256()
    )
    
    encoded_signature = base64url_encode(signature)
    
    return f"{encoded_header}.{encoded_payload}.{encoded_signature}"

if __name__ == "__main__":
    private_key = b"""-----BEGIN PRIVATE KEY-----
your_private_key_here
-----END PRIVATE KEY-----"""
    issuer = "your-service-account@your-project.iam.gserviceaccount.com"
    audience = "https://aiplatform.googleapis.com/"
    
    jwt = generate_jwt(private_key, issuer, audience)
    
    print("Generated JWT:")
    print(jwt)
    print()
    print("Use this JWT in Authorization header:")
    print(f"Authorization: Bearer {jwt}")