package it.polimi.demo.model.board;

/**
 * This class is thought to be complementary to the class Corner.
 * Indeed, Corner class has to interact in some way with Cell class.
 * Moreover, the importance of this class is mainly related to the fact
 * that we need to 'track' what places are in fact occupied (i.e. darken) by
 * the game card in a certain PersonalBoard configuration.
 * In particular, it could be useful to track the relationship between
 * a cell and the corner it represents using a mapping from the cell to the
 * corner.
 */
public class Cell {

    private Corner corner;
    public int level;
    public boolean is_full;
    public boolean cell_of_a_found_pattern;


    public Cell() {
        this.level = 0;
        this.is_full = false;
        this.cell_of_a_found_pattern = false;
    }

    /**
     *
     * @param corner
     */
    public Cell(Corner corner) {
        this.corner = corner;
        this.level = 0;
        this.is_full = false;
        this.cell_of_a_found_pattern = false;
    }

    /**
     *
     * @param new_corner
     */
    public void setCellAsFull(Corner new_corner) {
        this.corner = new_corner;
        this.level++;
        this.is_full = true;
    }

    public void setCellAsPatternFound() {
        this.cell_of_a_found_pattern = true;
    }

    /**
     * Remark: this method is crucial since it implement
     * the mapping between Cell and Corner classes
     *
     * @return the last corner put on this cell
     */
    public Corner getCornerFromCell() {
        return this.corner;
    }

    /**
     *
     * @param cell
     * @return true iff two cells are both full and has the same color
     * @Override
     */
    public boolean equals(Cell cell) {
        if (this.is_full && cell.is_full &&
            this.corner.reference_card.color == cell.corner.reference_card.color)
            return true;
        else
            return false;
    }

    public int getLevel() {
        return level;
    }
}
