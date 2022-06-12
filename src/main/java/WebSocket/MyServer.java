package WebSocket;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class MyServer {
    public static void main(String[] args) {
        startWebServer();
    }
    public static void startWebServer(){
        Thread thread = new Thread(MyServer::bootServer);
        thread.start();
    }
    public static void bootServer() {
        Server server = new Server(9917);
        WebSocketHandler wsHandler = new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.register(MyEchoSocket.class);
            }
        };
        ContextHandler context = new ContextHandler();
        context.setContextPath("/");
        context.setHandler(wsHandler);

        server.setHandler(context);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
