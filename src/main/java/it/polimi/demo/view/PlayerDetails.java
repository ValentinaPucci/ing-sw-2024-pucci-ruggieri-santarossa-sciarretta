package it.polimi.demo.view;

import it.polimi.demo.model.cards.gameCards.ResourceCard;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * This class is used to send the player's info to the client.
 */
public record PlayerDetails(String username, int points, ResourceCard last_chosen_card,
                         boolean isConnected, boolean isLast) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return username + ": " + points;
    }

}
