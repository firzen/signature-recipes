import * as crypto from 'crypto';

function generateSignature(payload: string, apiSecret: string): string {
    const hash = crypto.createHmac('sha256', apiSecret).update(payload).digest();
    return hash.toString('base64');
}

function verifyWebhookSignature(payload: string, signatureHeader: string, apiSecret: string): boolean {
    const expectedSignature = generateSignature(payload, apiSecret);
    return crypto.timingSafeEqual(Buffer.from(expectedSignature), Buffer.from(signatureHeader));
}

const apiSecret = "your_shopify_api_secret";
const payload = '{"id":123,"email":"test@example.com"}';
const signature = generateSignature(payload, apiSecret);

console.log("Generated Signature (X-Shopify-Hmac-Sha256):");
console.log(signature);
console.log();

const isValid = verifyWebhookSignature(payload, signature, apiSecret);
console.log(`Signature verification result: ${isValid ? 'VALID' : 'INVALID'}`);