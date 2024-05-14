package it.polimi.demo.model.cards;

import it.polimi.demo.model.enumerations.Orientation;

import java.io.Serializable;

public abstract class Card implements Serializable {

    protected int id;
    public Orientation orientation;
    public String type;

    public Card(int id, Orientation orientation) {
        this.id = id;
        this.orientation = orientation;
    }

    public int getId(){return this.id;}

    public String toString(){
        return "Card: " + this.id + " " + this.orientation;
    }
}


