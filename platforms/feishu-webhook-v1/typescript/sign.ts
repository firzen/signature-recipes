import * as crypto from 'crypto';

function generateSignature(timestamp: number, secret: string): string {
    const stringToSign = `${timestamp}\n${secret}`;
    return crypto.createHmac('sha256', secret).update(stringToSign).digest('base64');
}

function buildWebhookUrl(webhookUrl: string, secret: string): string {
    const timestamp = Math.floor(Date.now() / 1000);
    const signature = generateSignature(timestamp, secret);
    return `${webhookUrl}&timestamp=${timestamp}&sign=${encodeURIComponent(signature)}`;
}

const webhookUrl = "https://open.feishu.cn/open-apis/bot/v2/hook/your-webhook-id";
const secret = "your-feishu-bot-secret";

const signedUrl = buildWebhookUrl(webhookUrl, secret);

console.log("Signed Webhook URL:");
console.log(signedUrl);
console.log();
console.log(`Timestamp: ${Math.floor(Date.now() / 1000)}`);
console.log(`Signature: ${generateSignature(Math.floor(Date.now() / 1000), secret)}`);