package src.main.java.it.polimi.ingsw.model;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

public class GoldCard extends ResourceCard {

    // Attributes to calculate the score based on items or darken coordinates on the personal board;
    private Optional<Item> item_for_score;
    private Optional<Coordinate> coordinate_for_score;
    private Set<Resource> resources_required = new HashSet<>();

    /**
     *
     * @param id
     * @param orientation
     */
    public GoldCard(int id, Orientation orientation) {
        super(id, orientation);
    }

    /**
     * Setter method for GoldCard.
     * @param item_for_score
     * @param coordinate_for_score
     */
    public void setGoldCard(Optional<Item> item_for_score, Optional<Coordinate> coordinate_for_score) {
        this.item_for_score = item_for_score;
        this.coordinate_for_score = coordinate_for_score;
    }

    /**
     * 'filler' methods must be used every time we have to 'populate' a certain
     * data structures
     * @param resources
     */
    public void fillResourcesRequired(Set<Resource> resources) {
        resources_required.addAll(resources);
    }

}






















