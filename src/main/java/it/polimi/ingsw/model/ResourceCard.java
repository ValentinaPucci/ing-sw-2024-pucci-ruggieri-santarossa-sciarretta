package src.main.java.it.polimi.ingsw.model;

public class ResourceCard extends Card {
    private int score;
    private Color color;
    private Corner[][] corners;

    /**
     *
     * @param id
     * @param orientation
     */
    public ResourceCard(int id, Orientation orientation) {
        super(id, orientation);
        this.score = 0;
        this.corners = new Corner[4][4];
    }

    /**
     *
     * @param score
     * @param actual_corners
     * @param color
     */
    public void setResourceCard(int score, Corner[][] actual_corners, Color color) {
        this.score = score;
        this.color = color;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.corners[i][j] = actual_corners[i][j];
            }
        }

    }
}

