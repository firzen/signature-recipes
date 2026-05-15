import * as crypto from 'crypto';

function generateSignature(params: Record<string, string>, privateKeyPem: string): string {
    const sortedKeys = Object.keys(params).filter(k => k !== 'sign').sort();
    
    let signString = '';
    for (let i = 0; i < sortedKeys.length; i++) {
        const k = sortedKeys[i];
        const v = params[k];
        if (v) {
            if (i > 0) {
                signString += '&';
            }
            signString += `${k}=${v}`;
        }
    }
    
    const sign = crypto.createSign('RSA-SHA256');
    sign.update(signString);
    const signature = sign.sign(privateKeyPem);
    
    return signature.toString('base64');
}

const privateKey = `-----BEGIN RSA PRIVATE KEY-----
your_private_key_here
-----END RSA PRIVATE KEY-----`;

const params: Record<string, string> = {
    app_id: 'your_app_id',
    method: 'alipay.trade.app.pay',
    charset: 'UTF-8',
    sign_type: 'RSA2',
    timestamp: new Date().toLocaleString('zh-CN', { 
        year: 'numeric', month: '2-digit', day: '2-digit',
        hour: '2-digit', minute: '2-digit', second: '2-digit',
        hour12: false 
    }).replace(/\//g, '-'),
    version: '1.0',
    biz_content: '{"out_trade_no":"202401010001","total_amount":"0.01","subject":"Test"}'
};

params.sign = generateSignature(params, privateKey);

console.log("Request Parameters:");
for (const k of Object.keys(params).sort()) {
    console.log(`  ${k}: ${params[k]}`);
}