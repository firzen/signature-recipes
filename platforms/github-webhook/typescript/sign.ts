import * as crypto from 'crypto';

function hmacSha256(data: string, secret: string): string {
    return `sha256=${crypto.createHmac('sha256', secret).update(data).digest('hex')}`;
}

function verifyWebhookSignature(payload: string, signatureHeader: string, webhookSecret: string): boolean {
    const expectedSignature = hmacSha256(payload, webhookSecret);
    return crypto.timingSafeEqual(Buffer.from(expectedSignature), Buffer.from(signatureHeader));
}

const webhookSecret = "your_webhook_secret";
const payload = '{"action":"created","ref":"refs/heads/main"}';

const signatureHeader = hmacSha256(payload, webhookSecret);
console.log("Generated Signature Header:");
console.log(signatureHeader);
console.log();

const isValid = verifyWebhookSignature(payload, signatureHeader, webhookSecret);
console.log(`Signature verification result: ${isValid ? 'VALID' : 'INVALID'}`);