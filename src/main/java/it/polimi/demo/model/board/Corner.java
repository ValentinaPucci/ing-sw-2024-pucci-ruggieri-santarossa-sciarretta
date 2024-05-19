package it.polimi.demo.model.board;

import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.model.enumerations.Item;
import it.polimi.demo.model.enumerations.Resource;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public Corner() {
        this.is_visible = true;
        this.board_coordinate = new BoardCellCoordinate(0, 0);
    }

    /**
     *
     * @param board_coordinate
     */
    public Corner(BoardCellCoordinate board_coordinate) {
        this.board_coordinate = board_coordinate;
        this.is_visible = true;
        this.resource = null;
        this.item = null;
    }

    public Corner(BoardCellCoordinate board_coordinate, ResourceCard reference_card) {
        this.board_coordinate = board_coordinate;
        this.is_visible = true;
        this.resource = null;
        this.item = null;
        this.reference_card = reference_card;
    }

//    /**
//     * @param coordinate
//     * @param resource
//     * @param item
//     * @param is_visible
//     * @param card
//     */
////    public Corner(Coordinate coordinate, Optional<Resource> resource, Optional<Item> item,
////                  boolean is_visible, ResourceCard card) {
////        this.coordinate = coordinate;
////        this.resource = resource;
////        this.item = item;
////        this.is_visible = is_visible;
////        this.reference_card = card;
////        this.board_coordinate = new BoardCellCoordinate(0, 0);
////        this.board_coordinate.setXY(coordinate);
////    }

    // Set corner
// Crea un set resource. per settare resource e poi crea anche un set item per settare item.
    public void setCornerResource(Resource resource) {
        this.resource = resource;
    }

    public void setCornerItem(Item item) {
        this.item = item;
    }

    public void setCornerItemEmpty() {this.item = null;}

    public void setCornerResourceEmpty() {this.resource = null;}

    public void setEmpty(){
        this.resource = null;
        this.item = null;
    }

//    public void setBoard_coordinate(Coordinate coord) {
//        this.board_coordinate.setXY(coord);
//    }

    /**
     * to check
     *
     * @return
     * @throws NoSuchElementException
     */
    public Resource getResource() throws NoSuchElementException {
        return resource;
    }

    public Item getItem() throws NoSuchElementException {
        return item;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

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

    @Override
    public String toString() {
        return "Corner{" +
                "is_visible=" + is_visible +
               // ", resource=" + (resource.isPresent() ? resource.get().name() : "null") +
                '}';
    }

    public BoardCellCoordinate getBoard_coordinate() {
        return board_coordinate;
    }

    public void setReference_card(ResourceCard reference_card) {
        this.reference_card = reference_card;
    }
}
