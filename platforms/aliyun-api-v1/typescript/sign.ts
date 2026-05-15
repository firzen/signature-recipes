import * as crypto from 'crypto';

function percentEncode(s: string): string {
    return encodeURIComponent(s)
        .replace(/\+/g, '%20')
        .replace(/\*/g, '%2A')
        .replace(/%7E/g, '~');
}

function buildCanonicalizedQueryString(params: Record<string, string>): string {
    const sortedKeys = Object.keys(params)
        .filter(k => k !== 'Signature')
        .sort();
    
    return sortedKeys
        .map(k => `${percentEncode(k)}=${percentEncode(params[k])}`)
        .join('&');
}

function sign(accessKeySecret: string, method: string, host: string, path: string, params: Record<string, string>): string {
    const canonicalizedQueryString = buildCanonicalizedQueryString(params);
    const stringToSign = `${method}\n${host}\n${path}\n${canonicalizedQueryString}`;
    
    const signature = crypto.createHmac('sha1', accessKeySecret).update(stringToSign).digest('base64');
    return signature;
}

const accessKeyId = "your_access_key_id";
const accessKeySecret = "your_access_key_secret";
const method = "GET";
const host = "ecs.aliyuncs.com";
const path = "/";

const params: Record<string, string> = {
    "Format": "JSON",
    "Version": "2014-05-26",
    "AccessKeyId": accessKeyId,
    "SignatureMethod": "HMAC-SHA1",
    "Timestamp": new Date().toISOString().replace(/\.\d{3}Z$/, 'Z'),
    "SignatureVersion": "1.0",
    "SignatureNonce": Date.now().toString() + Math.random().toString().slice(2),
    "Action": "DescribeRegions"
};

const signature = sign(accessKeySecret, method, host, path, params);
params["Signature"] = signature;

console.log("Signature:");
console.log(signature);
console.log();
console.log("Full URL:");
console.log(`https://${host}${path}?${buildCanonicalizedQueryString(params)}`);