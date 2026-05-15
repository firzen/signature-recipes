import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignExample {
    public static String buildDataCheckString(Map<String, String> data) {
        List<String> sortedKeys = new ArrayList<>(data.keySet());
        Collections.sort(sortedKeys);
        
        StringBuilder checkString = new StringBuilder();
        for (String k : sortedKeys) {
            if (!k.equals("hash")) {
                if (checkString.length() > 0) {
                    checkString.append("\n");
                }
                checkString.append(k).append("=").append(data.get(k));
            }
        }
        return checkString.toString();
    }

    public static boolean verifyLogin(Map<String, String> data, String botToken) throws NoSuchAlgorithmException, InvalidKeyException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] secretKey = md.digest(botToken.getBytes(StandardCharsets.UTF_8));
        
        String dataCheckString = buildDataCheckString(data);
        
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(dataCheckString.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        
        return sb.toString().equalsIgnoreCase(data.get("hash"));
    }

    public static void main(String[] args) throws Exception {
        String botToken = "your_bot_token_without_bot_prefix";
        
        Map<String, String> data = new HashMap<>();
        data.put("auth_date", "1710000000");
        data.put("first_name", "John");
        data.put("id", "123456789");
        data.put("last_name", "Doe");
        data.put("username", "johndoe");
        
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] secretKey = md.digest(botToken.getBytes(StandardCharsets.UTF_8));
        
        String dataCheckString = buildDataCheckString(data);
        
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(dataCheckString.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        
        data.put("hash", sb.toString());
        
        System.out.println("Data Check String:");
        System.out.println(dataCheckString);
        System.out.println();
        
        System.out.println("Generated Hash:");
        System.out.println(sb.toString());
        System.out.println();
        
        boolean isValid = verifyLogin(data, botToken);
        System.out.println("Verification result: " + (isValid ? "VALID" : "INVALID"));
    }
}