<?php
function generateSignature($timestamp, $body, $signingSecret) {
    $stringToSign = "v0:" . $timestamp . ":" . $body;
    return "v0=" . hash_hmac('sha256', $stringToSign, $signingSecret);
}

function verifyRequest($timestamp, $body, $signatureHeader, $signingSecret) {
    $expectedSignature = generateSignature($timestamp, $body, $signingSecret);
    return hash_equals($expectedSignature, $signatureHeader);
}

$signingSecret = "your_slack_signing_secret";
$timestamp = strval(time());
$body = '{"token":"abc123","team_id":"T123"}';
$signature = generateSignature($timestamp, $body, $signingSecret);

echo "Timestamp: " . $timestamp . "\n";
echo "Generated Signature: " . $signature . "\n\n";

$isValid = verifyRequest($timestamp, $body, $signature, $signingSecret);
echo "Signature verification result: " . ($isValid ? "VALID" : "INVALID") . "\n";
?>