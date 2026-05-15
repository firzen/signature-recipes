<?php
// PHP Version: 5.6+ (requires openssl extension)
// Tested: PHP 5.6, PHP 7.x, PHP 8.x
function base64url_encode($data) {
    return rtrim(strtr(base64_encode($data), '+/', '-_'), '=');
}

function generateJWT($privateKeyPem, $iss, $aud, $expiresIn = 3600) {
    $header = json_encode(['alg' => 'RS256', 'typ' => 'JWT']);
    $payload = json_encode([
        'iss' => $iss,
        'sub' => $iss,
        'aud' => $aud,
        'exp' => time() + $expiresIn,
        'iat' => time()
    ]);
    
    $encodedHeader = base64url_encode($header);
    $encodedPayload = base64url_encode($payload);
    
    $dataToSign = $encodedHeader . '.' . $encodedPayload;
    
    openssl_sign($dataToSign, $signature, $privateKeyPem, OPENSSL_ALGO_SHA256);
    $encodedSignature = base64url_encode($signature);
    
    return $encodedHeader . '.' . $encodedPayload . '.' . $encodedSignature;
}

$privateKey = <<<EOD
-----BEGIN PRIVATE KEY-----
your_private_key_here
-----END PRIVATE KEY-----
EOD;

$issuer = "your-service-account@your-project.iam.gserviceaccount.com";
$audience = "https://aiplatform.googleapis.com/";

$jwt = generateJWT($privateKey, $issuer, $audience);

echo "Generated JWT:\n";
echo $jwt . "\n\n";

echo "Use this JWT in Authorization header:\n";
echo "Authorization: Bearer " . $jwt . "\n";
?>