package it.polimi.demo.model.chat;
import it.polimi.demo.model.Player;

import java.io.Serializable;

public class Message implements Serializable {

    private final String text;
    private final Player sender;

    public Message(String text, Player sender) {
        this.text = text;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public Player getSender() {
        return sender;
    }
}
