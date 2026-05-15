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
        return "t=" + timestamp + ",v1=" + sb.toString();
    }

    public static boolean verifyWebhookSignature(String payload, String signatureHeader, String webhookSecret) 
            throws NoSuchAlgorithmException, InvalidKeyException {
        String[] parts = signatureHeader.split(",");
        String timestamp = parts[0].replace("t=", "");
        String actualSignature = parts[1].replace("v1=", "");
        
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
        String webhookSecret = "your_openai_webhook_secret";
        String payload = "{\"event\":\"completion\",\"data\":{\"id\":\"cmpl-xxx\"}}";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signatureHeader = generateSignature(timestamp, payload, webhookSecret);
        
        System.out.println("Generated Signature Header:");
        System.out.println(signatureHeader);
        System.out.println();
        
        boolean isValid = verifyWebhookSignature(payload, signatureHeader, webhookSecret);
        System.out.println("Signature verification result: " + (isValid ? "VALID" : "INVALID"));
    }
}