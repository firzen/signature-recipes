import hmac
import hashlib
import time

def hmac_sha256(data: bytes, key: bytes) -> bytes:
    return hmac.new(key, data, hashlib.sha256).digest()

def hash_sha256(data: str) -> bytes:
    return hashlib.sha256(data.encode('utf-8')).digest()

def build_canonical_request(method: str, uri: str, query_string: str, 
                           headers: dict, payload: str) -> str:
    sorted_keys = sorted(headers.keys())
    canonical_headers = ''
    signed_headers = ''
    for i, k in enumerate(sorted_keys):
        canonical_headers += f"{k}:{headers[k]}\n"
        if i > 0:
            signed_headers += ';'
        signed_headers += k
    
    payload_hash = hash_sha256(payload).hex()
    
    return f"{method}\n{uri}\n{query_string}\n{canonical_headers}{signed_headers}\n{payload_hash}"

def build_string_to_sign(timestamp: str, date: str, region: str, 
                        service: str, canonical_request: str) -> str:
    canonical_request_hash = hash_sha256(canonical_request).hex()
    return f"AWS4-HMAC-SHA256\n{timestamp}\n{date}/{region}/{service}/aws4_request\n{canonical_request_hash}"

def generate_signature(secret_key: str, date: str, region: str, 
                      service: str, string_to_sign: str) -> str:
    k_date = hmac_sha256(date.encode('utf-8'), f"AWS4{secret_key}".encode('utf-8'))
    k_region = hmac_sha256(region.encode('utf-8'), k_date)
    k_service = hmac_sha256(service.encode('utf-8'), k_region)
    k_signing = hmac_sha256(b"aws4_request", k_service)
    return hmac_sha256(string_to_sign.encode('utf-8'), k_signing).hex()

if __name__ == "__main__":
    access_key = "your_aws_access_key"
    secret_key = "your_aws_secret_key"
    region = "us-east-1"
    service = "bedrock"
    method = "POST"
    uri = "/model/anthropic.claude-3-sonnet-20240229/v1/complete"
    payload = '{"prompt":"Hello","max_tokens_to_sample":100}'

    timestamp = time.strftime("%Y%m%dT%H%M%SZ", time.gmtime())
    date = timestamp[:8]

    headers = {
        "host": f"bedrock.{region}.amazonaws.com",
        "x-amz-date": timestamp,
        "content-type": "application/json"
    }

    canonical_request = build_canonical_request(method, uri, "", headers, payload)
    string_to_sign = build_string_to_sign(timestamp, date, region, service, canonical_request)
    signature = generate_signature(secret_key, date, region, service, string_to_sign)

    print("Canonical Request:")
    print(canonical_request)
    print("\nString to Sign:")
    print(string_to_sign)
    print("\nSignature:")
    print(signature)