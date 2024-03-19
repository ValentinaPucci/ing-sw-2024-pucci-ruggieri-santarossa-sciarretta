package src.main.java.it.polimi.ingsw.model;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

public class GoldCard extends ResourceCard {

    // Attributes to calculate the score based on items or darken coordinates on the personal board;
    private Optional<Item> item_for_score;
    // 0=false, 1=true, the card has the darken_items requirements to calculate the score.
    private int coordinate_for_score;
    private int mushroom_required;
    private int leaf_required;
    private int butterfly_required;
    private int wolf_required;

    /**
     *
     * @param id
     * @param orientation
     */
    public GoldCard(int id, Orientation orientation, Color color) {
        super(id, orientation, color);
    }

    /**
     * Setter method for GoldCard.
     * @param item_for_score
     * @param coordinate_for_score
     */
    public void setGoldCard(Optional<Item> item_for_score, int coordinate_for_score, int mushroom_required, int leaf_required, int butterfly_required, int wolf_required) {
        this.item_for_score = item_for_score;
        this.coordinate_for_score = coordinate_for_score;
        this.mushroom_required = mushroom_required;
        this.leaf_required = leaf_required;
        this.butterfly_required = butterfly_required;
        this.wolf_required = wolf_required;
    }

    /**
     * 'filler' methods must be used every time we have to 'populate' a certain
     * data structures
     * @param resources
     */
//    public void fillResourcesRequired(Set<Resource> resources) {
//        resources_required.addAll(resources);
//    }

}






















