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

const webhookSecret = "your_elevenlabs_webhook_secret";
const payload = '{"event":"audio.generation.completed","data":{"id":"gen-xxx"}}';
const timestamp = Math.floor(Date.now() / 1000).toString();
const signatureHeader = generateSignature(timestamp, payload, webhookSecret);

console.log("Generated Signature Header (X-ElevenLabs-Signature):");
console.log(signatureHeader);
console.log();

const isValid = verifyWebhookSignature(payload, signatureHeader, webhookSecret);
console.log(`Signature verification result: ${isValid ? 'VALID' : 'INVALID'}`);