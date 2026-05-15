function createAuthorizationHeader(apiKey) {
    return `Bearer ${apiKey}`;
}

function buildRequest(apiKey, model, prompt, maxTokens = 100) {
    const headers = {
        "Authorization": createAuthorizationHeader(apiKey),
        "Content-Type": "application/json"
    };
    
    const body = {
        model: model,
        prompt: prompt,
        max_tokens: maxTokens
    };
    
    return { headers, body };
}

const apiKey = "sk-your-api-key";
const request = buildRequest(apiKey, "gpt-3.5-turbo", "Hello, world!", 100);

console.log("Headers:");
for (const [key, value] of Object.entries(request.headers)) {
    console.log(`  ${key}: ${value}`);
}

console.log("\nBody:");
for (const [key, value] of Object.entries(request.body)) {
    console.log(`  ${key}: ${value}`);
}