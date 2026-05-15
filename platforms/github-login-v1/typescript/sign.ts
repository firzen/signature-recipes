import * as crypto from 'crypto';

function validateAccessToken(accessToken: string, clientSecret: string): string {
    return crypto.createHmac('sha256', clientSecret).update(accessToken).digest('hex');
}

const clientSecret = "your-github-client-secret";
const accessToken = "gho_...";

const signature = validateAccessToken(accessToken, clientSecret);

console.log("HMAC Signature:");
console.log(signature);