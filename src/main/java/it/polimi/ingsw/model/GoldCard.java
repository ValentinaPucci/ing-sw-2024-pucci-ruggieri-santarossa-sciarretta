package it.polimi.ingsw.model;

import java.util.Optional;

public class GoldCard extends ResourceCard {


    private boolean isCornerCoverageRequired;
    private boolean isPotionRequired;
    private boolean isFeatherRequired;
    private boolean isParchmentRequired;
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
     *
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
                            boolean isParchmentRequired) {
        this.mushroom_required = mushroom_required;
        this.leaf_required = leaf_required;
        this.butterfly_required = butterfly_required;
        this.wolf_required = wolf_required;
        this.isCornerCoverageRequired = isCornerCoverageRequired;
        this.isPotionRequired = isPotionRequired;
        this.isFeatherRequired = isFeatherRequired;
        this.isParchmentRequired = isParchmentRequired;
    }

    public boolean getIsCornerCoverageRequired() {
        return isCornerCoverageRequired;
    }

    public boolean getIsPotionRequired() {
        return isPotionRequired;
    }

    public boolean getIsFeatherRequired() {
        return isFeatherRequired;
    }

    public boolean getIsParchmentRequired() {
        return isParchmentRequired;
    }

    public int getMushroomRequired() {
        return mushroom_required;
    }

    public int getLeafRequired() {
        return leaf_required;
    }

    public int getButterflyRequired() {
        return butterfly_required;
    }

    public int getWolfRequired() {
        return wolf_required;
    }


    // TODO: override updatePoints to adapt it to this class

}






















