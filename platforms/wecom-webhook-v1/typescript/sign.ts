import * as crypto from 'crypto';

function generateSignature(timestamp: number, secret: string): string {
    const stringToSign = `${timestamp}\n${secret}`;
    return crypto.createHash('sha256').update(stringToSign).digest('hex');
}

function buildWebhookUrl(webhookUrl: string, secret: string): string {
    const timestamp = Math.floor(Date.now() / 1000);
    const signature = generateSignature(timestamp, secret);
    return `${webhookUrl}&timestamp=${timestamp}&sign=${signature}`;
}

const webhookUrl = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=your-webhook-key";
const secret = "your-wecom-bot-secret";

const signedUrl = buildWebhookUrl(webhookUrl, secret);

console.log("Signed Webhook URL:");
console.log(signedUrl);
console.log();
console.log(`Timestamp: ${Math.floor(Date.now() / 1000)}`);
console.log(`Signature: ${generateSignature(Math.floor(Date.now() / 1000), secret)}`);