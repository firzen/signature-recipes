import * as crypto from 'crypto';

function generateNonceStr(length: number = 32): string {
    const chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    let result = '';
    for (let i = 0; i < length; i++) {
        result += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return result;
}

function buildStringToSign(method: string, uri: string, timestamp: string, nonce: string, body: string): string {
    return `${method}\n${uri}\n${timestamp}\n${nonce}\n${body}\n`;
}

function generateSignature(method: string, uri: string, timestamp: string, nonce: string, body: string, apiV3Key: string): string {
    const stringToSign = buildStringToSign(method, uri, timestamp, nonce, body);
    return crypto.createHmac('sha256', apiV3Key).update(stringToSign).digest('hex');
}

function buildAuthorizationHeader(mchid: string, apiV3Key: string, serialNo: string, method: string, uri: string, body: string): string {
    const timestamp = Math.floor(Date.now() / 1000).toString();
    const nonce = generateNonceStr();
    const signature = generateSignature(method, uri, timestamp, nonce, body, apiV3Key);
    
    return `WECHATPAY2-SHA256-RSA2048 mchid="${mchid}",nonce_str="${nonce}",signature="${signature}",timestamp="${timestamp}",serial_no="${serialNo}"`;
}

const mchid = "1234567890";
const apiV3Key = "your_api_v3_key";
const serialNo = "your_certificate_serial_number";
const method = "POST";
const uri = "/v3/pay/transactions/app";
const body = '{"mchid":"1234567890","out_trade_no":"202401010001","amount":{"total":100},"description":"Test"}';

const authorization = buildAuthorizationHeader(mchid, apiV3Key, serialNo, method, uri, body);

console.log("Authorization Header:");
console.log(authorization);