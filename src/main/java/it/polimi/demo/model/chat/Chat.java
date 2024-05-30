package it.polimi.demo.model.chat;

import java.io.Serializable;
import java.util.Stack;

public class Chat implements Serializable {

    private Stack<Message> messages;

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