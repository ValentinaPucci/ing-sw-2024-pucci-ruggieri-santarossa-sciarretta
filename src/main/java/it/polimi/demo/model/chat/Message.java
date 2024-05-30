package it.polimi.demo.model.chat;

import it.polimi.demo.model.Player;
import it.polimi.demo.model.interfaces.PlayerIC;
import java.io.Serializable;

public class Message implements Serializable {

    private final String text;
    private final PlayerIC sender;

    public Message(String text, PlayerIC sender) {
        this.text = text;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public PlayerIC getSender() {
        return sender;
    }
}
