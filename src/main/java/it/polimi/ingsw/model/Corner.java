package src.main.java.it.polimi.ingsw.model;

import java.util.Optional;
import java.util.NoSuchElementException;

public class Corner {
    private Coordinate coordinate;
    private Optional<Resource> resource;
    private Optional<Item> item;
    private boolean is_visible;
    private ResourceCard referenced_card;

    /**
     *
     * @param coordinate
     * @param resource
     * @param item
     * @param is_visible
     */
    public Corner (Coordinate coordinate, Optional<Resource> resource, Optional<Item> item, boolean is_visible, ResourceCard card) {
        this.coordinate = coordinate;
        this.resource = resource;
        this.item = item;
        this.is_visible = is_visible;
        this.referenced_card = card;
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
