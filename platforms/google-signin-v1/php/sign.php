<?php
// PHP Version: 5.2+ (requires json extension)
// Tested: PHP 5.2, PHP 5.6, PHP 7.x, PHP 8.x
function base64url_decode($data) {
    $padding = strlen($data) % 4;
    if ($padding) {
        $data .= str_repeat('=', 4 - $padding);
    }
    return base64_decode(strtr($data, '-_', '+/'));
}

function verifyGoogleIdToken($idToken, $clientId) {
    list($headerB64, $payloadB64, $signatureB64) = explode('.', $idToken);
    
    $header = json_decode(base64url_decode($headerB64), true);
    $payload = json_decode(base64url_decode($payloadB64), true);
    
    if ($payload['aud'] !== $clientId) {
        return false;
    }
    
    if ($payload['exp'] < time()) {
        return false;
    }
    
    return $payload;
}

$clientId = "your-google-client-id.apps.googleusercontent.com";
$idToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6...";

$payload = verifyGoogleIdToken($idToken, $clientId);

if ($payload) {
    echo "Token verified successfully!\n";
    echo "User ID: " . $payload['sub'] . "\n";
    echo "Email: " . $payload['email'] . "\n";
} else {
    echo "Token verification failed!\n";
}
?>