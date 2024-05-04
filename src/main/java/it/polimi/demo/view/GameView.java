package it.polimi.demo.view;

import it.polimi.demo.model.GameModel;
import it.polimi.demo.model.board.CommonBoard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class GameView implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The index of the player who is receiving this GameView.
     */
    private final int myIndex;
    /**
     * The matrix of the actual living room board.
     */
    private final CommonBoard common_board;

    /**
     * The path of the player's personal goal card image.
     */
    private final ObjectiveCard personal_objective_card;
    /**
     * The index of the current player who's playing.
     */
    //private final int currentPlayerIndex;
    /**
     * The index of the player who filled the bookshelf first.
     */
    private final int finalPlayerIndex;
    /**
     * The current player's username.
     */
    //private final String currentPlayerNickname;
    /**
     * The username of the player who played first.
     */
    private final String firstPlayerNickname;

    /**
     * True if the game is ended, false otherwise.
     */
    private final boolean gameEnded;
    /**
     * True if the game is paused (there is only one player connected), false otherwise.
     */
    private final boolean gamePaused;

    /**
     * Data about players in the game. Contains the scores of the players in the game.
     * The map is sorted in descending order, so the first entry is the winner.
     * The key is the score, the value is the username. Always contains scoring tokens in possession of each player.
     */
    private final List<PlayerDetails> playersData;
    /**
     * This string contains the latest error message that occurred in the game.
     */
    private final String errorMessage;

    public GameView(GameModel game, int receivingPlayerIndex){
        this.myIndex = receivingPlayerIndex;

        this.common_board = game.getCommonBoard();
        this.personal_objective_card = game.getAllPlayers().get(this.myIndex).getChosenObjectiveCard();
        //this.currentPlayerIndex = game.getCurrentPlayerIndex();
        this.finalPlayerIndex = game.getFinalPlayerIndex();
        //this.currentPlayerNickname= game.getCurrentPlayer().getNickname();
        this.firstPlayerNickname = game.getFirstPlayer().getNickname();

        this.gameEnded = game.isEnded();
        this.gamePaused = game.isPaused();

        this.playersData = game.getPlayersDetails();
        this.errorMessage = game.getErrorMessage();
    }


}
