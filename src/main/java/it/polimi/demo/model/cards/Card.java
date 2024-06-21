package it.polimi.demo.model.cards;

import it.polimi.demo.model.enumerations.Orientation;

import java.io.Serial;
import java.io.Serializable;

/**
 * This abstract class represents a generic card of the game.
 * It has an id, an orientation and a type . The Orientation is an enum that can be either FRONT or BACK.
 * The type is either "Resource", "Gold" or "Starter".
 */
public abstract class Card implements Serializable {

    @Serial
    private static final long serialVersionUID = 5159343861481678780L;
    protected int id;
    public Orientation orientation;
    public String type;

    /**
     * Constructor of the class Card.
     * @param id the id of the card
     * @param orientation the orientation of the card
     */
    public Card(int id, Orientation orientation) {
        this.id = id;
        this.orientation = orientation;
    }
    
    public int getId() {return this.id;}

    public String toString(){
        return "Card: " + this.id + " " + this.orientation;
    }
}


