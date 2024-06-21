package it.polimi.demo.model.board;

import it.polimi.demo.model.enumerations.Color;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents a pair of an integer id and a color. It is used
 * to identify a card and math it with its color. It is used in particular
 * to calculate pattern scores.
 */
public record IdColor(int id, Color color) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
