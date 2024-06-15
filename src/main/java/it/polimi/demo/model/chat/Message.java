package it.polimi.demo.model.chat;
import it.polimi.demo.model.Player;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents a message in a chat.
 */
public record Message(String text, Player sender) implements Serializable {

    @Serial
    private static final long serialVersionUID = 8360669716219257792L;
}
