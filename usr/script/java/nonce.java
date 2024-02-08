import java.security.SecureRandom;
import java.util.Arrays;


public class nonce{
    private static byte[] generateNonce() {
        byte[] nonce = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(nonce);
        return nonce;
    }
    
    public static void main(String[] args){
        byte[] nonce = generateNonce();
        System.out.println(Arrays.toString(nonce));
    }
}
