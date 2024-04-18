package it.polimi.demo.model.cards;

import it.polimi.demo.model.enumerations.Orientation;

public abstract class Card {

    protected int id;
    public Orientation orientation;
    public String type;

    public Card(int id, Orientation orientation) {
        this.id = id;
        this.orientation = orientation;
    }
}


