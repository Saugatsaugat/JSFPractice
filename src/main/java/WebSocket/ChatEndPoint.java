package WebSocket;

import java.io.Serializable;
import java.util.ArrayList;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author saugat
 */

@ServerEndpoint(value = "/chat")
public class ChatEndPoint implements Serializable {

    static ArrayList<Session> sessions = new ArrayList<>();

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println("Received message:" + message);

    }

    @OnOpen
    public void onOpen(Session session) {
        //System.out.println("onOpen: " + session.getId());
        sessions.add(session);
        // System.out.println("onOpen: Notification list size: " + sessions.size());
    }

    @OnClose
    public void onClose(Session session) {
        // System.out.println("onClose: " + session.getId());
        sessions.remove(session);
    }

    public static ArrayList<Session> getSessions() {
        return sessions;
    }

    public static void setSessions(ArrayList<Session> sessions) {
        ChatEndPoint.sessions = sessions;
    }
}
