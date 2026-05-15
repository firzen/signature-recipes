import * as crypto from 'crypto';

function validateAccessToken(accessToken: string, appSecret: string): string {
    return crypto.createHmac('sha256', appSecret).update(accessToken).digest('hex');
}

function verifyAppSecretProof(accessToken: string, appSecret: string, appSecretProof: string): boolean {
    const expectedProof = validateAccessToken(accessToken, appSecret);
    return crypto.timingSafeEqual(Buffer.from(expectedProof), Buffer.from(appSecretProof));
}

const appSecret = "your-facebook-app-secret";
const accessToken = "EAA...";

const appSecretProof = validateAccessToken(accessToken, appSecret);

console.log("App Secret Proof:");
console.log(appSecretProof);
console.log();

const isValid = verifyAppSecretProof(accessToken, appSecret, appSecretProof);
console.log(`Verification result: ${isValid ? 'VALID' : 'INVALID'}`);