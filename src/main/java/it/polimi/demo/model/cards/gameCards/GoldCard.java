package it.polimi.demo.model.cards.gameCards;

import it.polimi.demo.model.board.Corner;
import it.polimi.demo.model.enumerations.Color;
import it.polimi.demo.model.enumerations.Orientation;

/**
 * Represents a GoldCard, which is a subclass of Resource.
 * This class includes requirements needed to play the card.
 * */
public class GoldCard extends ResourceCard {


    /**
     * Corner coverage required
     */
    private boolean isCornerCoverageRequired;
    /**
     * Potion required
     */
    private boolean isPotionRequired;
    /**
     * Feather required
     */
    private boolean isFeatherRequired;
    private boolean isParchmentRequired;
    private int mushroom_required;
    private int leaf_required;
    private int butterfly_required;
    private int wolf_required;

    /**
     * Constructor
     * @param id card id
     * @param orientation card orientation
     * @param color card color
     */
    public GoldCard(int id, Orientation orientation, Color color) {

        super(id, orientation, color);
        this.isCornerCoverageRequired = false;
        this.isPotionRequired = false;
        this.isFeatherRequired = false;
        this.isParchmentRequired = false;
        this.leaf_required = 0;
        this.butterfly_required = 0;
        this.wolf_required = 0;
        this.mushroom_required = 0;

    }

    /**
     * Constructor
     * @param id card id
     * @param orientation card orientation
     * @param color card color
     * @param points card points
     * @param actual_corners card corners
     */
    public GoldCard(int id, Orientation orientation, Color color, int points, Corner[][] actual_corners) {
        super(id, orientation, color, points, actual_corners);
    }

    /**
     * setter for the requirements
     * @param mushroom_required
     * @param leaf_required
     * @param butterfly_required
     * @param wolf_required
     * @param isCornerCoverageRequired
     * @param isPotionRequired
     * @param isFeatherRequired
     * @param isParchmentRequired
     */
    public void setGoldCard(int mushroom_required,
                            int leaf_required,
                            int butterfly_required,
                            int wolf_required,
                            boolean isCornerCoverageRequired,
                            boolean isPotionRequired,
                            boolean isFeatherRequired,
                            boolean isParchmentRequired
                            ) {
        this.mushroom_required = mushroom_required;
        this.leaf_required = leaf_required;
        this.butterfly_required = butterfly_required;
        this.wolf_required = wolf_required;
        this.isCornerCoverageRequired = isCornerCoverageRequired;
        this.isPotionRequired = isPotionRequired;
        this.isFeatherRequired = isFeatherRequired;
        this.isParchmentRequired = isParchmentRequired;

    }

    /**
     * getter for the requirements
     * @return requirements
     */
    public boolean getIsCornerCoverageRequired() {
        return isCornerCoverageRequired;
    }

    /**
     * getter for the requirements
     * @return requirements
     */
    public boolean getIsPotionRequired() {
        return isPotionRequired;
    }

    /**
     * getter for the requirements
     * @return requirements
     */
    public boolean getIsFeatherRequired() {
        return isFeatherRequired;
    }

    /**
     * getter for the requirements
     * @return requirements
     */
    public boolean getIsParchmentRequired() {
        return isParchmentRequired;
    }

    /**
     * getter for the requirements
     * @return requirements
     */
    public int getMushroomRequired() {
        return mushroom_required;
    }

    /**
     * getter for the requirements
     * @return requirements
     */
    public int getLeafRequired() {
        return leaf_required;
    }

    /**
     * getter for the requirements
     * @return requirements
     */
    public int getButterflyRequired() {
        return butterfly_required;
    }

    /**
     * getter for the requirements
     * @return requirements
     */
    public int getWolfRequired() {
        return wolf_required;
    }

}






















