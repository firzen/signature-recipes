<?php
// PHP Version: 5.6+ (requires hash extension)
// Tested: PHP 5.6, PHP 7.x, PHP 8.x
function verifyWebhookSignature($payload, $signatureHeader, $webhookSecret) {
    list($timestampPart, $signaturePart) = explode(',', $signatureHeader);
    $timestamp = str_replace('t=', '', $timestampPart);
    $expectedSignature = hash_hmac('sha256', $timestamp . '.' . $payload, $webhookSecret);
    $actualSignature = str_replace('v1=', '', $signaturePart);
    return hash_equals($expectedSignature, $actualSignature);
}

$webhookSecret = "your_elevenlabs_webhook_secret";
$payload = '{"event":"audio.generation.completed","data":{"id":"gen-xxx"}}';
$timestamp = strval(time());
$signature = hash_hmac('sha256', $timestamp . '.' . $payload, $webhookSecret);
$signatureHeader = "t=" . $timestamp . ",v1=" . $signature;

echo "Generated Signature Header (X-ElevenLabs-Signature):\n";
echo $signatureHeader . "\n\n";

$isValid = verifyWebhookSignature($payload, $signatureHeader, $webhookSecret);
echo "Signature verification result: " . ($isValid ? "VALID" : "INVALID") . "\n";
?>