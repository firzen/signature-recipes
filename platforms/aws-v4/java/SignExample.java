import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignExample {
    private static byte[] hmacSha256(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA256");
        mac.init(secretKeySpec);
        return mac.doFinal(data);
    }

    private static byte[] hashSha256(String data) throws NoSuchAlgorithmException {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
        return md.digest(data.getBytes(StandardCharsets.UTF_8));
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static String buildCanonicalRequest(String method, String uri, String query, 
            Map<String, String> headers, String payload) throws NoSuchAlgorithmException {
        Map<String, String> sortedHeaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        sortedHeaders.putAll(headers);
        
        StringBuilder canonicalHeaders = new StringBuilder();
        StringBuilder signedHeaders = new StringBuilder();
        
        for (Map.Entry<String, String> entry : sortedHeaders.entrySet()) {
            canonicalHeaders.append(entry.getKey().toLowerCase()).append(":")
                           .append(entry.getValue().trim()).append("\n");
            signedHeaders.append(entry.getKey().toLowerCase()).append(";");
        }
        String signedHeadersStr = signedHeaders.toString().replaceAll(";$", "");
        
        String payloadHash = bytesToHex(hashSha256(payload));
        
        return method + "\n" + uri + "\n" + query + "\n" + canonicalHeaders + "\n" + signedHeadersStr + "\n" + payloadHash;
    }

    private static String buildStringToSign(String algorithm, String timestamp, String credentialScope, 
            String canonicalRequest) throws NoSuchAlgorithmException {
        String canonicalRequestHash = bytesToHex(hashSha256(canonicalRequest));
        return algorithm + "\n" + timestamp + "\n" + credentialScope + "\n" + canonicalRequestHash;
    }

    private static String calculateSignature(String stringToSign, String secretKey, String date, 
            String region, String service) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] kDate = hmacSha256(date.getBytes(StandardCharsets.UTF_8), ("AWS4" + secretKey).getBytes(StandardCharsets.UTF_8));
        byte[] kRegion = hmacSha256(region.getBytes(StandardCharsets.UTF_8), kDate);
        byte[] kService = hmacSha256(service.getBytes(StandardCharsets.UTF_8), kRegion);
        byte[] kSigning = hmacSha256("aws4_request".getBytes(StandardCharsets.UTF_8), kService);
        return bytesToHex(hmacSha256(stringToSign.getBytes(StandardCharsets.UTF_8), kSigning));
    }

    public static String buildAuthorizationHeader(String accessKey, String secretKey, String region, 
            String service, String method, String uri, String query, Map<String, String> headers, 
            String payload, String timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
        String algorithm = "AWS4-HMAC-SHA256";
        String date = timestamp.substring(0, 8);
        String credentialScope = date + "/" + region + "/" + service + "/aws4_request";
        
        String canonicalRequest = buildCanonicalRequest(method, uri, query, headers, payload);
        String stringToSign = buildStringToSign(algorithm, timestamp, credentialScope, canonicalRequest);
        String signature = calculateSignature(stringToSign, secretKey, date, region, service);
        
        Map<String, String> sortedHeaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        sortedHeaders.putAll(headers);
        StringBuilder signedHeaders = new StringBuilder();
        for (String key : sortedHeaders.keySet()) {
            signedHeaders.append(key.toLowerCase()).append(";");
        }
        String signedHeadersStr = signedHeaders.toString().replaceAll(";$", "");
        
        return algorithm + " Credential=" + accessKey + "/" + credentialScope + 
               ", SignedHeaders=" + signedHeadersStr + ", Signature=" + signature;
    }

    public static void main(String[] args) throws Exception {
        String accessKey = "AKIAIOSFODNN7EXAMPLE";
        String secretKey = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY";
        String region = "us-east-1";
        String service = "s3";
        String method = "GET";
        String host = "examplebucket.s3.amazonaws.com";
        String uri = "/";
        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'")
                .format(new java.util.Date());
        
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", host);
        headers.put("X-Amz-Date", timestamp);
        
        String authorization = buildAuthorizationHeader(accessKey, secretKey, region, service, 
                method, uri, "", headers, "", timestamp);
        
        System.out.println("Authorization Header:");
        System.out.println(authorization);
        System.out.println();
        System.out.println("X-Amz-Date: " + timestamp);
    }
}