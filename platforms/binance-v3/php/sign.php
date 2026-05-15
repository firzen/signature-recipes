<?php
// PHP Version: 5.6+ (requires hash extension)
// Tested: PHP 5.6, PHP 7.x, PHP 8.x
function signRequest($params, $secretKey) {
    ksort($params);
    
    $queryString = '';
    foreach ($params as $key => $value) {
        if ($queryString !== '') {
            $queryString .= '&';
        }
        $queryString .= urlencode($key) . '=' . urlencode($value);
    }
    
    $signature = hash_hmac('sha256', $queryString, $secretKey);
    
    return $queryString . '&signature=' . $signature;
}

function buildSignedRequest($apiKey, $secretKey, $params) {
    if (!isset($params['timestamp'])) {
        $params['timestamp'] = (int) (microtime(true) * 1000);
    }
    
    $signedQuery = signRequest($params, $secretKey);
    
    return array(
        'headers' => array(
            'X-MBX-APIKEY: ' . $apiKey
        ),
        'query' => $signedQuery
    );
}

$apiKey = "your_api_key";
$secretKey = "your_secret_key";

$params = array(
    'symbol' => 'BTCUSDT',
    'quantity' => 0.001,
    'price' => 40000.00
);

$request = buildSignedRequest($apiKey, $secretKey, $params);

echo "Headers:\n";
foreach ($request['headers'] as $header) {
    echo "  " . $header . "\n";
}

echo "\nQuery String:\n";
echo $request['query'] . "\n";
?>