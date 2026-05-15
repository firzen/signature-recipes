<?php
function verifyWebhookSignature($payload, $signatureHeader, $webhookSecret) {
    $expectedSignature = hash_hmac('sha256', $payload, $webhookSecret);
    return hash_equals($expectedSignature, $signatureHeader);
}

$webhookSecret = "your_replicate_webhook_secret";
$payload = '{"id":"xxx","version":"xxx","status":"succeeded"}';
$signature = hash_hmac('sha256', $payload, $webhookSecret);

echo "Generated Signature (X-Replicate-Signature):\n";
echo $signature . "\n\n";

$isValid = verifyWebhookSignature($payload, $signature, $webhookSecret);
echo "Signature verification result: " . ($isValid ? "VALID" : "INVALID") . "\n";
?>