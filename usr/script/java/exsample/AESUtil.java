import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";  // 指定加密算法、工作模式和填充方式
    private static final String SECRET_KEY_ALGORITHM = "AES";  // 指定密钥算法

    /**
     * 对明文进行加密
     *
     * @param plaintext 明文字符串
     * @param key       加密密钥字符串
     * @param iv        初始化向量字符串
     * @return 加密后的密文字符串
     * @throws Exception 加密过程中出现异常
     */
    public static String encrypt(String plaintext, String key, String iv) throws Exception {
        byte[] keyBytes = key.getBytes();  // 将密钥字符串转换为字节数组
        byte[] ivBytes = iv.getBytes();  // 将初始化向量字符串转换为字节数组
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, SECRET_KEY_ALGORITHM);  // 创建密钥规范
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);  // 创建加密器
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);  // 创建初始化向量规范
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);  // 初始化加密器为加密模式，并传入密钥和初始化向量
        byte[] ciphertext = cipher.doFinal(plaintext.getBytes());  // 加密明文
        return Base64.getEncoder().encodeToString(ciphertext);  // 将密文进行 Base64 编码，并返回编码后的字符串
    }

    /**
     * 对密文进行解密
     *
     * @param ciphertext 密文字符串
     * @param key        加密密钥字符串
     * @param iv         初始化向量字符串
     * @return 解密后的明文字符串
     * @throws Exception 解密过程中出现异常
     */
    public static String decrypt(String ciphertext, String key, String iv) throws Exception {
        byte[] keyBytes = key.getBytes();  // 将密钥字符串转换为字节数组
        byte[] ivBytes = iv.getBytes();  // 将初始化向量字符串转换为字节数组
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, SECRET_KEY_ALGORITHM);  // 创建密钥规范
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);  // 创建加密器
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);  // 创建初始化向量规范
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);  // 初始化加密器为解密模式，并传入密钥和初始化向量
        byte[] plaintext = cipher.doFinal(Base64.getDecoder().decode(ciphertext));  // 解密密文
        return new String(plaintext);  // 返回解密后的明文字符串
    }

    public static void main(String[] args) throws Exception {
        String plaintext = "Hello, world!";  // 待加密的明文字符串
        String key = "12345678901234567890123456789012";  // 自定义的 AES

        String iv = "1234567890123456";  // 自定义的初始化向量字符串
        String ciphertext = encrypt(plaintext, key, iv);  // 对明文进行加密
        System.out.println("Ciphertext: " + ciphertext);  // 输出密文字符串
        String decrypted = decrypt(ciphertext, key, iv);  // 对密文进行解密
        System.out.println("Decrypted: " + decrypted);  // 输出解密后的明文字符串
    }
}

