<?php
// PHP Version: 5.6+ (requires hash extension)
// Tested: PHP 5.6, PHP 7.x, PHP 8.x
function generateNonceStr($length = 32) {
    $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    $str = "";
    for ($i = 0; $i < $length; $i++) {
        $str .= $chars[mt_rand(0, strlen($chars) - 1)];
    }
    return $str;
}

function buildStringToSign($method, $uri, $timestamp, $nonce, $body) {
    return $method . "\n" . $uri . "\n" . $timestamp . "\n" . $nonce . "\n" . $body . "\n";
}

function generateSignature($method, $uri, $timestamp, $nonce, $body, $apiV3Key) {
    $stringToSign = buildStringToSign($method, $uri, $timestamp, $nonce, $body);
    return hash_hmac('sha256', $stringToSign, $apiV3Key);
}

function buildAuthorizationHeader($mchid, $apiV3Key, $serialNo, $method, $uri, $body) {
    $timestamp = strval(time());
    $nonce = generateNonceStr();
    $signature = generateSignature($method, $uri, $timestamp, $nonce, $body, $apiV3Key);
    
    return sprintf(
        'WECHATPAY2-SHA256-RSA2048 mchid="%s",nonce_str="%s",signature="%s",timestamp="%s",serial_no="%s"',
        $mchid, $nonce, $signature, $timestamp, $serialNo
    );
}

$mchid = "1234567890";
$apiV3Key = "your_api_v3_key";
$serialNo = "your_certificate_serial_number";
$method = "POST";
$uri = "/v3/pay/transactions/app";
$body = '{"mchid":"1234567890","out_trade_no":"202401010001","amount":{"total":100},"description":"Test"}';

$authorization = buildAuthorizationHeader($mchid, $apiV3Key, $serialNo, $method, $uri, $body);

echo "Authorization Header:\n";
echo $authorization . "\n";
?>