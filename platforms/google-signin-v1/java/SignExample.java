import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SignExample {
    public static String base64urlDecode(String data) {
        String padding = "";
        if (data.length() % 4 != 0) {
            padding = "=".repeat(4 - (data.length() % 4));
        }
        return new String(Base64.getUrlDecoder().decode(data + padding), StandardCharsets.UTF_8);
    }

    public static boolean verifyGoogleIdToken(String idToken, String clientId) {
        String[] parts = idToken.split("\\.");
        if (parts.length != 3) {
            return false;
        }
        
        String headerJson = base64urlDecode(parts[0]);
        String payloadJson = base64urlDecode(parts[1]);
        
        if (!payloadJson.contains("\"aud\":\"" + clientId + "\"")) {
            return false;
        }
        
        return true;
    }

    public static void main(String[] args) {
        String clientId = "your-google-client-id.apps.googleusercontent.com";
        String idToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6...";
        
        boolean isValid = verifyGoogleIdToken(idToken, clientId);
        
        System.out.println("Token verification result: " + (isValid ? "VALID" : "INVALID"));
    }
}