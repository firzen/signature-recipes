<?php
function verifyWebhookSignature($payload, $signatureHeader, $webhookSecret) {
    list($timestampPart, $signaturePart) = explode(',', $signatureHeader);
    $timestamp = str_replace('t=', '', $timestampPart);
    $expectedSignature = hash_hmac('sha256', $timestamp . '.' . $payload, $webhookSecret);
    $actualSignature = str_replace('v1=', '', $signaturePart);
    return hash_equals($expectedSignature, $actualSignature);
}

$webhookSecret = "your_openai_webhook_secret";
$payload = '{"event":"completion","data":{"id":"cmpl-xxx"}}';
$timestamp = strval(time());
$signature = hash_hmac('sha256', $timestamp . '.' . $payload, $webhookSecret);
$signatureHeader = "t=" . $timestamp . ",v1=" . $signature;

echo "Generated Signature Header:\n";
echo $signatureHeader . "\n\n";

$isValid = verifyWebhookSignature($payload, $signatureHeader, $webhookSecret);
echo "Signature verification result: " . ($isValid ? "VALID" : "INVALID") . "\n";
?>