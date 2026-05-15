function base64urlDecode(data: string): string {
    const padding = '='.repeat((4 - (data.length % 4)) % 4);
    return Buffer.from(data + padding, 'base64url').toString('utf-8');
}

interface Payload {
    sub: string;
    aud: string;
    exp: number;
    email?: string;
}

function verifyGoogleIdToken(idToken: string, clientId: string): Payload | null {
    const parts = idToken.split('.');
    if (parts.length !== 3) {
        return null;
    }

    const payloadJson = base64urlDecode(parts[1]);
    const payload = JSON.parse(payloadJson) as Payload;

    if (payload.aud !== clientId) {
        return null;
    }

    if (payload.exp < Date.now() / 1000) {
        return null;
    }

    return payload;
}

const clientId = "your-google-client-id.apps.googleusercontent.com";
const idToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6...";

const payload = verifyGoogleIdToken(idToken, clientId);

if (payload) {
    console.log("Token verified successfully!");
    console.log(`User ID: ${payload.sub}`);
    console.log(`Email: ${payload.email}`);
} else {
    console.log("Token verification failed!");
}