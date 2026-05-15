<?php
// PHP Version: 5.6+ (requires hash extension)
// Tested: PHP 5.6, PHP 7.x, PHP 8.x
function buildDataCheckString($data) {
    ksort($data);
    
    $checkString = '';
    foreach ($data as $k => $v) {
        if ($k !== 'hash') {
            if ($checkString !== '') {
                $checkString .= "\n";
            }
            $checkString .= $k . '=' . $v;
        }
    }
    
    return $checkString;
}

function verifyLogin($data, $botToken) {
    $secretKey = hash('sha256', $botToken, true);
    $dataCheckString = buildDataCheckString($data);
    $expectedHash = hash_hmac('sha256', $dataCheckString, $secretKey);
    
    return hash_equals($expectedHash, $data['hash']);
}

$botToken = "your_bot_token_without_bot_prefix";

$data = array(
    'auth_date' => '1710000000',
    'first_name' => 'John',
    'id' => '123456789',
    'last_name' => 'Doe',
    'username' => 'johndoe'
);

$secretKey = hash('sha256', $botToken, true);
$dataCheckString = buildDataCheckString($data);
$hash = hash_hmac('sha256', $dataCheckString, $secretKey);

$data['hash'] = $hash;

echo "Data Check String:\n";
echo $dataCheckString . "\n\n";

echo "Generated Hash:\n";
echo $hash . "\n\n";

$isValid = verifyLogin($data, $botToken);
echo "Verification result: " . ($isValid ? "VALID" : "INVALID") . "\n";
?>