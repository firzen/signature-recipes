import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignExample {
    public static String generateSignature(long timestamp, String secret) 
            throws NoSuchAlgorithmException {
        String stringToSign = timestamp + "\n" + secret;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(stringToSign.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String buildWebhookUrl(String webhookUrl, String secret) throws Exception {
        long timestamp = System.currentTimeMillis() / 1000;
        String signature = generateSignature(timestamp, secret);
        return webhookUrl + "&timestamp=" + timestamp + "&sign=" + signature;
    }

    public static void main(String[] args) throws Exception {
        String webhookUrl = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=your-webhook-key";
        String secret = "your-wecom-bot-secret";

        String signedUrl = buildWebhookUrl(webhookUrl, secret);

        System.out.println("Signed Webhook URL:");
        System.out.println(signedUrl);
        System.out.println();
        System.out.println("Timestamp: " + (System.currentTimeMillis() / 1000));
        System.out.println("Signature: " + generateSignature(System.currentTimeMillis() / 1000, secret));
    }
}