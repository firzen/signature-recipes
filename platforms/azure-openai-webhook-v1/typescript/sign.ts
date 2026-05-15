import * as crypto from 'crypto';

function generateSignature(timestamp: string, payload: string, webhookSecret: string): string {
    const stringToSign = `${timestamp}.${payload}`;
    const signature = crypto.createHmac('sha256', webhookSecret).update(stringToSign).digest('hex');
    return `${timestamp}.${signature}`;
}

function verifyWebhookSignature(payload: string, signatureHeader: string, webhookSecret: string): boolean {
    const parts = signatureHeader.split('.', 2);
    const timestamp = parts[0];
    const actualSignature = parts[1];
    
    const stringToSign = `${timestamp}.${payload}`;
    const expectedSignature = crypto.createHmac('sha256', webhookSecret).update(stringToSign).digest('hex');
    
    return crypto.timingSafeEqual(Buffer.from(expectedSignature), Buffer.from(actualSignature));
}

const webhookSecret = "your_azure_openai_webhook_secret";
const payload = '{"eventType":"completion","data":{"id":"cmpl-xxx"}}';
const timestamp = Math.floor(Date.now() / 1000).toString();
const signatureHeader = generateSignature(timestamp, payload, webhookSecret);

console.log("Generated Signature Header (Azure-Signature):");
console.log(signatureHeader);
console.log();

const isValid = verifyWebhookSignature(payload, signatureHeader, webhookSecret);
console.log(`Signature verification result: ${isValid ? 'VALID' : 'INVALID'}`);