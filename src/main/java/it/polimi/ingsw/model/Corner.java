package src.main.java.it.polimi.ingsw.model;

import java.util.Optional;
import java.util.NoSuchElementException;

/**
 * This class is thought to be complementary to the class Cell.
 * Indeed Corner class has to interact in some way with Cell class.
 * Moreover, the importance of this class is mainly related to the fact
 * that we need to 'track' the resources and the items which are present
 * inside the PersonalBoard.
 */
public class Corner {

    private Coordinate coordinate;
    private Optional<Resource> resource;
    private Optional<Item> item;
    public boolean is_visible;
    private ResourceCard referenced_card;

    /**
     * In fact, this attribute make a lot of sense: board_coordinate
     * is not the effective coordinate of the corner inside the board
     */
    public BoardCellCoordinate board_coordinate;

    /**
     *
     * @param coordinate
     * @param resource
     * @param item
     * @param is_visible
     */
    public Corner(Coordinate coordinate, Optional<Resource> resource, Optional<Item> item,
                   boolean is_visible, ResourceCard card) {
        this.coordinate = coordinate;
        this.resource = resource;
        this.item = item;
        this.is_visible = is_visible;
        this.referenced_card = card;
    }

    public void setBoard_coordinate(Coordinate coord) {
        this.board_coordinate.setXY(coord);
    }

    /**
     * to check
     * @return
     * @throws NoSuchElementException
     */
    public Resource getResource() throws NoSuchElementException {
        return resource.get();
    }

}
