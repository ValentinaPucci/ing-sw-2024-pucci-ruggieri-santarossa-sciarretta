package src.main.java.it.polimi.ingsw.model;

public class Card {
    public enum Orientation {
        FRONT,
        BACK
    }
    private int id;

    private Orientation orientation;

    /**
     *
     * @param id
     * @param orientation
     */
    public Card(int id, Orientation orientation) {
        this.id = id;
        this.orientation = orientation;
    }

}


