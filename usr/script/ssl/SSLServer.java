import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

public class SSLServer {
    private static final int SERVER_PORT = 8443;
    private static final String SERVER_KEYSTORE_PASSWORD = "password";
    private static final String SERVER_TRUSTSTORE_PASSWORD = "password";

    public static void main(String[] args) {
        try {
            // 加载服务端证书和私钥
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("server.jks"), SERVER_KEYSTORE_PASSWORD.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, SERVER_KEYSTORE_PASSWORD.toCharArray());

            // 加载客户端信任的证书
            KeyStore trustStore = KeyStore.getInstance("JKS");
            trustStore.load(new FileInputStream("client.jks"), SERVER_TRUSTSTORE_PASSWORD.toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(trustStore);

            // 创建 SSL 上下文
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

            // 创建 SSL 服务器套接字
            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
            SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(SERVER_PORT);

            // 开始监听客户端请求
            System.out.println("SSL server started, listening on port " + SERVER_PORT);
            while (true) {
                SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
                System.out.println("New client connected from " + sslSocket.getInetAddress());
                new Thread(new ClientHandler(sslSocket)).start();
            }
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private SSLSocket sslSocket;

        public ClientHandler(SSLSocket sslSocket) {
            this.sslSocket = sslSocket;
        }

        @Override
        public void run() {
            try {
                // 获取输入输出流
                BufferedReader in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
                PrintWriter out = new PrintWriter(sslSocket.getOutputStream(), true);

                // 接收客户端发送的消息，并回复
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received message from " + sslSocket.getInetAddress() + ": " + message);
                    out.println("Server received message: " + message);
                    out.flush();
                }

                // 关闭连接
                sslSocket.close();
                System.out.println("Connection closed with " + sslSocket.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
