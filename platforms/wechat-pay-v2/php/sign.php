<?php
function generateNonceStr($length = 32) {
    $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    $str = "";
    for ($i = 0; $i < $length; $i++) {
        $str .= $chars[mt_rand(0, strlen($chars) - 1)];
    }
    return $str;
}

function signParams($params, $key) {
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
    
    $signString .= '&key=' . $key;
    
    return strtoupper(md5($signString));
}

function buildPaymentRequest($appid, $mchId, $key, $body, $outTradeNo, $totalFee, $spbillCreateIp, $notifyUrl, $tradeType) {
    $params = array(
        'appid' => $appid,
        'mch_id' => $mchId,
        'nonce_str' => generateNonceStr(),
        'body' => $body,
        'out_trade_no' => $outTradeNo,
        'total_fee' => $totalFee,
        'spbill_create_ip' => $spbillCreateIp,
        'notify_url' => $notifyUrl,
        'trade_type' => $tradeType
    );
    
    $params['sign'] = signParams($params, $key);
    
    return $params;
}

$appid = "wx1234567890abcdef";
$mchId = "1234567890";
$key = "your_secret_key";

$params = buildPaymentRequest(
    $appid,
    $mchId,
    $key,
    "Test Payment",
    "202401010001",
    100,
    "192.168.1.1",
    "https://example.com/notify",
    "APP"
);

echo "Request Parameters:\n";
foreach ($params as $key => $value) {
    echo "  " . $key . ": " . $value . "\n";
}
?>