package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Orientation;
import it.polimi.ingsw.model.enumerations.Resource;

public class StarterCard extends Card {
    public static final int STARTER_CARD_COORDINATE = 50;
    private Resource front_resource1;
    private Resource front_resource2;
    private Resource front_resource3;
    private Corner[][] back_corners;
    private Orientation orientation;

    /**
     *
     * @param id
     * @param orientation
     */
    public StarterCard(int id, Orientation orientation) {
        super(id, orientation);
        this.front_resource1 = null;
        this.front_resource2 = null;
        this.front_resource3 = null;
        this.back_corners = new Corner[4][4];
    }

    /**
     *
     * @param resource1
     * @param resource2
     */
    public void SetStarterCardFront(Resource resource1, Resource resource2, Resource resource3, Corner[][] actual_corners){
        this.front_resource1 = resource1;
        this.front_resource2 = resource2;
        this.front_resource3 = resource3;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.back_corners[i][j] = actual_corners[i][j];
            }
        }
    }

    /**
     *
     * @param score
     * @param actual_corners
     * @param resource
     * @param item
     */
    public void setStarterCardBack(Corner[][] actual_corners) {
        this.front_resource1 = null;
        this.front_resource2 = null;
        this.front_resource3 = null;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.back_corners[i][j] = actual_corners[i][j];
            }
        }
    }


    public Corner getCornerAtNE () {
        return back_corners[0][1];
    }

    /**
     *
     * @return SE corner
     */
    public Corner getCornerAtSE () {
        return back_corners[1][1];
    }

    /**
     *
     * @return SO corner
     */
    public Corner getCornerAtSW () {
        return back_corners[1][0];
    }

    /**
     *
     * @return NO corner
     */
    public Corner getCornerAtNW () {
        return back_corners[0][0];
    }

    public Corner getCornerAt(int i, int j) {
        if (i == 0 && j == 0)
            return this.getCornerAtNW();
        else if (i == 0 && j == 1)
            return this.getCornerAtNE();
        else if (i == 1 && j == 0)
            return this.getCornerAtSW();
        else
            return this.getCornerAtSE();
    }

}


