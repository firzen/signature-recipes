<?php
// PHP Version: 5.6+ (requires hash extension)
// Tested: PHP 5.6, PHP 7.x, PHP 8.x
function hmacSha256($data, $key) {
    return hash_hmac('sha256', $data, $key, true);
}

function hashSha256($data) {
    return hash('sha256', $data, true);
}

function buildCanonicalRequest($method, $uri, $query, $headers, $payload) {
    ksort($headers);
    $canonicalHeaders = '';
    $signedHeaders = '';
    
    foreach ($headers as $key => $value) {
        $canonicalHeaders .= strtolower($key) . ':' . trim($value) . "\n";
        $signedHeaders .= strtolower($key) . ';';
    }
    $signedHeaders = rtrim($signedHeaders, ';');
    
    $payloadHash = bin2hex(hashSha256($payload));
    
    return "$method\n$uri\n$query\n$canonicalHeaders\n$signedHeaders\n$payloadHash";
}

function buildStringToSign($algorithm, $timestamp, $credentialScope, $canonicalRequest) {
    $canonicalRequestHash = bin2hex(hashSha256($canonicalRequest));
    return "$algorithm\n$timestamp\n$credentialScope\n$canonicalRequestHash";
}

function calculateSignature($stringToSign, $secretKey, $date, $region, $service) {
    $kDate = hmacSha256($date, "AWS4" . $secretKey);
    $kRegion = hmacSha256($region, $kDate);
    $kService = hmacSha256($service, $kRegion);
    $kSigning = hmacSha256("aws4_request", $kService);
    return bin2hex(hmacSha256($stringToSign, $kSigning));
}

function buildAuthorizationHeader($accessKey, $secretKey, $region, $service, $method, $uri, $query, $headers, $payload, $timestamp) {
    $algorithm = "AWS4-HMAC-SHA256";
    $date = substr($timestamp, 0, 8);
    $credentialScope = "$date/$region/$service/aws4_request";
    
    $canonicalRequest = buildCanonicalRequest($method, $uri, $query, $headers, $payload);
    $stringToSign = buildStringToSign($algorithm, $timestamp, $credentialScope, $canonicalRequest);
    $signature = calculateSignature($stringToSign, $secretKey, $date, $region, $service);
    
    ksort($headers);
    $signedHeaders = '';
    foreach ($headers as $key => $value) {
        $signedHeaders .= strtolower($key) . ';';
    }
    $signedHeaders = rtrim($signedHeaders, ';');
    
    return "$algorithm Credential=$accessKey/$credentialScope, SignedHeaders=$signedHeaders, Signature=$signature";
}

$accessKey = "AKIAIOSFODNN7EXAMPLE";
$secretKey = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY";
$region = "us-east-1";
$service = "s3";
$method = "GET";
$host = "examplebucket.s3.amazonaws.com";
$uri = "/";
$timestamp = gmdate("Ymd\THis\Z");

$headers = array(
    "Host" => $host,
    "X-Amz-Date" => $timestamp
);

$authorization = buildAuthorizationHeader($accessKey, $secretKey, $region, $service, $method, $uri, "", $headers, "", $timestamp);

echo "Authorization Header:\n";
echo $authorization . "\n\n";

echo "X-Amz-Date: " . $timestamp . "\n";
?>