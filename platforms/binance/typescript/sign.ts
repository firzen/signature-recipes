import * as crypto from 'crypto';

function signRequest(params: Record<string, string | number>, secretKey: string): string {
    const sortedKeys = Object.keys(params).sort();
    
    let queryString = '';
    for (const key of sortedKeys) {
        if (queryString) {
            queryString += '&';
        }
        queryString += `${encodeURIComponent(key)}=${encodeURIComponent(String(params[key]))}`;
    }
    
    const signature = crypto.createHmac('sha256', secretKey).update(queryString).digest('hex');
    
    return `${queryString}&signature=${signature}`;
}

function buildSignedRequest(apiKey: string, secretKey: string, params: Record<string, string | number>): { headers: Record<string, string>; query: string } {
    if (!params['timestamp']) {
        params['timestamp'] = Date.now();
    }
    
    const signedQuery = signRequest(params, secretKey);
    
    return {
        headers: { 'X-MBX-APIKEY': apiKey },
        query: signedQuery
    };
}

const apiKey = "your_api_key";
const secretKey = "your_secret_key";

const params: Record<string, string | number> = {
    symbol: 'BTCUSDT',
    quantity: '0.001',
    price: '40000.00'
};

const request = buildSignedRequest(apiKey, secretKey, params);

console.log("Headers:");
for (const [key, value] of Object.entries(request.headers)) {
    console.log(`  ${key}: ${value}`);
}

console.log("\nQuery String:");
console.log(request.query);