import hmac
import hashlib

def hmac_sha256(data: bytes, key: bytes) -> bytes:
    return hmac.new(key, data, hashlib.sha256).digest()

def hash_sha256(data: str) -> str:
    return hashlib.sha256(data.encode('utf-8')).hexdigest()

def build_canonical_request(method: str, uri: str, query: str, headers: dict, payload: str) -> str:
    sorted_headers = sorted(headers.keys())
    
    canonical_headers = ''
    signed_headers = ''
    
    for key in sorted_headers:
        canonical_headers += f"{key.lower()}:{headers[key].strip()}\n"
        signed_headers += f"{key.lower()};"
    
    signed_headers = signed_headers.rstrip(';')
    payload_hash = hash_sha256(payload)
    
    return f"{method}\n{uri}\n{query}\n{canonical_headers}\n{signed_headers}\n{payload_hash}"

def build_string_to_sign(algorithm: str, timestamp: str, credential_scope: str, canonical_request: str) -> str:
    canonical_request_hash = hash_sha256(canonical_request)
    return f"{algorithm}\n{timestamp}\n{credential_scope}\n{canonical_request_hash}"

def calculate_signature(string_to_sign: str, secret_key: str, date: str, region: str, service: str) -> str:
    k_date = hmac_sha256(date.encode('utf-8'), f"AWS4{secret_key}".encode('utf-8'))
    k_region = hmac_sha256(region.encode('utf-8'), k_date)
    k_service = hmac_sha256(service.encode('utf-8'), k_region)
    k_signing = hmac_sha256(b"aws4_request", k_service)
    return hmac_sha256(string_to_sign.encode('utf-8'), k_signing).hex()

def build_authorization_header(access_key: str, secret_key: str, region: str, service: str, 
                              method: str, uri: str, query: str, headers: dict, payload: str, timestamp: str) -> str:
    algorithm = "AWS4-HMAC-SHA256"
    date = timestamp[:8]
    credential_scope = f"{date}/{region}/{service}/aws4_request"
    
    canonical_request = build_canonical_request(method, uri, query, headers, payload)
    string_to_sign = build_string_to_sign(algorithm, timestamp, credential_scope, canonical_request)
    signature = calculate_signature(string_to_sign, secret_key, date, region, service)
    
    sorted_headers = sorted(headers.keys())
    signed_headers = ';'.join(key.lower() for key in sorted_headers)
    
    return f"{algorithm} Credential={access_key}/{credential_scope}, SignedHeaders={signed_headers}, Signature={signature}"

if __name__ == "__main__":
    access_key = "AKIAIOSFODNN7EXAMPLE"
    secret_key = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY"
    region = "us-east-1"
    service = "s3"
    method = "GET"
    host = "examplebucket.s3.amazonaws.com"
    uri = "/"
    timestamp = "20240101T000000Z"
    
    headers = {
        "Host": host,
        "X-Amz-Date": timestamp
    }
    
    authorization = build_authorization_header(access_key, secret_key, region, service, method, uri, "", headers, "", timestamp)
    
    print("Authorization Header:")
    print(authorization)
    print()
    print(f"X-Amz-Date: {timestamp}")