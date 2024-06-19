package it.polimi.demo.model.board;

import it.polimi.demo.model.enumerations.Color;

import java.io.Serial;
import java.io.Serializable;

public record IdColor(int id, Color color) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
