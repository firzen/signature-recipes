import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import java.util.Base64;

public class SignExample {
    public static String generateSignature(Map<String, String> params, String privateKey) throws Exception {
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
        
        byte[] keyBytes = Base64.getDecoder().decode(privateKey.replace("-----BEGIN PRIVATE KEY-----\n", "").replace("\n-----END PRIVATE KEY-----", ""));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(key);
        signature.update(signString.toString().getBytes(StandardCharsets.UTF_8));
        
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    public static void main(String[] args) throws Exception {
        String privateKey = "-----BEGIN PRIVATE KEY-----\nyour_private_key_here\n-----END PRIVATE KEY-----";
        
        Map<String, String> params = new HashMap<>();
        params.put("app_id", "your_app_id");
        params.put("method", "alipay.trade.app.pay");
        params.put("charset", "UTF-8");
        params.put("sign_type", "RSA2");
        params.put("timestamp", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        params.put("version", "1.0");
        params.put("biz_content", "{\"out_trade_no\":\"202401010001\",\"total_amount\":\"0.01\",\"subject\":\"Test\"}");
        
        params.put("sign", generateSignature(params, privateKey));
        
        System.out.println("Request Parameters:");
        List<String> sortedKeys = new ArrayList<>(params.keySet());
        Collections.sort(sortedKeys);
        for (String k : sortedKeys) {
            System.out.println("  " + k + ": " + params.get(k));
        }
    }
}