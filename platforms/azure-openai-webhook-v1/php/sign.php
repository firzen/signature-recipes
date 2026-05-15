<?php
function verifyWebhookSignature($payload, $signatureHeader, $webhookSecret) {
    list($timestamp, $actualSignature) = explode('.', $signatureHeader, 2);
    $expectedSignature = hash_hmac('sha256', $timestamp . '.' . $payload, $webhookSecret);
    return hash_equals($expectedSignature, $actualSignature);
}

$webhookSecret = "your_azure_openai_webhook_secret";
$payload = '{"eventType":"completion","data":{"id":"cmpl-xxx"}}';
$timestamp = strval(time());
$signature = hash_hmac('sha256', $timestamp . '.' . $payload, $webhookSecret);
$signatureHeader = $timestamp . "." . $signature;

echo "Generated Signature Header (Azure-Signature):\n";
echo $signatureHeader . "\n\n";

$isValid = verifyWebhookSignature($payload, $signatureHeader, $webhookSecret);
echo "Signature verification result: " . ($isValid ? "VALID" : "INVALID") . "\n";
?>