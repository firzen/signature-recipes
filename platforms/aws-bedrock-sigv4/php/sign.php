<?php
// PHP Version: 5.6+ (requires hash extension)
// Tested: PHP 5.6, PHP 7.x, PHP 8.x
function hmacSha256($data, $key) {
    return hash_hmac('sha256', $data, $key, true);
}

function hashSha256($data) {
    return hash('sha256', $data, true);
}

function buildCanonicalRequest($method, $uri, $queryString, $headers, $payload) {
    ksort($headers);
    $canonicalHeaders = '';
    foreach ($headers as $key => $value) {
        $canonicalHeaders .= strtolower($key) . ':' . trim($value) . "\n";
    }
    
    $signedHeaders = implode(';', array_map('strtolower', array_keys($headers)));
    $payloadHash = bin2hex(hashSha256($payload));
    
    return "$method\n$uri\n$queryString\n$canonicalHeaders\n$signedHeaders\n$payloadHash";
}

function buildStringToSign($timestamp, $date, $region, $service, $canonicalRequest) {
    $canonicalRequestHash = bin2hex(hashSha256($canonicalRequest));
    return "AWS4-HMAC-SHA256\n$timestamp\n$date/$region/$service/aws4_request\n$canonicalRequestHash";
}

function generateSignature($secretKey, $date, $region, $service, $stringToSign) {
    $kDate = hmacSha256($date, "AWS4$secretKey");
    $kRegion = hmacSha256($region, $kDate);
    $kService = hmacSha256($service, $kRegion);
    $kSigning = hmacSha256("aws4_request", $kService);
    return bin2hex(hmacSha256($stringToSign, $kSigning));
}

$accessKey = "your_aws_access_key";
$secretKey = "your_aws_secret_key";
$region = "us-east-1";
$service = "bedrock";
$method = "POST";
$uri = "/model/anthropic.claude-3-sonnet-20240229/v1/complete";
$payload = '{"prompt":"Hello","max_tokens_to_sample":100}';

$timestamp = gmdate("Ymd\THis\Z");
$date = substr($timestamp, 0, 8);

$headers = array(
    "host" => "bedrock.$region.amazonaws.com",
    "x-amz-date" => $timestamp,
    "content-type" => "application/json"
);

$canonicalRequest = buildCanonicalRequest($method, $uri, "", $headers, $payload);
$stringToSign = buildStringToSign($timestamp, $date, $region, $service, $canonicalRequest);
$signature = generateSignature($secretKey, $date, $region, $service, $stringToSign);

echo "Canonical Request:\n$canonicalRequest\n\n";
echo "String to Sign:\n$stringToSign\n\n";
echo "Signature:\n$signature\n";
?>