package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Orientation;

public abstract class Card {
    private int id;
    public Orientation orientation;

    /**
     *
     */
    public Card(int id, Orientation orientation) {
        this.id = id;
        this.orientation = orientation;
    }
}


