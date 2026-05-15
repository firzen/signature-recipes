import * as crypto from 'crypto';

function generateSignature(timestamp: string, secret: string): string {
    const stringToSign = `${timestamp}\n${secret}`;
    const hash = crypto.createHmac('sha256', secret).update(stringToSign).digest();
    return hash.toString('base64');
}

const secret = "SECyour_secret";
const timestamp = Date.now().toString();
const signature = generateSignature(timestamp, secret);

console.log(`Timestamp: ${timestamp}`);
console.log(`Signature: ${signature}`);
console.log();
console.log("Webhook URL with signature:");
console.log(`https://oapi.dingtalk.com/robot/send?access_token=xxx&timestamp=${timestamp}&sign=${encodeURIComponent(signature)}`);