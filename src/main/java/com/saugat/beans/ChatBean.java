package com.saugat.beans;

import java.io.Serializable;

/**
 *
 * @author saugat
 */
public class ChatBean implements Serializable {

    private String receivedMessage;
    private String typedMessage;

    public String getReceivedMessage() {
        return receivedMessage;
    }

    public void setReceivedMessage(String receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    public String getTypedMessage() {
        return typedMessage;
    }

    public void setTypedMessage(String typedMessage) {
        this.typedMessage = typedMessage;
    }

}
