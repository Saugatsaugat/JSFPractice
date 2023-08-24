package WebSocket;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 *
 * @author saugat
 */
public class ThreadMessageSender extends Thread {

    public ThreadMessageSender() {
    }
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy  HH:mm:ss ");

    @Override
    public void run() {
       // System.out.println("RUN called");
        while (true) {
            sendMessage(formatter.format(Calendar.getInstance().getTime()));
            try {
                this.sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                Logger.getLogger(ThreadMessageSender.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public static void sendMessage(String message) {
        List<Session> list = ChatEndPoint.getSessions();
        // System.out.println("Notification list size: " + list.size());
        for (Session s : list) {
            if (s.isOpen()) {
                //System.out.println("Sending Notification To: " + s.getId());
                s.getAsyncRemote().sendText(message);
            }
        }
    }

}
