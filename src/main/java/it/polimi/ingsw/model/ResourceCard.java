package src.main.java.it.polimi.ingsw.model;

public class ResourceCard extends Card {
    private int score;
    public Color color;
    private Corner[][] corners;

    /**
     *
     * @param id
     * @param orientation
     * @param color
     */
    public ResourceCard(int id, Orientation orientation, Color color) {
        super(id, orientation);
        this.score = 0;

        this.corners = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                corners[i][j] = new Corner(new BoardCellCoordinate(i, j));
            }
        }

        this.color = color;
    }

    /**
     *
     * @param score
     * @param actual_corners
     */
    public void setResourceCard(int score, Corner[][] actual_corners) {

        this.score = score;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                this.corners[i][j] = actual_corners[i][j];
            }
        }

    }

    /**
     *
     * @return NE corner
     */
    public Corner getCornerAtNE () {
        return corners[0][1];
    }

    /**
     *
     * @return SE corner
     */
    public Corner getCornerAtSE () {
        return corners[1][1];
    }

    /**
     *
     * @return SO corner
     */
    public Corner getCornerAtSO () {
        return corners[1][0];
    }

    /**
     *
     * @return NO corner
     */
    public Corner getCornerAtNO () {
        return corners[0][0];
    }

    /**
     * @requires
     *      0 <= i < 2 && 0 <= j < 2;
     * @param i
     * @param j
     * @return the corner indexed by the couple (i, j)
     */
    public Corner getCornerAt(int i, int j) {
        if (i == 0 && j == 0)
            return this.getCornerAtNO();
        else if (i == 0 && j == 1)
            return this.getCornerAtNE();
        else if (i == 1 && j == 0)
            return this.getCornerAtSO();
        else
            return this.getCornerAtSE();
    }
}

