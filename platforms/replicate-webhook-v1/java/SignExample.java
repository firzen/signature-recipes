import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignExample {
    public static String generateSignature(String payload, String webhookSecret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(webhookSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static boolean verifyWebhookSignature(String payload, String signatureHeader, String webhookSecret) 
            throws NoSuchAlgorithmException, InvalidKeyException {
        String expectedSignature = generateSignature(payload, webhookSecret);
        return expectedSignature.equalsIgnoreCase(signatureHeader);
    }

    public static void main(String[] args) throws Exception {
        String webhookSecret = "your_replicate_webhook_secret";
        String payload = "{\"id\":\"xxx\",\"version\":\"xxx\",\"status\":\"succeeded\"}";
        String signature = generateSignature(payload, webhookSecret);
        
        System.out.println("Generated Signature (X-Replicate-Signature):");
        System.out.println(signature);
        System.out.println();
        
        boolean isValid = verifyWebhookSignature(payload, signature, webhookSecret);
        System.out.println("Signature verification result: " + (isValid ? "VALID" : "INVALID"));
    }
}