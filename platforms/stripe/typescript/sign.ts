import * as crypto from 'crypto';

function createAuthorizationHeader(secretKey: string): string {
    return `Bearer ${secretKey}`;
}

function hmacSha256(data: string, secret: string): string {
    return crypto.createHmac('sha256', secret).update(data).digest('hex');
}

function verifyWebhookSignature(payload: string, signatureHeader: string, webhookSecret: string): boolean {
    const parts = signatureHeader.split(',');
    let timestamp: string | null = null;
    let signature: string | null = null;
    
    for (const part of parts) {
        const [key, value] = part.split('=', 2);
        if (key === 't') {
            timestamp = value;
        } else if (key === 'v1') {
            signature = value;
        }
    }
    
    if (!timestamp || !signature) {
        return false;
    }
    
    const signedPayload = `${timestamp}.${payload}`;
    const expectedSignature = hmacSha256(signedPayload, webhookSecret);
    
    return crypto.timingSafeEqual(Buffer.from(expectedSignature), Buffer.from(signature));
}

const secretKey = "sk_test_your_secret_key";
const webhookSecret = "whsec_your_webhook_secret";

console.log(`Authorization Header: ${createAuthorizationHeader(secretKey)}`);
console.log();

const payload = '{"id":"evt_123","object":"event"}';
const timestamp = "1710000000";
const signedPayload = `${timestamp}.${payload}`;
const calculatedSignature = hmacSha256(signedPayload, webhookSecret);
const signatureHeader = `t=${timestamp},v1=${calculatedSignature}`;

console.log("Generated Signature Header:");
console.log(signatureHeader);
console.log();

const isValid = verifyWebhookSignature(payload, signatureHeader, webhookSecret);
console.log(`Signature verification result: ${isValid ? 'VALID' : 'INVALID'}`);