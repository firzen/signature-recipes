import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignExample {
    public static String hmacSha256(String data, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return "sha256=" + sb.toString();
    }

    public static boolean verifyWebhookSignature(String payload, String signatureHeader, String webhookSecret) 
            throws NoSuchAlgorithmException, InvalidKeyException {
        String expectedSignature = hmacSha256(payload, webhookSecret);
        return expectedSignature.equalsIgnoreCase(signatureHeader);
    }

    public static void main(String[] args) throws Exception {
        String webhookSecret = "your_webhook_secret";
        String payload = "{\"action\":\"created\",\"ref\":\"refs/heads/main\"}";
        
        String signatureHeader = hmacSha256(payload, webhookSecret);
        System.out.println("Generated Signature Header:");
        System.out.println(signatureHeader);
        System.out.println();
        
        boolean isValid = verifyWebhookSignature(payload, signatureHeader, webhookSecret);
        System.out.println("Signature verification result: " + (isValid ? "VALID" : "INVALID"));
    }
}