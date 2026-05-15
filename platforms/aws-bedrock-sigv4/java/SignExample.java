import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignExample {
    public static byte[] hmacSha256(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA256");
        mac.init(secretKeySpec);
        return mac.doFinal(data);
    }

    public static byte[] hashSha256(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(data.getBytes(StandardCharsets.UTF_8));
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String buildCanonicalRequest(String method, String uri, String queryString, 
                                              Map<String, String> headers, String payload) throws NoSuchAlgorithmException {
        List<String> sortedKeys = new ArrayList<>(headers.keySet());
        Collections.sort(sortedKeys, String.CASE_INSENSITIVE_ORDER);
        
        StringBuilder canonicalHeaders = new StringBuilder();
        StringBuilder signedHeaders = new StringBuilder();
        for (int i = 0; i < sortedKeys.size(); i++) {
            String key = sortedKeys.get(i).toLowerCase();
            String value = headers.get(sortedKeys.get(i)).trim();
            canonicalHeaders.append(key).append(":").append(value).append("\n");
            if (i > 0) signedHeaders.append(";");
            signedHeaders.append(key);
        }
        
        String payloadHash = bytesToHex(hashSha256(payload));
        
        return method + "\n" + uri + "\n" + queryString + "\n" + canonicalHeaders + signedHeaders + "\n" + payloadHash;
    }

    public static String buildStringToSign(String timestamp, String date, String region, 
                                          String service, String canonicalRequest) throws NoSuchAlgorithmException {
        String canonicalRequestHash = bytesToHex(hashSha256(canonicalRequest));
        return "AWS4-HMAC-SHA256\n" + timestamp + "\n" + date + "/" + region + "/" + service + "/aws4_request\n" + canonicalRequestHash;
    }

    public static String generateSignature(String secretKey, String date, String region, 
                                          String service, String stringToSign) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] kDate = hmacSha256(date.getBytes(StandardCharsets.UTF_8), ("AWS4" + secretKey).getBytes(StandardCharsets.UTF_8));
        byte[] kRegion = hmacSha256(region.getBytes(StandardCharsets.UTF_8), kDate);
        byte[] kService = hmacSha256(service.getBytes(StandardCharsets.UTF_8), kRegion);
        byte[] kSigning = hmacSha256("aws4_request".getBytes(StandardCharsets.UTF_8), kService);
        return bytesToHex(hmacSha256(stringToSign.getBytes(StandardCharsets.UTF_8), kSigning));
    }

    public static void main(String[] args) throws Exception {
        String accessKey = "your_aws_access_key";
        String secretKey = "your_aws_secret_key";
        String region = "us-east-1";
        String service = "bedrock";
        String method = "POST";
        String uri = "/model/anthropic.claude-3-sonnet-20240229/v1/complete";
        String payload = "{\"prompt\":\"Hello\",\"max_tokens_to_sample\":100}";

        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'").format(new Date());
        String date = timestamp.substring(0, 8);

        Map<String, String> headers = new HashMap<>();
        headers.put("host", "bedrock." + region + ".amazonaws.com");
        headers.put("x-amz-date", timestamp);
        headers.put("content-type", "application/json");

        String canonicalRequest = buildCanonicalRequest(method, uri, "", headers, payload);
        String stringToSign = buildStringToSign(timestamp, date, region, service, canonicalRequest);
        String signature = generateSignature(secretKey, date, region, service, stringToSign);

        System.out.println("Canonical Request:\n" + canonicalRequest + "\n");
        System.out.println("String to Sign:\n" + stringToSign + "\n");
        System.out.println("Signature:\n" + signature);
    }
}