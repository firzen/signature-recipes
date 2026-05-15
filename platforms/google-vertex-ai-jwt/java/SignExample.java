import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

public class SignExample {
    private static String base64urlEncode(byte[] data) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
    }

    public static String generateJWT(String privateKeyPem, String iss, String aud, long expiresIn) throws Exception {
        String header = "{\"alg\":\"RS256\",\"typ\":\"JWT\"}";
        long now = System.currentTimeMillis() / 1000;
        String payload = String.format(
            "{\"iss\":\"%s\",\"sub\":\"%s\",\"aud\":\"%s\",\"exp\":%d,\"iat\":%d}",
            iss, iss, aud, now + expiresIn, now
        );

        String encodedHeader = base64urlEncode(header.getBytes(StandardCharsets.UTF_8));
        String encodedPayload = base64urlEncode(payload.getBytes(StandardCharsets.UTF_8));

        String dataToSign = encodedHeader + "." + encodedPayload;

        String privateKeyClean = privateKeyPem
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replaceAll("\\s", "");
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyClean);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(dataToSign.getBytes(StandardCharsets.UTF_8));
        byte[] signatureBytes = signature.sign();

        String encodedSignature = base64urlEncode(signatureBytes);

        return encodedHeader + "." + encodedPayload + "." + encodedSignature;
    }

    public static void main(String[] args) throws Exception {
        String privateKey = "-----BEGIN PRIVATE KEY-----\nyour_private_key_here\n-----END PRIVATE KEY-----";
        String issuer = "your-service-account@your-project.iam.gserviceaccount.com";
        String audience = "https://aiplatform.googleapis.com/";

        String jwt = generateJWT(privateKey, issuer, audience, 3600);

        System.out.println("Generated JWT:");
        System.out.println(jwt);
        System.out.println();
        System.out.println("Use this JWT in Authorization header:");
        System.out.println("Authorization: Bearer " + jwt);
    }
}