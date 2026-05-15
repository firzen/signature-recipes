import * as crypto from 'crypto';

function generateNonceStr(length: number = 32): string {
    const chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    let result = '';
    for (let i = 0; i < length; i++) {
        result += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return result;
}

function signParams(params: Record<string, string>, key: string): string {
    const sortedKeys = Object.keys(params).sort();
    
    let signString = '';
    for (const k of sortedKeys) {
        const v = params[k];
        if (v && k !== 'sign') {
            if (signString) {
                signString += '&';
            }
            signString += `${k}=${v}`;
        }
    }
    
    signString += `&key=${key}`;
    
    return crypto.createHash('md5').update(signString).digest('hex').toUpperCase();
}

function buildPaymentRequest(appid: string, mchId: string, key: string, body: string, 
                            outTradeNo: string, totalFee: number, spbillCreateIp: string, 
                            notifyUrl: string, tradeType: string): Record<string, string> {
    const params: Record<string, string> = {
        appid: appid,
        mch_id: mchId,
        nonce_str: generateNonceStr(),
        body: body,
        out_trade_no: outTradeNo,
        total_fee: String(totalFee),
        spbill_create_ip: spbillCreateIp,
        notify_url: notifyUrl,
        trade_type: tradeType
    };
    
    params.sign = signParams(params, key);
    
    return params;
}

const appid = "wx1234567890abcdef";
const mchId = "1234567890";
const key = "your_secret_key";

const params = buildPaymentRequest(
    appid, mchId, key,
    "Test Payment",
    "202401010001",
    100,
    "192.168.1.1",
    "https://example.com/notify",
    "APP"
);

console.log("Request Parameters:");
for (const [k, v] of Object.entries(params).sort()) {
    console.log(`  ${k}: ${v}`);
}