package Controller;

import com.saugat.beans.ChatBean;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author saugat
 */
@Named
@ViewScoped
public class ChatWebSocketController implements Serializable {

    private ChatBean chatBean;

    public ChatBean getChatBean() {
        return chatBean;
    }

    public void setChatBean(ChatBean chatBean) {
        this.chatBean = chatBean;
    }

    @PostConstruct
    public void init() {
        chatBean = new ChatBean();
    }
}
