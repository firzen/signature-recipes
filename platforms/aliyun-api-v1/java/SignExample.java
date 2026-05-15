import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class SignExample {
    public static String percentEncode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8")
                .replace("+", "%20")
                .replace("*", "%2A")
                .replace("%7E", "~");
    }

    public static String buildCanonicalizedQueryString(Map<String, String> params) throws UnsupportedEncodingException {
        List<String> sortedKeys = new ArrayList<>(params.keySet());
        Collections.sort(sortedKeys);
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sortedKeys.size(); i++) {
            String key = sortedKeys.get(i);
            String value = params.get(key);
            if (!"Signature".equals(key)) {
                if (i > 0) sb.append("&");
                sb.append(percentEncode(key)).append("=").append(percentEncode(value));
            }
        }
        return sb.toString();
    }

    public static String sign(String accessKeySecret, String method, String host, String path, 
                             Map<String, String> params) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String canonicalizedQueryString = buildCanonicalizedQueryString(params);
        String stringToSign = method + "\n" + host + "\n" + path + "\n" + canonicalizedQueryString;
        
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec secretKeySpec = new SecretKeySpec(accessKeySecret.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        
        return Base64.getEncoder().encodeToString(hash);
    }

    public static void main(String[] args) throws Exception {
        String accessKeyId = "your_access_key_id";
        String accessKeySecret = "your_access_key_secret";
        String method = "GET";
        String host = "ecs.aliyuncs.com";
        String path = "/";

        Map<String, String> params = new LinkedHashMap<>();
        params.put("Format", "JSON");
        params.put("Version", "2014-05-26");
        params.put("AccessKeyId", accessKeyId);
        params.put("SignatureMethod", "HMAC-SHA1");
        params.put("Timestamp", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()));
        params.put("SignatureVersion", "1.0");
        params.put("SignatureNonce", UUID.randomUUID().toString());
        params.put("Action", "DescribeRegions");

        String signature = sign(accessKeySecret, method, host, path, params);
        params.put("Signature", signature);

        System.out.println("Signature:");
        System.out.println(signature);
        System.out.println();
        System.out.println("Full URL:");
        System.out.println("https://" + host + path + "?" + buildCanonicalizedQueryString(params) + "&Signature=" + percentEncode(signature));
    }
}