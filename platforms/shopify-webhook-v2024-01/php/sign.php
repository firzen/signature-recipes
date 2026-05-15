<?php
// PHP Version: 5.6+ (requires hash extension)
// Tested: PHP 5.6, PHP 7.x, PHP 8.x
function generateSignature($payload, $apiSecret) {
    $hash = hash_hmac('sha256', $payload, $apiSecret, true);
    return base64_encode($hash);
}

function verifyWebhookSignature($payload, $signatureHeader, $apiSecret) {
    $expectedSignature = generateSignature($payload, $apiSecret);
    return hash_equals($expectedSignature, $signatureHeader);
}

$apiSecret = "your_shopify_api_secret";
$payload = '{"id":123,"email":"test@example.com"}';
$signature = generateSignature($payload, $apiSecret);

echo "Generated Signature (X-Shopify-Hmac-Sha256):\n";
echo $signature . "\n\n";

$isValid = verifyWebhookSignature($payload, $signature, $apiSecret);
echo "Signature verification result: " . ($isValid ? "VALID" : "INVALID") . "\n";
?>