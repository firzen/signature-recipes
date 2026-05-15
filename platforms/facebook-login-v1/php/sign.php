<?php
function validateAccessToken($accessToken, $appSecret) {
    $signature = hash_hmac('sha256', $accessToken, $appSecret);
    return $signature;
}

function verifyAppSecretProof($accessToken, $appSecret, $appSecretProof) {
    $expectedProof = hash_hmac('sha256', $accessToken, $appSecret);
    return hash_equals($expectedProof, $appSecretProof);
}

$appSecret = "your-facebook-app-secret";
$accessToken = "EAA...";

$appSecretProof = validateAccessToken($accessToken, $appSecret);

echo "App Secret Proof:\n";
echo $appSecretProof . "\n\n";

$isValid = verifyAppSecretProof($accessToken, $appSecret, $appSecretProof);
echo "Verification result: " . ($isValid ? "VALID" : "INVALID") . "\n";
?>