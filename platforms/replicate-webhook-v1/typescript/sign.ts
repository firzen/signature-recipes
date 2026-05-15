import * as crypto from 'crypto';

function generateSignature(payload: string, webhookSecret: string): string {
    return crypto.createHmac('sha256', webhookSecret).update(payload).digest('hex');
}

function verifyWebhookSignature(payload: string, signatureHeader: string, webhookSecret: string): boolean {
    const expectedSignature = generateSignature(payload, webhookSecret);
    return crypto.timingSafeEqual(Buffer.from(expectedSignature), Buffer.from(signatureHeader));
}

const webhookSecret = "your_replicate_webhook_secret";
const payload = '{"id":"xxx","version":"xxx","status":"succeeded"}';
const signature = generateSignature(payload, webhookSecret);

console.log("Generated Signature (X-Replicate-Signature):");
console.log(signature);
console.log();

const isValid = verifyWebhookSignature(payload, signature, webhookSecret);
console.log(`Signature verification result: ${isValid ? 'VALID' : 'INVALID'}`);