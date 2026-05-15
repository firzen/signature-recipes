import * as crypto from 'crypto';

function hmacSha256(data: string, key: string): Buffer {
    return crypto.createHmac('sha256', key).update(data).digest();
}

function hashSha256(data: string): Buffer {
    return crypto.createHash('sha256').update(data).digest();
}

function buildCanonicalRequest(method: string, uri: string, queryString: string, 
                              headers: Record<string, string>, payload: string): string {
    const sortedKeys = Object.keys(headers).sort();
    let canonicalHeaders = '';
    let signedHeaders = '';
    
    for (let i = 0; i < sortedKeys.length; i++) {
        const k = sortedKeys[i];
        canonicalHeaders += `${k}:${headers[k]}\n`;
        if (i > 0) signedHeaders += ';';
        signedHeaders += k;
    }
    
    const payloadHash = hashSha256(payload).toString('hex');
    
    return `${method}\n${uri}\n${queryString}\n${canonicalHeaders}${signedHeaders}\n${payloadHash}`;
}

function buildStringToSign(timestamp: string, date: string, region: string, 
                          service: string, canonicalRequest: string): string {
    const canonicalRequestHash = hashSha256(canonicalRequest).toString('hex');
    return `AWS4-HMAC-SHA256\n${timestamp}\n${date}/${region}/${service}/aws4_request\n${canonicalRequestHash}`;
}

function generateSignature(secretKey: string, date: string, region: string, 
                          service: string, stringToSign: string): string {
    const kDate = hmacSha256(date, `AWS4${secretKey}`);
    const kRegion = hmacSha256(region, kDate.toString('binary'));
    const kService = hmacSha256(service, kRegion.toString('binary'));
    const kSigning = hmacSha256('aws4_request', kService.toString('binary'));
    return hmacSha256(stringToSign, kSigning.toString('binary')).toString('hex');
}

const accessKey = "your_aws_access_key";
const secretKey = "your_aws_secret_key";
const region = "us-east-1";
const service = "bedrock";
const method = "POST";
const uri = "/model/anthropic.claude-3-sonnet-20240229/v1/complete";
const payload = '{"prompt":"Hello","max_tokens_to_sample":100}';

const timestamp = new Date().toISOString().replace(/[:-]/g, '').slice(0, 15) + 'Z';
const date = timestamp.slice(0, 8);

const headers: Record<string, string> = {
    "host": `bedrock.${region}.amazonaws.com`,
    "x-amz-date": timestamp,
    "content-type": "application/json"
};

const canonicalRequest = buildCanonicalRequest(method, uri, "", headers, payload);
const stringToSign = buildStringToSign(timestamp, date, region, service, canonicalRequest);
const signature = generateSignature(secretKey, date, region, service, stringToSign);

console.log("Canonical Request:");
console.log(canonicalRequest);
console.log("\nString to Sign:");
console.log(stringToSign);
console.log("\nSignature:");
console.log(signature);