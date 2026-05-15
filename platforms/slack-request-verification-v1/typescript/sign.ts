import * as crypto from 'crypto';

function generateSignature(timestamp: string, body: string, signingSecret: string): string {
    const stringToSign = `v0:${timestamp}:${body}`;
    return `v0=${crypto.createHmac('sha256', signingSecret).update(stringToSign).digest('hex')}`;
}

function verifyRequest(timestamp: string, body: string, signatureHeader: string, signingSecret: string): boolean {
    const expectedSignature = generateSignature(timestamp, body, signingSecret);
    return crypto.timingSafeEqual(Buffer.from(expectedSignature), Buffer.from(signatureHeader));
}

const signingSecret = "your_slack_signing_secret";
const timestamp = Math.floor(Date.now() / 1000).toString();
const body = '{"token":"abc123","team_id":"T123"}';
const signature = generateSignature(timestamp, body, signingSecret);

console.log(`Timestamp: ${timestamp}`);
console.log(`Generated Signature: ${signature}`);
console.log();

const isValid = verifyRequest(timestamp, body, signature, signingSecret);
console.log(`Signature verification result: ${isValid ? 'VALID' : 'INVALID'}`);