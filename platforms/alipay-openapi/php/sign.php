<?php
function generateSignature($params, $privateKey) {
    ksort($params);
    
    $signString = '';
    foreach ($params as $k => $v) {
        if ($v !== '' && $k !== 'sign') {
            if ($signString !== '') {
                $signString .= '&';
            }
            $signString .= $k . '=' . $v;
        }
    }
    
    $key = openssl_pkey_get_private($privateKey);
    openssl_sign($signString, $signature, $key, OPENSSL_ALGO_SHA256);
    openssl_free_key($key);
    
    return base64_encode($signature);
}

$privateKey = "-----BEGIN PRIVATE KEY-----\nyour_private_key_here\n-----END PRIVATE KEY-----";

$params = array(
    'app_id' => 'your_app_id',
    'method' => 'alipay.trade.app.pay',
    'charset' => 'UTF-8',
    'sign_type' => 'RSA2',
    'timestamp' => date('Y-m-d H:i:s'),
    'version' => '1.0',
    'biz_content' => '{"out_trade_no":"202401010001","total_amount":"0.01","subject":"Test"}'
);

$params['sign'] = generateSignature($params, $privateKey);

echo "Request Parameters:\n";
foreach ($params as $key => $value) {
    echo "  " . $key . ": " . $value . "\n";
}
?>