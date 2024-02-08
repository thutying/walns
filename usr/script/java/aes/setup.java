
public class setup{
    public static void main(String[] args) throws Exception {
        String key = AESUtil.generateAESKey();
        String content = "Hello, world!";
        String encryptedContent = AESUtil.encryptAES(content, key);
        String decryptedContent = AESUtil.decryptAES(encryptedContent, key);
        System.out.println("key:" + key);
        System.out.println("原文：" + content);
        System.out.println("加密后：" + encryptedContent);
        System.out.println("解密后：" + decryptedContent);
    }

}
