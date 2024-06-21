package it.polimi.demo.model.board;

import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.enumerations.Color;
import it.polimi.demo.model.enumerations.Orientation;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    /**
     * This is the last visible corner (the one we need to use throughout the game)
     */
    private Corner corner; // level 0 or level 1 (the last one available)

    public int level = 0;
    public boolean is_full;
    public boolean cell_of_a_found_pattern;

    private List<IdColor> id_colors = new ArrayList<>();

    /**
     * Constructor for the class Cell.
     */
    public Cell() {
        this.is_full = false;
        this.cell_of_a_found_pattern = false;
    }

    /**
     * This method is used to set the corner of the cell.
     * @param new_corner the corner of the cell
     */
    public void setCellAsFull(Corner new_corner) {
        id_colors.add(new IdColor(new_corner.reference_card.getId(), new_corner.reference_card.color));
        this.corner = new_corner;
        this.level++;
        this.is_full = true;
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
     * getter for IdColor type list
     * @return the list of IdColor objects
     */
    public List<IdColor> getIdColors() {
        return id_colors;
    }

    /**
     * setter for found pattern cell
     * @param id the id of the card
     * @param color the color of the card
     */
    public void setIdColorAsFoundPattern(int id, Color color) {
        id_colors.remove(new IdColor(id, color));
        id_colors.add(new IdColor(id, Color.NONE));
    }

    /**
     * This method is used to check if a cell is empty.
     *
     * @return true if the cell is empty, false otherwise
     */
    public boolean equals(Cell cell) {
        // all possible combinations, but we need to check they belong to the same card!
        return this.is_full && cell.is_full && (
                this.id_colors.getFirst().color() == cell.id_colors.getFirst().color() ||
                        this.id_colors.getLast().color() == cell.id_colors.getLast().color() ||
                        this.id_colors.getFirst().color() == cell.id_colors.getLast().color() ||
                        this.id_colors.getLast().color() == cell.id_colors.getFirst().color()
                );
    }
}
