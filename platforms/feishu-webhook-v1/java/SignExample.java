import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignExample {
    public static String generateSignature(long timestamp, String secret) 
            throws NoSuchAlgorithmException, InvalidKeyException {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    public static String buildWebhookUrl(String webhookUrl, String secret) throws Exception {
        long timestamp = System.currentTimeMillis() / 1000;
        String signature = generateSignature(timestamp, secret);
        return webhookUrl + "&timestamp=" + timestamp + "&sign=" + java.net.URLEncoder.encode(signature, "UTF-8");
    }

    public static void main(String[] args) throws Exception {
        String webhookUrl = "https://open.feishu.cn/open-apis/bot/v2/hook/your-webhook-id";
        String secret = "your-feishu-bot-secret";

        String signedUrl = buildWebhookUrl(webhookUrl, secret);

        System.out.println("Signed Webhook URL:");
        System.out.println(signedUrl);
        System.out.println();
        System.out.println("Timestamp: " + (System.currentTimeMillis() / 1000));
        System.out.println("Signature: " + generateSignature(System.currentTimeMillis() / 1000, secret));
    }
}