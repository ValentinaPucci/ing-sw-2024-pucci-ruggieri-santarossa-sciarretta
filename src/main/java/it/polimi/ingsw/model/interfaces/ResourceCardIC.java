package it.polimi.ingsw.model.interfaces;

import it.polimi.ingsw.model.board.Corner;
import it.polimi.ingsw.model.enumerations.Color;

public interface ResourceCardIC {

    /**
     * This method is used to get the specified ResourceCard's corner
     * @param i
     * @param j
     * @return the corner indexed by the couple (i, j)
     */
    Corner getCornerAt(int i, int j);

     /**
      *This method is used to get the ResourceCard's color
      * @return color
      */
    Color getColor();

    /**
     *This method is used to get the ResourceCard's points
     * @return points
     */
    int getPoints();



}
