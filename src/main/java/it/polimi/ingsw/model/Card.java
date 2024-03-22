package it.polimi.ingsw.model;

public class Card {
    private int id;
    public Orientation orientation;

    /**
     *
     */
    public Card(int id, Orientation orientation) {
        this.id = id;
        this.orientation = this.ChooseOrientation();

    }

    public Orientation ChooseOrientation(){
        return orientation;
    }

}


