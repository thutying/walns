import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

    private static final String AES_ALGORITHM = "AES";
    private static final String CHARSET = "UTF-8";

    
    /**
    * 生成AES密钥
    * @return AES密钥
    * @throws Exception
    */
    public static String generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyBytes = secretKey.getEncoded();
        System.out.println(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    /**
     * AES加密
     *
     * @param content 待加密内容
     * @param key     加密密钥
     * @return 加密后的内容
     * @throws Exception
     */
    public static String encryptAES(String content, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        byte[] keyBytes = Base64.getDecoder().decode(key);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(content.getBytes(CHARSET));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * AES解密
     *
     * @param content 待解密内容
     * @param key     解密密钥
     * @return 解密后的内容
     * @throws Exception
     */
    public static String decryptAES(String content, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        byte[] keyBytes = Base64.getDecoder().decode(key);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(content));
        return new String(decryptedBytes, CHARSET);
    }

}

