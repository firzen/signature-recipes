<?php
// PHP Version: 5.6+ (requires hash extension)
// Tested: PHP 5.6, PHP 7.x, PHP 8.x
function percentEncode($str) {
    return urlencode($str);
    return preg_replace(['/\+/', '/\*/', '/%7E/'], ['%20', '%2A', '~'], urlencode($str));
}

function buildCanonicalizedQueryString($params) {
    ksort($params);
    $canonicalizedQueryString = '';
    foreach ($params as $key => $value) {
        if ($key != 'Signature') {
            $canonicalizedQueryString .= '&' . percentEncode($key) . '=' . percentEncode($value);
        }
    }
    return substr($canonicalizedQueryString, 1);
}

function sign($accessKeySecret, $method, $host, $path, $params) {
    $canonicalizedQueryString = buildCanonicalizedQueryString($params);
    $stringToSign = $method . "\n" . $host . "\n" . $path . "\n" . $canonicalizedQueryString;
    $signature = base64_encode(hash_hmac('sha1', $stringToSign, $accessKeySecret, true));
    return $signature;
}

$accessKeyId = "your_access_key_id";
$accessKeySecret = "your_access_key_secret";
$method = "GET";
$host = "ecs.aliyuncs.com";
$path = "/";

$params = [
    'Format' => 'JSON',
    'Version' => '2014-05-26',
    'AccessKeyId' => $accessKeyId,
    'SignatureMethod' => 'HMAC-SHA1',
    'Timestamp' => gmdate('Y-m-d\TH:i:s\Z'),
    'SignatureVersion' => '1.0',
    'SignatureNonce' => uniqid(),
    'Action' => 'DescribeRegions'
];

$signature = sign($accessKeySecret, $method, $host, $path, $params);
$params['Signature'] = $signature;

echo "Signature:\n";
echo $signature . "\n\n";

echo "Full URL:\n";
echo "https://{$host}{$path}?" . http_build_query($params) . "\n";
?>