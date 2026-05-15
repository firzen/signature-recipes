<?php
function validateAccessToken($accessToken, $clientSecret) {
    return hash_hmac('sha256', $accessToken, $clientSecret);
}

$clientSecret = "your-github-client-secret";
$accessToken = "gho_...";

$signature = validateAccessToken($accessToken, $clientSecret);

echo "HMAC Signature:\n";
echo $signature . "\n";
?>