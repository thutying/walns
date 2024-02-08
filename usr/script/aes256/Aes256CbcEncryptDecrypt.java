 
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class Aes256CbcEncryptDecrypt {

    private static final int AES_KEY_SIZE = 32;
    private static final int AES_BLOCK_SIZE = 16;

    public static void main(String[] args) {
        String plaintext = "This is a secret message!";
        long timestamp = System.currentTimeMillis();
        byte[] sha256 = sha256Hash(plaintext + timestamp + new String(new SecureRandom().generateSeed(16)));
        byte[] key = Arrays.copyOfRange(md5Hash(sha256), 0, AES_KEY_SIZE);
        byte[] iv = Arrays.copyOfRange(md5Hash(sha256), AES_KEY_SIZE, AES_KEY_SIZE + AES_BLOCK_SIZE);

        byte[] ciphertext = aes256cbcEncrypt(plaintext.getBytes(StandardCharsets.UTF_8), key, iv);
        System.out.println("Ciphertext: " + new String(ciphertext, StandardCharsets.UTF_8));

        byte[] decrypted = aes256cbcDecrypt(ciphertext, key, iv);
        System.out.println("Decrypted: " + new String(decrypted, StandardCharsets.UTF_8));
    }

    private static byte[] sha256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] md5Hash(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return digest.digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] aes256cbcEncrypt(byte[] input, byte[] key, byte[] iv) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] aes256cbcDecrypt(byte[] input, byte[] key, byte[] iv) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
