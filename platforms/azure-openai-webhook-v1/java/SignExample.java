import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignExample {
    public static String generateSignature(String timestamp, String payload, String webhookSecret) 
            throws NoSuchAlgorithmException, InvalidKeyException {
        String stringToSign = timestamp + "." + payload;
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(webhookSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return timestamp + "." + sb.toString();
    }

    public static boolean verifyWebhookSignature(String payload, String signatureHeader, String webhookSecret) 
            throws NoSuchAlgorithmException, InvalidKeyException {
        String[] parts = signatureHeader.split("\\.", 2);
        String timestamp = parts[0];
        String actualSignature = parts[1];
        
        String stringToSign = timestamp + "." + payload;
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(webhookSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString().equals(actualSignature);
    }

    public static void main(String[] args) throws Exception {
        String webhookSecret = "your_azure_openai_webhook_secret";
        String payload = "{\"eventType\":\"completion\",\"data\":{\"id\":\"cmpl-xxx\"}}";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signatureHeader = generateSignature(timestamp, payload, webhookSecret);
        
        System.out.println("Generated Signature Header (Azure-Signature):");
        System.out.println(signatureHeader);
        System.out.println();
        
        boolean isValid = verifyWebhookSignature(payload, signatureHeader, webhookSecret);
        System.out.println("Signature verification result: " + (isValid ? "VALID" : "INVALID"));
    }
}