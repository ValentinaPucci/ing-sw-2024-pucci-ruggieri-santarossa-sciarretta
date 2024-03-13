package src.main.java.it.polimi.ingsw.model;

public class GoldCard extends ResourceCard {

    // Attributes to calculate the score based on items or darken coordinates on the personal board;
    private Optional<Item> item_for_score;
    private Optional<Coordinate> coordinate_for_score;
    private Set<Resource> required_resources = new HashSet<>();

    /**
     *
     * @param coordinate_for_score
     * @param item_for_score
     */
    public GoldCard(Optional<Coordinate> coordinate_for_score, Optional<Item> item_for_score ) {
        this.coordinate_for_score = coordinate_for_score;
        this.item_for_score = item_for_score;
    }

    public fill_required_resources() {
        // Upload from card_database the required resources;

    }
}
