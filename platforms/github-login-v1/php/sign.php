<?php
// PHP Version: 5.6+ (requires hash extension)
// Tested: PHP 5.6, PHP 7.x, PHP 8.x
function validateAccessToken($accessToken, $clientSecret) {
    return hash_hmac('sha256', $accessToken, $clientSecret);
}

$clientSecret = "your-github-client-secret";
$accessToken = "gho_...";

$signature = validateAccessToken($accessToken, $clientSecret);

echo "HMAC Signature:\n";
echo $signature . "\n";
?>