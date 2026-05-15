import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignExample {
    public static String generateSignature(String timestamp, String body, String signingSecret) 
            throws NoSuchAlgorithmException, InvalidKeyException {
        String stringToSign = "v0:" + timestamp + ":" + body;
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(signingSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return "v0=" + sb.toString();
    }

    public static boolean verifyRequest(String timestamp, String body, String signatureHeader, String signingSecret) 
            throws NoSuchAlgorithmException, InvalidKeyException {
        String expectedSignature = generateSignature(timestamp, body, signingSecret);
        return expectedSignature.equalsIgnoreCase(signatureHeader);
    }

    public static void main(String[] args) throws Exception {
        String signingSecret = "your_slack_signing_secret";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String body = "{\"token\":\"abc123\",\"team_id\":\"T123\"}";
        String signature = generateSignature(timestamp, body, signingSecret);
        
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Generated Signature: " + signature);
        System.out.println();
        
        boolean isValid = verifyRequest(timestamp, body, signature, signingSecret);
        System.out.println("Signature verification result: " + (isValid ? "VALID" : "INVALID"));
    }
}