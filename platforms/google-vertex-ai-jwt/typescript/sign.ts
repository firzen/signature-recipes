import * as crypto from 'crypto';

function base64urlEncode(data: Buffer): string {
    return data.toString('base64')
        .replace(/\+/g, '-')
        .replace(/\//g, '_')
        .replace(/=/g, '');
}

function generateJWT(privateKeyPem: string, iss: string, aud: string, expiresIn: number = 3600): string {
    const header = JSON.stringify({ alg: 'RS256', typ: 'JWT' });
    const now = Math.floor(Date.now() / 1000);
    const payload = JSON.stringify({
        iss: iss,
        sub: iss,
        aud: aud,
        exp: now + expiresIn,
        iat: now
    });

    const encodedHeader = base64urlEncode(Buffer.from(header, 'utf-8'));
    const encodedPayload = base64urlEncode(Buffer.from(payload, 'utf-8'));

    const dataToSign = `${encodedHeader}.${encodedPayload}`;

    const privateKey = crypto.createPrivateKey(privateKeyPem);
    const signature = crypto.sign('sha256', Buffer.from(dataToSign, 'utf-8'), privateKey);
    const encodedSignature = base64urlEncode(signature);

    return `${encodedHeader}.${encodedPayload}.${encodedSignature}`;
}

const privateKey = `-----BEGIN PRIVATE KEY-----
your_private_key_here
-----END PRIVATE KEY-----`;
const issuer = "your-service-account@your-project.iam.gserviceaccount.com";
const audience = "https://aiplatform.googleapis.com/";

const jwt = generateJWT(privateKey, issuer, audience);

console.log("Generated JWT:");
console.log(jwt);
console.log();
console.log("Use this JWT in Authorization header:");
console.log(`Authorization: Bearer ${jwt}`);