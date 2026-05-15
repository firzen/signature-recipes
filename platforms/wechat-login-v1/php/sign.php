<?php
// PHP Version: 5.6+ (requires hash extension)
// Tested: PHP 5.6, PHP 7.x, PHP 8.x
function buildQueryString($params) {
    ksort($params);
    $queryString = '';
    foreach ($params as $key => $value) {
        if ($queryString) $queryString .= '&';
        $queryString .= $key . '=' . urlencode($value);
    }
    return $queryString;
}

function signQueryString($params, $appSecret) {
    $queryString = buildQueryString($params);
    return hash_hmac('sha256', $queryString, $appSecret);
}

$appId = "your-wechat-app-id";
$appSecret = "your-wechat-app-secret";
$code = "001xxx";

$params = [
    'appid' => $appId,
    'secret' => $appSecret,
    'js_code' => $code,
    'grant_type' => 'authorization_code'
];

$signature = signQueryString($params, $appSecret);

echo "Query String:\n";
echo buildQueryString($params) . "\n\n";
echo "Signature:\n";
echo $signature . "\n";
?>