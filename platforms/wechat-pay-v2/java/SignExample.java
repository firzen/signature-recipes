import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

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

    public static String signParams(Map<String, String> params, String key) {
        List<String> sortedKeys = new ArrayList<>(params.keySet());
        Collections.sort(sortedKeys);
        
        StringBuilder signString = new StringBuilder();
        for (String k : sortedKeys) {
            String v = params.get(k);
            if (v != null && !v.isEmpty() && !k.equals("sign")) {
                if (signString.length() > 0) {
                    signString.append("&");
                }
                signString.append(k).append("=").append(v);
            }
        }
        
        signString.append("&key=").append(key);
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(signString.toString().getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> buildPaymentRequest(String appid, String mchId, String key, 
            String body, String outTradeNo, int totalFee, String spbillCreateIp, 
            String notifyUrl, String tradeType) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("mch_id", mchId);
        params.put("nonce_str", generateNonceStr());
        params.put("body", body);
        params.put("out_trade_no", outTradeNo);
        params.put("total_fee", String.valueOf(totalFee));
        params.put("spbill_create_ip", spbillCreateIp);
        params.put("notify_url", notifyUrl);
        params.put("trade_type", tradeType);
        
        params.put("sign", signParams(params, key));
        
        return params;
    }

    public static void main(String[] args) {
        String appid = "wx1234567890abcdef";
        String mchId = "1234567890";
        String key = "your_secret_key";
        
        Map<String, String> params = buildPaymentRequest(
            appid, mchId, key,
            "Test Payment",
            "202401010001",
            100,
            "192.168.1.1",
            "https://example.com/notify",
            "APP"
        );
        
        System.out.println("Request Parameters:");
        List<String> sortedKeys = new ArrayList<>(params.keySet());
        Collections.sort(sortedKeys);
        for (String k : sortedKeys) {
            System.out.println("  " + k + ": " + params.get(k));
        }
    }
}