package it.polimi.demo.model.chat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Stack;

public class Chat implements Serializable {

    @Serial
    private static final long serialVersionUID = 6540600707148771629L;
    private final Stack<Message> messages;

    public Chat() {
        this.messages = new Stack<>();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public Message getLastMessage() {
        return messages.peek();
    }
}