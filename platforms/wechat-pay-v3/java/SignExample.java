import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignExample {
    public static String generateNonceStr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String buildStringToSign(String method, String uri, String timestamp, String nonce, String body) {
        return method + "\n" + uri + "\n" + timestamp + "\n" + nonce + "\n" + body + "\n";
    }

    public static String generateSignature(String method, String uri, String timestamp, String nonce, 
                                          String body, String apiV3Key) throws NoSuchAlgorithmException, InvalidKeyException {
        String stringToSign = buildStringToSign(method, uri, timestamp, nonce, body);
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(apiV3Key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String buildAuthorizationHeader(String mchid, String apiV3Key, String serialNo, 
                                                  String method, String uri, String body) throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonce = generateNonceStr();
        String signature = generateSignature(method, uri, timestamp, nonce, body, apiV3Key);
        
        return String.format(
            "WECHATPAY2-SHA256-RSA2048 mchid=\"%s\",nonce_str=\"%s\",signature=\"%s\",timestamp=\"%s\",serial_no=\"%s\"",
            mchid, nonce, signature, timestamp, serialNo
        );
    }

    public static void main(String[] args) throws Exception {
        String mchid = "1234567890";
        String apiV3Key = "your_api_v3_key";
        String serialNo = "your_certificate_serial_number";
        String method = "POST";
        String uri = "/v3/pay/transactions/app";
        String body = "{\"mchid\":\"1234567890\",\"out_trade_no\":\"202401010001\",\"amount\":{\"total\":100},\"description\":\"Test\"}";
        
        String authorization = buildAuthorizationHeader(mchid, apiV3Key, serialNo, method, uri, body);
        
        System.out.println("Authorization Header:");
        System.out.println(authorization);
    }
}