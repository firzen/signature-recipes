import * as crypto from 'crypto';

function hashSha256(data: string): string {
    return crypto.createHash('sha256').update(data).digest('hex');
}

function hmacSha256(data: string, key: Buffer): Buffer {
    return crypto.createHmac('sha256', key).update(data).digest();
}

function buildCanonicalRequest(method: string, uri: string, query: string, headers: Record<string, string>, payload: string): string {
    const sortedKeys = Object.keys(headers).sort();
    
    let canonicalHeaders = '';
    let signedHeaders = '';
    
    for (const key of sortedKeys) {
        canonicalHeaders += `${key.toLowerCase()}:${headers[key].trim()}\n`;
        signedHeaders += `${key.toLowerCase()};`;
    }
    
    signedHeaders = signedHeaders.slice(0, -1);
    const payloadHash = hashSha256(payload);
    
    return `${method}\n${uri}\n${query}\n${canonicalHeaders}\n${signedHeaders}\n${payloadHash}`;
}

function buildStringToSign(algorithm: string, timestamp: string, credentialScope: string, canonicalRequest: string): string {
    const canonicalRequestHash = hashSha256(canonicalRequest);
    return `${algorithm}\n${timestamp}\n${credentialScope}\n${canonicalRequestHash}`;
}

function calculateSignature(stringToSign: string, secretKey: string, date: string, region: string, service: string): string {
    const kDate = hmacSha256(date, Buffer.from(`AWS4${secretKey}`));
    const kRegion = hmacSha256(region, kDate);
    const kService = hmacSha256(service, kRegion);
    const kSigning = hmacSha256('aws4_request', kService);
    return hmacSha256(stringToSign, kSigning).toString('hex');
}

function buildAuthorizationHeader(accessKey: string, secretKey: string, region: string, service: string,
                                  method: string, uri: string, query: string, headers: Record<string, string>,
                                  payload: string, timestamp: string): string {
    const algorithm = 'AWS4-HMAC-SHA256';
    const date = timestamp.slice(0, 8);
    const credentialScope = `${date}/${region}/${service}/aws4_request`;
    
    const canonicalRequest = buildCanonicalRequest(method, uri, query, headers, payload);
    const stringToSign = buildStringToSign(algorithm, timestamp, credentialScope, canonicalRequest);
    const signature = calculateSignature(stringToSign, secretKey, date, region, service);
    
    const sortedKeys = Object.keys(headers).sort();
    const signedHeaders = sortedKeys.map(k => k.toLowerCase()).join(';');
    
    return `${algorithm} Credential=${accessKey}/${credentialScope}, SignedHeaders=${signedHeaders}, Signature=${signature}`;
}

const accessKey = 'AKIAIOSFODNN7EXAMPLE';
const secretKey = 'wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY';
const region = 'us-east-1';
const service = 's3';
const method = 'GET';
const host = 'examplebucket.s3.amazonaws.com';
const uri = '/';
const timestamp = '20240101T000000Z';

const headers: Record<string, string> = {
    Host: host,
    'X-Amz-Date': timestamp
};

const authorization = buildAuthorizationHeader(accessKey, secretKey, region, service, method, uri, '', headers, '', timestamp);

console.log('Authorization Header:');
console.log(authorization);
console.log();
console.log(`X-Amz-Date: ${timestamp}`);