package src.main.java.it.polimi.ingsw.model;

public class Corner {
    private Coordinate coordinate;
    private Optional<Resource> resource;
    private Optional<Item> item;
    private boolean is_visible;

    /**
     *
     * @param coordinate
     * @param resource
     * @param item
     * @param is_visible
     */
    public Corner(Coordinate coordinate, Optional<Resource> resource, Optional<Item> item, boolean is_visible) {
        this.coordinate = coordinate;
        this.resource = resource;
        this.item = item;
        this.is_visible = is_visible;
    }

    public Resource getResource() throws NoSuchElementException {
        return resource.get();
    }

}
