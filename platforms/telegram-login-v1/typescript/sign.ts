import * as crypto from 'crypto';

function buildDataCheckString(data: Record<string, string>): string {
    const sortedKeys = Object.keys(data).filter(k => k !== 'hash').sort();
    
    let checkString = '';
    for (let i = 0; i < sortedKeys.length; i++) {
        if (i > 0) {
            checkString += '\n';
        }
        checkString += `${sortedKeys[i]}=${data[sortedKeys[i]]}`;
    }
    return checkString;
}

function verifyLogin(data: Record<string, string>, botToken: string): boolean {
    const secretKey = crypto.createHash('sha256').update(botToken).digest();
    const dataCheckString = buildDataCheckString(data);
    const expectedHash = crypto.createHmac('sha256', secretKey).update(dataCheckString).digest('hex');
    
    return crypto.timingSafeEqual(Buffer.from(expectedHash), Buffer.from(data['hash']));
}

const botToken = "your_bot_token_without_bot_prefix";

const data: Record<string, string> = {
    auth_date: '1710000000',
    first_name: 'John',
    id: '123456789',
    last_name: 'Doe',
    username: 'johndoe'
};

const secretKey = crypto.createHash('sha256').update(botToken).digest();
const dataCheckString = buildDataCheckString(data);
const hashValue = crypto.createHmac('sha256', secretKey).update(dataCheckString).digest('hex');

data['hash'] = hashValue;

console.log("Data Check String:");
console.log(dataCheckString);
console.log();

console.log("Generated Hash:");
console.log(hashValue);
console.log();

const isValid = verifyLogin(data, botToken);
console.log(`Verification result: ${isValid ? 'VALID' : 'INVALID'}`);