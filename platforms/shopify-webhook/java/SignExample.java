import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignExample {
    public static String generateSignature(String payload, String apiSecret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    public static boolean verifyWebhookSignature(String payload, String signatureHeader, String apiSecret) 
            throws NoSuchAlgorithmException, InvalidKeyException {
        String expectedSignature = generateSignature(payload, apiSecret);
        return expectedSignature.equalsIgnoreCase(signatureHeader);
    }

    public static void main(String[] args) throws Exception {
        String apiSecret = "your_shopify_api_secret";
        String payload = "{\"id\":123,\"email\":\"test@example.com\"}";
        String signature = generateSignature(payload, apiSecret);
        
        System.out.println("Generated Signature (X-Shopify-Hmac-Sha256):");
        System.out.println(signature);
        System.out.println();
        
        boolean isValid = verifyWebhookSignature(payload, signature, apiSecret);
        System.out.println("Signature verification result: " + (isValid ? "VALID" : "INVALID"));
    }
}