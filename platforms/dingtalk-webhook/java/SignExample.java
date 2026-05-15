import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignExample {
    public static String generateSignature(String timestamp, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    public static void main(String[] args) throws Exception {
        String secret = "SECyour_secret";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String signature = generateSignature(timestamp, secret);
        
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Signature: " + signature);
        System.out.println();
        System.out.println("Webhook URL with signature:");
        System.out.println("https://oapi.dingtalk.com/robot/send?access_token=xxx&timestamp=" + timestamp + "&sign=" + java.net.URLEncoder.encode(signature, "UTF-8"));
    }
}