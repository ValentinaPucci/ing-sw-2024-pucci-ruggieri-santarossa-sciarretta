package it.polimi.demo.model.board;

import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.model.enumerations.Item;
import it.polimi.demo.model.enumerations.Resource;

import java.io.Serializable;
import java.util.NoSuchElementException;


/**
 * This class is thought to be complementary to the class Cell.
 * Indeed, Corner class has to interact in some way with Cell class.
 * Moreover, the importance of this class is mainly related to the fact
 * that we need to 'track' the resources and the items which are present
 * inside the PersonalBoard.
 */
public class Corner implements Serializable {

    private Coordinate coordinate;
    public Resource resource;
    public Item item;
    public boolean is_visible;
    public ResourceCard reference_card;

    /**
     * In fact, this attribute make a lot of sense: board_coordinate
     * is not the effective coordinate of the corner inside the board
     */
    public BoardCellCoordinate board_coordinate;

    /**
     * Constructor for the class Corner.
     */
    public Corner() {
        this.is_visible = true;
        this.board_coordinate = new BoardCellCoordinate(0, 0);
    }

    /**
     * Constructor for the class Corner.
     * @param board_coordinate the coordinate of the corner
     */
    public Corner(BoardCellCoordinate board_coordinate) {
        this.board_coordinate = board_coordinate;
        this.is_visible = true;
        this.resource = null;
        this.item = null;
    }

    /**
     * Constructor for the class Corner.
     * @param board_coordinate the coordinate of the corner
     * @param reference_card the reference card
     */
    public Corner(BoardCellCoordinate board_coordinate, ResourceCard reference_card) {
        this.board_coordinate = board_coordinate;
        this.is_visible = true;
        this.resource = null;
        this.item = null;
        this.reference_card = reference_card;
    }

    /**
     * setter for the resource
     * @param resource the resource
     */
    public void setCornerResource(Resource resource) {
        this.resource = resource;
    }

    /**
     * setter for the item
     * @param item the item
     */
    public void setCornerItem(Item item) {
        this.item = item;
    }

    /**
     * set a corner as empty
     */
    public void setCornerItemEmpty() {this.item = null;}

    /**
     * set a corner as empty
     */
    public void setCornerResourceEmpty() {this.resource = null;}

    /**
     * set a corner as empty
     */
    public void setEmpty(){
        this.resource = null;
        this.item = null;
    }

    /**
     * getter for the resource
     */
    public Resource getResource() throws NoSuchElementException {
        return resource;
    }

    /**
     * getter for the item
     */
    public Item getItem() throws NoSuchElementException {
        return item;
    }

    /**
     * getter for the coordinate
     * @return the coordinate
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * setter for the coordinate
     * @param coordinate the coordinate
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * equals method for the class Corner.
     * @param obj the object to compare
     * @return true if the two objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        Corner corner = (Corner) obj;
        if (this.is_visible && corner.is_visible) {
            if (this.resource != null && corner.resource != null) {
                return this.resource.equals(corner.resource);
            } else {
                return false;
            }
        } else
            return false;
    }

    /**
     * toString method for the class Corner.
     * @return a string representing the corner
     */
    @Override
    public String toString() {
        return "Corner{" +
                "is_visible=" + is_visible +
               // ", resource=" + (resource.isPresent() ? resource.get().name() : "null") +
                '}';
    }

    public void setReference_card(ResourceCard reference_card) {
        this.reference_card = reference_card;
    }
}
