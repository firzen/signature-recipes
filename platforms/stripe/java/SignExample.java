import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class SignExample {
    public static String createAuthorizationHeader(String secretKey) {
        return "Bearer " + secretKey;
    }

    public static String hmacSha256(String data, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static boolean verifyWebhookSignature(String payload, String signatureHeader, String webhookSecret) 
            throws NoSuchAlgorithmException, InvalidKeyException {
        String timestamp = null;
        String signature = null;
        
        String[] parts = signatureHeader.split(",");
        for (String part : parts) {
            String[] kv = part.split("=", 2);
            if (kv[0].equals("t")) {
                timestamp = kv[1];
            } else if (kv[0].equals("v1")) {
                signature = kv[1];
            }
        }
        
        if (timestamp == null || signature == null) {
            return false;
        }
        
        String signedPayload = timestamp + "." + payload;
        String expectedSignature = hmacSha256(signedPayload, webhookSecret);
        
        return expectedSignature.equalsIgnoreCase(signature);
    }

    public static void main(String[] args) throws Exception {
        String secretKey = "sk_test_your_secret_key";
        String webhookSecret = "whsec_your_webhook_secret";
        
        System.out.println("Authorization Header: " + createAuthorizationHeader(secretKey));
        System.out.println();
        
        String payload = "{\"id\":\"evt_123\",\"object\":\"event\"}";
        long timestamp = System.currentTimeMillis() / 1000;
        String signedPayload = timestamp + "." + payload;
        String calculatedSignature = hmacSha256(signedPayload, webhookSecret);
        String signatureHeader = "t=" + timestamp + ",v1=" + calculatedSignature;
        
        System.out.println("Generated Signature Header:");
        System.out.println(signatureHeader);
        System.out.println();
        
        boolean isValid = verifyWebhookSignature(payload, signatureHeader, webhookSecret);
        System.out.println("Signature verification result: " + (isValid ? "VALID" : "INVALID"));
    }
}