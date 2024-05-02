package it.polimi.demo.view;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * This class is used to send the details of a specific game to the client.
 */
public record GameDetails(int gameID,
                              List<PlayerDetails> playersInfo,
                              int numberOfPlayers,
                              boolean isFull,
                              boolean isStarted) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
