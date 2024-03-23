package it.polimi.ingsw.model;

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

}


