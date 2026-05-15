<?php
// PHP Version: 5.6+ (requires hash extension)
// Tested: PHP 5.6, PHP 7.x, PHP 8.x
function generateSignature($timestamp, $secret) {
    $stringToSign = $timestamp . "\n" . $secret;
    $hash = hash_hmac('sha256', $stringToSign, $secret, true);
    return base64_encode($hash);
}

function buildWebhookUrl($webhookUrl, $secret) {
    $timestamp = time();
    $signature = generateSignature($timestamp, $secret);
    return $webhookUrl . "&timestamp=" . $timestamp . "&sign=" . urlencode($signature);
}

$webhookUrl = "https://open.feishu.cn/open-apis/bot/v2/hook/your-webhook-id";
$secret = "your-feishu-bot-secret";

$signedUrl = buildWebhookUrl($webhookUrl, $secret);

echo "Signed Webhook URL:\n";
echo $signedUrl . "\n\n";

echo "Timestamp: " . time() . "\n";
echo "Signature: " . generateSignature(time(), $secret) . "\n";
?>