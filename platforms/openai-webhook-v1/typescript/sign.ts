import * as crypto from 'crypto';

function generateSignature(timestamp: string, payload: string, webhookSecret: string): string {
    const stringToSign = `${timestamp}.${payload}`;
    const signature = crypto.createHmac('sha256', webhookSecret).update(stringToSign).digest('hex');
    return `t=${timestamp},v1=${signature}`;
}

function verifyWebhookSignature(payload: string, signatureHeader: string, webhookSecret: string): boolean {
    const parts = signatureHeader.split(',');
    const timestamp = parts[0].replace('t=', '');
    const actualSignature = parts[1].replace('v1=', '');
    
    const stringToSign = `${timestamp}.${payload}`;
    const expectedSignature = crypto.createHmac('sha256', webhookSecret).update(stringToSign).digest('hex');
    
    return crypto.timingSafeEqual(Buffer.from(expectedSignature), Buffer.from(actualSignature));
}

const webhookSecret = "your_openai_webhook_secret";
const payload = '{"event":"completion","data":{"id":"cmpl-xxx"}}';
const timestamp = Math.floor(Date.now() / 1000).toString();
const signatureHeader = generateSignature(timestamp, payload, webhookSecret);

console.log("Generated Signature Header:");
console.log(signatureHeader);
console.log();

const isValid = verifyWebhookSignature(payload, signatureHeader, webhookSecret);
console.log(`Signature verification result: ${isValid ? 'VALID' : 'INVALID'}`);