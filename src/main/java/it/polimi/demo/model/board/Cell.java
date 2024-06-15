package it.polimi.demo.model.board;

import java.io.Serial;
import java.io.Serializable;

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
public class Cell implements Serializable {

    @Serial
    private static final long serialVersionUID = 7281719514568472877L;
    private Corner corner;
    public int level;
    public boolean is_full;
    public boolean cell_of_a_found_pattern;


    /**
     * Constructor for the class Cell.
     */
    public Cell() {
        this.level = 0;
        this.is_full = false;
        this.cell_of_a_found_pattern = false;
    }

    /**
     * This method is used to set the corner of the cell.
     * @param new_corner the corner of the cell
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
     * This method is used to check if a cell is empty.
     *
     * @return true if the cell is empty, false otherwise
     */
    public boolean equals(Cell cell) {
        return this.is_full && cell.is_full && this.corner.reference_card.color == cell.corner.reference_card.color;
    }
}
