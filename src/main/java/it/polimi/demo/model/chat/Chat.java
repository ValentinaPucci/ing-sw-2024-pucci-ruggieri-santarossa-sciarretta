package it.polimi.demo.model.chat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Stack;

/**
 * This class represents a chat between two or more users.
 */
public class Chat implements Serializable {

    @Serial
    private static final long serialVersionUID = 6540600707148771629L;
    private final Stack<Message> messages;

    /**
     * Creates a new chat.
     */
    public Chat() {
        this.messages = new Stack<>();
    }

    /**
     * Adds a message to the chat.
     *
     * @param message the message to add
     */
    public void addMessage(Message message) {
        messages.add(message);
    }

    /**
     * Returns the last message in the chat.
     *
     * @return the last message in the chat
     */
    public Message getLastMessage() {
        return messages.peek();
    }
}