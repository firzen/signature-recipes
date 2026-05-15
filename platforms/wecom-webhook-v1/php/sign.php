<?php
// PHP Version: 5.6+ (requires hash extension)
// Tested: PHP 5.6, PHP 7.x, PHP 8.x
function generateSignature($timestamp, $secret) {
    $stringToSign = $timestamp . "\n" . $secret;
    return hash('sha256', $stringToSign);
}

function buildWebhookUrl($webhookUrl, $secret) {
    $timestamp = time();
    $signature = generateSignature($timestamp, $secret);
    return $webhookUrl . "&timestamp=" . $timestamp . "&sign=" . $signature;
}

$webhookUrl = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=your-webhook-key";
$secret = "your-wecom-bot-secret";

$signedUrl = buildWebhookUrl($webhookUrl, $secret);

echo "Signed Webhook URL:\n";
echo $signedUrl . "\n\n";

echo "Timestamp: " . time() . "\n";
echo "Signature: " . generateSignature(time(), $secret) . "\n";
?>