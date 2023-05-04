package com.fis.crm.security;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncodeAndDecode {

    private static final String publicKeyContent = "-----BEGIN PUBLIC KEY-----\n" +
        "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAqqMkxGTabMd86tnhL9pW\n" +
        "d4YzvZg4L38cbP2+kr6VWDkYKCTClx+jxBnRkYmC4p4WVIiG3YJzpDCPgdSJoZO3\n" +
        "n3Qdv1X+dAgk4AS0CqJkiAltBQmPnC127wtfDvf4p50ecIdWRAu/w0HHInZ4bL2/\n" +
        "ZUQprQGByVIecOHYKQ28mt4xMiP+RMgdhCh91hexf6hdZz2LV8zWB+x6TYjQLjCc\n" +
        "9toqa4Ud+0REItSXt5TZSXF7tEqb7mnbT18kBnxhJMgGvt6VZNvl8NyOkfc5GbB+\n" +
        "yFwVqmtQ4veixKIlgATXUrPipn8i8qzcrnijkKKXK1d4s+jjGZVax9LIxhHYqiE6\n" +
        "Vu+aOOQl+rRsksTup4Q9mErarg146l+Ukdov0zetVVrohudg1uN71qLTT+lYkT0J\n" +
        "imkyl1cp1VfEsHN7+xhEc0lSfXBdCmrVsCuqxTd2Dd7zDY18ooUE82VD8bvsGolH\n" +
        "/u72PDz6UwC2k2x3o7jMD6b7rnI3QmHB2YfdnBXvu/bMpjQaJ6sa1JQb1irlShFa\n" +
        "hc5SAKEyEgKAbP1Lxl/tG8zJ9xwuQCTqC2HNEt1nz9QipcUiP04aJ0MtVuF6lctY\n" +
        "7Qcs2VKdV+PZ9rQA9Obx4RhgeTZKjo+IDSIV1Q4oydQBYT5HmVZ2wYxpU3kvsRfi\n" +
        "rIX9EUI9kFru8GyK+uK2YT8CAwEAAQ==\n" +
        "-----END PUBLIC KEY-----";

    private static final String privateKeyContent = "-----BEGIN PRIVATE KEY-----\n" +
        "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\n" +
        "kmAgH9oz8UnX4SYpBC3ia5DFEHse\n" +
        "-----END PRIVATE KEY-----";

    public static RSAPublicKey getRsaPublicKey() throws Exception{
            String content = publicKeyContent;
            content = content.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(content));
            return (RSAPublicKey) kf.generatePublic(keySpecX509);
    }

    public static RSAPrivateKey getRsaPrivateKey() throws Exception{
            String content = privateKeyContent;
            content = content.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(content));
            return (RSAPrivateKey) kf.generatePrivate(keySpecPKCS8);
    }

    public static String encrypt(String password) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        RSAPublicKey pbk = getRsaPublicKey();
        cipher.init(Cipher.ENCRYPT_MODE, pbk);
        byte[] encryptedPassword = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedPassword);
    }

    public static String decrypt(String password) throws Exception {
        byte[] encryptPassword = java.util.Base64.getDecoder().decode(password);
        Cipher cipher = Cipher.getInstance("RSA");
        RSAPrivateKey pvk = getRsaPrivateKey();
        cipher.init(Cipher.DECRYPT_MODE, pvk);
        byte[] decryptPassword = cipher.doFinal(encryptPassword);
        return new String(decryptPassword, "UTF-8");
    }
}
