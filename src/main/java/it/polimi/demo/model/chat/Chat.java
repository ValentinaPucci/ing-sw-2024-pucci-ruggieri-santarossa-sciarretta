package it.polimi.demo.model.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Chat implements Serializable {

    private Stack<Message> messages;

    public Chat() {
        this.messages = new Stack<>();
    }

    public LinkedList<Message> getMessages() {
        return new LinkedList<>(messages);
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public Message getLastMessage() {
        return messages.peek();
    }
}