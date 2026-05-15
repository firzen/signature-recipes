<?php
// PHP Version: 5.2+ (requires json extension)
// Tested: PHP 5.2, PHP 5.6, PHP 7.x, PHP 8.x
function createAuthorizationHeader($apiKey) {
    return "Bearer " . $apiKey;
}

function buildRequest($apiKey, $model, $prompt, $maxTokens = 100) {
    $headers = array(
        "Authorization: " . createAuthorizationHeader($apiKey),
        "Content-Type: application/json"
    );
    
    $data = array(
        "model" => $model,
        "prompt" => $prompt,
        "max_tokens" => $maxTokens
    );
    
    return array(
        "headers" => $headers,
        "body" => json_encode($data)
    );
}

$apiKey = "sk-your-api-key";
$request = buildRequest($apiKey, "gpt-3.5-turbo", "Hello, world!", 100);

echo "Headers:\n";
foreach ($request["headers"] as $header) {
    echo "  " . $header . "\n";
}
echo "\nBody:\n";
echo $request["body"] . "\n";
?>