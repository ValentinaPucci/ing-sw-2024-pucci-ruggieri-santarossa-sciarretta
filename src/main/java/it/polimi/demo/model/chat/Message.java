package it.polimi.demo.model.chat;
import it.polimi.demo.model.Player;

import java.io.Serial;
import java.io.Serializable;

public record Message(String text, Player sender) implements Serializable {

    @Serial
    private static final long serialVersionUID = 8360669716219257792L;
}
