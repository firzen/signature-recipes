<?php
// PHP Version: 5.6+ (requires hash extension)
// Tested: PHP 5.6, PHP 7.x, PHP 8.x
function generateSignature($timestamp, $secret) {
    $stringToSign = $timestamp . "\n" . $secret;
    $hash = hash_hmac('sha256', $stringToSign, $secret, true);
    return base64_encode($hash);
}

$secret = "SECyour_secret";
$timestamp = strval(time() * 1000);
$signature = generateSignature($timestamp, $secret);

echo "Timestamp: " . $timestamp . "\n";
echo "Signature: " . $signature . "\n";
echo "\nWebhook URL with signature:\n";
echo "https://oapi.dingtalk.com/robot/send?access_token=xxx&timestamp=" . $timestamp . "&sign=" . urlencode($signature) . "\n";
?>