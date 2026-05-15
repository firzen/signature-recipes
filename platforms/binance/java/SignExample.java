import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignExample {
    public static String signRequest(Map<String, String> params, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        List<String> sortedKeys = new ArrayList<>(params.keySet());
        Collections.sort(sortedKeys);
        
        StringBuilder queryString = new StringBuilder();
        for (String key : sortedKeys) {
            if (queryString.length() > 0) {
                queryString.append("&");
            }
            queryString.append(encodeURIComponent(key))
                       .append("=")
                       .append(encodeURIComponent(params.get(key)));
        }
        
        String signature = hmacSha256(queryString.toString(), secretKey);
        return queryString.toString() + "&signature=" + signature;
    }

    private static String hmacSha256(String data, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static String encodeURIComponent(String s) {
        try {
            return java.net.URLEncoder.encode(s, "UTF-8")
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("%7E", "~");
        } catch (Exception e) {
            return s;
        }
    }

    public static void main(String[] args) throws Exception {
        String apiKey = "your_api_key";
        String secretKey = "your_secret_key";
        
        Map<String, String> params = new HashMap<>();
        params.put("symbol", "BTCUSDT");
        params.put("quantity", "0.001");
        params.put("price", "40000.00");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        
        String signedQuery = signRequest(params, secretKey);
        
        System.out.println("Headers:");
        System.out.println("  X-MBX-APIKEY: " + apiKey);
        
        System.out.println("\nQuery String:");
        System.out.println(signedQuery);
    }
}