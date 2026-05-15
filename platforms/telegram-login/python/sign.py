import hmac
import hashlib

def build_data_check_string(data: dict) -> str:
    sorted_keys = sorted(k for k in data.keys() if k != 'hash')
    
    check_string = ''
    for i, k in enumerate(sorted_keys):
        if i > 0:
            check_string += '\n'
        check_string += f"{k}={data[k]}"
    
    return check_string

def verify_login(data: dict, bot_token: str) -> bool:
    secret_key = hashlib.sha256(bot_token.encode('utf-8')).digest()
    data_check_string = build_data_check_string(data)
    expected_hash = hmac.new(secret_key, data_check_string.encode('utf-8'), hashlib.sha256).hexdigest()
    
    return hmac.compare_digest(expected_hash, data['hash'])

if __name__ == "__main__":
    bot_token = "your_bot_token_without_bot_prefix"
    
    data = {
        'auth_date': '1710000000',
        'first_name': 'John',
        'id': '123456789',
        'last_name': 'Doe',
        'username': 'johndoe'
    }
    
    secret_key = hashlib.sha256(bot_token.encode('utf-8')).digest()
    data_check_string = build_data_check_string(data)
    hash_value = hmac.new(secret_key, data_check_string.encode('utf-8'), hashlib.sha256).hexdigest()
    
    data['hash'] = hash_value
    
    print("Data Check String:")
    print(data_check_string)
    print()
    
    print("Generated Hash:")
    print(hash_value)
    print()
    
    is_valid = verify_login(data, bot_token)
    print(f"Verification result: {'VALID' if is_valid else 'INVALID'}")