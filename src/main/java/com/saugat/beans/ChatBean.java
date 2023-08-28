package com.saugat.beans;

import WebSocket.ChatEndPoint;
import WebSocket.ThreadMessageSender;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author saugat
 */
@Named
@ViewScoped
public class ChatBean implements Serializable {

    public ChatBean() {
    }

    private String message;

    public void sendNotification() {
        
        ThreadMessageSender.sendMessage(message);
    }

    public List getNotificationReceiverList() {
        return ChatEndPoint.getSessions();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
