package it.polimi.demo.model.cards;

import it.polimi.demo.model.enumerations.Orientation;

import java.io.Serial;
import java.io.Serializable;

public abstract class Card implements Serializable {

    @Serial
    private static final long serialVersionUID = 5159343861481678780L;
    protected int id;
    public Orientation orientation;
    public String type;

    public Card(int id, Orientation orientation) {
        this.id = id;
        this.orientation = orientation;
    }
    
    public int getId() {return this.id;}

    public String toString(){
        return "Card: " + this.id + " " + this.orientation;
    }
}


