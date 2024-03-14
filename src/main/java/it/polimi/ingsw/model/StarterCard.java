package src.main.java.it.polimi.ingsw.model;

public class StarterCard extends Card {
    private Resource front_resource1;
    private Resource front_resource2;
    private Corner[][] back_corners;

    /**
     *
     * @param id
     * @param orientation
     */
    public StarterCard(int id, Orientation orientation) {
        super(id, orientation);
        this.front_resource1 = null;
        this.front_resource2 = null;
        this.back_cornerscorners = new Corner[4][4];
    }

    /**
     *
     * @param resource1
     * @param resource2
     */
    public void SetStarterCardFront(Resource resource1, Resource resource2) {
        this.front_resource1 = resource1;
        this.front_resource2 = resource2;
    }

    /**
     *
     * @param score
     * @param actual_corners
     * @param resource
     * @param item
     */
    public void setStarterCardBack(Corner[][] actual_corners) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.back_cornerscorners[i][j] = actual_corners[i][j];
            }
        }

    }

}
