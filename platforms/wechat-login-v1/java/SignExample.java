import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

public class SignExample {
    public static String buildQueryString(Map<String, String> params) throws Exception {
        List<String> sortedKeys = new ArrayList<>(params.keySet());
        Collections.sort(sortedKeys);
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sortedKeys.size(); i++) {
            String key = sortedKeys.get(i);
            String value = params.get(key);
            if (i > 0) sb.append("&");
            sb.append(key).append("=").append(URLEncoder.encode(value, "UTF-8"));
        }
        return sb.toString();
    }

    public static String signQueryString(Map<String, String> params, String appSecret) 
            throws NoSuchAlgorithmException, InvalidKeyException, Exception {
        String queryString = buildQueryString(params);
        
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(appSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(queryString.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String appId = "your-wechat-app-id";
        String appSecret = "your-wechat-app-secret";
        String code = "001xxx";

        Map<String, String> params = new LinkedHashMap<>();
        params.put("appid", appId);
        params.put("secret", appSecret);
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");

        String signature = signQueryString(params, appSecret);

        System.out.println("Query String:");
        System.out.println(buildQueryString(params));
        System.out.println();
        System.out.println("Signature:");
        System.out.println(signature);
    }
}