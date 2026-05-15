<?php
function createAuthorizationHeader($secretKey) {
    return "Bearer " . $secretKey;
}

function verifyWebhookSignature($payload, $signatureHeader, $webhookSecret) {
    $parts = explode(',', $signatureHeader);
    $timestamp = null;
    $signature = null;
    
    foreach ($parts as $part) {
        $kv = explode('=', $part, 2);
        if ($kv[0] === 't') {
            $timestamp = $kv[1];
        } elseif ($kv[0] === 'v1') {
            $signature = $kv[1];
        }
    }
    
    if ($timestamp === null || $signature === null) {
        return false;
    }
    
    $signedPayload = $timestamp . '.' . $payload;
    $expectedSignature = hash_hmac('sha256', $signedPayload, $webhookSecret);
    
    return hash_equals($expectedSignature, $signature);
}

$secretKey = "sk_test_your_secret_key";
$webhookSecret = "whsec_your_webhook_secret";

echo "Authorization Header: " . createAuthorizationHeader($secretKey) . "\n\n";

$payload = '{"id":"evt_123","object":"event"}';
$timestamp = time();
$signedPayload = $timestamp . '.' . $payload;
$calculatedSignature = hash_hmac('sha256', $signedPayload, $webhookSecret);
$signatureHeader = "t={$timestamp},v1={$calculatedSignature}";

echo "Generated Signature Header:\n";
echo $signatureHeader . "\n\n";

$isValid = verifyWebhookSignature($payload, $signatureHeader, $webhookSecret);
echo "Signature verification result: " . ($isValid ? "VALID" : "INVALID") . "\n";
?>