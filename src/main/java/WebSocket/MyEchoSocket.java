package WebSocket;

import hamang.ChatDriver;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
public class MyEchoSocket implements WebSocketListener {
    private Session outbound;

    public MyEchoSocket() {
        System.out.println("class loaded " + this.getClass());
    }
    @Override
    public void onWebSocketConnect(Session session) {
        this.outbound = session;
    }
    @Override
    public void onWebSocketError(Throwable cause)
    {
        cause.printStackTrace(System.err);
    }
    @Override
    public void onWebSocketText(String message)
    {
        if ((outbound != null) && (outbound.isOpen()))
        {
            System.out.println("Received:"+message);
            ChatDriver.enqueueMessage(message);
//            outbound.getRemote().sendString("Success", null);
        }
    }
    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len)
    {
        //.
    }
    @Override
    public void onWebSocketClose(int statusCode, String reason)
    {
        System.out.println("Close, statusCode = " + statusCode + ", reasone = " + reason);
        this.outbound = null;
    }
}