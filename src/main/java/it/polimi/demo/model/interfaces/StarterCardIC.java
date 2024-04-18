package it.polimi.demo.model.interfaces;

import it.polimi.demo.model.board.Corner;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.enumerations.Resource;

import java.util.Optional;

public interface StarterCardIC {

    /**
     * This method is used to get the specified StarterCard's corner
     * @param i
     * @param j
     * @return the corner indexed by the couple (i, j)
     */
    Corner getCornerAt(int i, int j);

    /**
     * This method is used to get the first front resource
     * @return the first front resource
     */

    Optional<Resource> getFront_resource1();

    /**
     * This method is used to get the second front resource
     * @return the second front resource
     */

    Optional<Resource> getFront_resource2();

    /**
     * This method is used to get the third front resource
     * @return the third front resource
     */
    Optional<Resource> getFront_resource3();

    /**
     *This method is used to get the StarterCard's orientation
     * @return orientation
     */
    Orientation getOrientation();

}
