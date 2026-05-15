<?php
// PHP Version: 5.6+ (requires hash extension)
// Tested: PHP 5.6, PHP 7.x, PHP 8.x
function verifyWebhookSignature($payload, $signatureHeader, $webhookSecret) {
    $expectedSignature = 'sha256=' . hash_hmac('sha256', $payload, $webhookSecret);
    return hash_equals($expectedSignature, $signatureHeader);
}

$webhookSecret = "your_webhook_secret";
$payload = '{"action":"created","ref":"refs/heads/main"}';
$signature = hash_hmac('sha256', $payload, $webhookSecret);
$signatureHeader = "sha256=" . $signature;

echo "Generated Signature Header:\n";
echo $signatureHeader . "\n\n";

$isValid = verifyWebhookSignature($payload, $signatureHeader, $webhookSecret);
echo "Signature verification result: " . ($isValid ? "VALID" : "INVALID") . "\n";
?>