import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class WebSocketServer {
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session) {
        clients.add(session);
        System.out.println("WebSocket 连接已经开启...");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("接收到来自客户端的消息：" + message);
        for(Session client: clients) {
            if(!client.equals(session)) {
                client.getBasicRemote().sendText(message);
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
        System.out.println("WebSocket 连接已经关闭...");
    }

    @OnError
    public void onError(Throwable error) {
        System.out.println("WebSocket 发生错误：" + error.getMessage());
    }
}
