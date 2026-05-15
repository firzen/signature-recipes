import base64
import json
import time

def base64url_decode(data: str) -> bytes:
    padding = 4 - (len(data) % 4)
    if padding != 4:
        data += '=' * padding
    return base64.urlsafe_b64decode(data)

def verify_google_id_token(id_token: str, client_id: str) -> dict:
    parts = id_token.split('.')
    if len(parts) != 3:
        return None
    
    payload_bytes = base64url_decode(parts[1])
    payload = json.loads(payload_bytes.decode('utf-8'))
    
    if payload.get('aud') != client_id:
        return None
    
    if payload.get('exp', 0) < time.time():
        return None
    
    return payload

if __name__ == "__main__":
    client_id = "your-google-client-id.apps.googleusercontent.com"
    id_token = "eyJhbGciOiJSUzI1NiIsImtpZCI6..."
    
    payload = verify_google_id_token(id_token, client_id)
    
    if payload:
        print("Token verified successfully!")
        print(f"User ID: {payload['sub']}")
        print(f"Email: {payload['email']}")
    else:
        print("Token verification failed!")