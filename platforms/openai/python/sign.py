def create_authorization_header(api_key):
    return f"Bearer {api_key}"

def build_request(api_key, model, prompt, max_tokens=100):
    headers = {
        "Authorization": create_authorization_header(api_key),
        "Content-Type": "application/json"
    }
    
    body = {
        "model": model,
        "prompt": prompt,
        "max_tokens": max_tokens
    }
    
    return {"headers": headers, "body": body}

if __name__ == "__main__":
    api_key = "sk-your-api-key"
    request = build_request(api_key, "gpt-3.5-turbo", "Hello, world!", 100)
    
    print("Headers:")
    for key, value in request["headers"].items():
        print(f"  {key}: {value}")
    
    print("\nBody:")
    for key, value in request["body"].items():
        print(f"  {key}: {value}")