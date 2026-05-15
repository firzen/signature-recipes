import java.util.HashMap;
import java.util.Map;

public class SignExample {
    public static String createAuthorizationHeader(String apiKey) {
        return "Bearer " + apiKey;
    }

    public static Map<String, Object> buildRequest(String apiKey, String model, String prompt, int maxTokens) {
        Map<String, Object> request = new HashMap<>();
        
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", createAuthorizationHeader(apiKey));
        headers.put("Content-Type", "application/json");
        request.put("headers", headers);
        
        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("prompt", prompt);
        body.put("max_tokens", maxTokens);
        request.put("body", body);
        
        return request;
    }

    public static void main(String[] args) {
        String apiKey = "sk-your-api-key";
        Map<String, Object> request = buildRequest(apiKey, "gpt-3.5-turbo", "Hello, world!", 100);
        
        System.out.println("Headers:");
        @SuppressWarnings("unchecked")
        Map<String, String> headers = (Map<String, String>) request.get("headers");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        
        System.out.println("\nBody:");
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) request.get("body");
        System.out.println("  model: " + body.get("model"));
        System.out.println("  prompt: " + body.get("prompt"));
        System.out.println("  max_tokens: " + body.get("max_tokens"));
    }
}