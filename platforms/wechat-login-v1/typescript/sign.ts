import * as crypto from 'crypto';

function buildQueryString(params: Record<string, string>): string {
    const sortedKeys = Object.keys(params).sort();
    return sortedKeys.map(k => `${k}=${encodeURIComponent(params[k])}`).join('&');
}

function signQueryString(params: Record<string, string>, appSecret: string): string {
    const queryString = buildQueryString(params);
    return crypto.createHmac('sha256', appSecret).update(queryString).digest('hex');
}

const appId = "your-wechat-app-id";
const appSecret = "your-wechat-app-secret";
const code = "001xxx";

const params: Record<string, string> = {
    'appid': appId,
    'secret': appSecret,
    'js_code': code,
    'grant_type': 'authorization_code'
};

const signature = signQueryString(params, appSecret);

console.log("Query String:");
console.log(buildQueryString(params));
console.log();
console.log("Signature:");
console.log(signature);