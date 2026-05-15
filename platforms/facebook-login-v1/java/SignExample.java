import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignExample {
    public static String validateAccessToken(String accessToken, String appSecret) 
            throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(appSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(accessToken.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static boolean verifyAppSecretProof(String accessToken, String appSecret, String appSecretProof) 
            throws NoSuchAlgorithmException, InvalidKeyException {
        String expectedProof = validateAccessToken(accessToken, appSecret);
        return expectedProof.equalsIgnoreCase(appSecretProof);
    }

    public static void main(String[] args) throws Exception {
        String appSecret = "your-facebook-app-secret";
        String accessToken = "EAA...";
        
        String appSecretProof = validateAccessToken(accessToken, appSecret);
        
        System.out.println("App Secret Proof:");
        System.out.println(appSecretProof);
        System.out.println();
        
        boolean isValid = verifyAppSecretProof(accessToken, appSecret, appSecretProof);
        System.out.println("Verification result: " + (isValid ? "VALID" : "INVALID"));
    }
}