package it.polimi.demo.view;

import it.polimi.demo.model.Player;
import it.polimi.demo.model.board.CommonBoard;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.interfaces.GameModelInterface;
import it.polimi.demo.model.interfaces.PlayerIC;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class GameView implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String initial_player_nickname;
    private final CommonBoard common_board;
    private final int game_id;
    private final int num_required_players_to_start;
    private final GameStatus actual_status;
    private final String current_player_nickname;
    //private final List<Player> winners;
    private final Map<Player, Integer> leaderboard;
    private final PersonalBoard personal_board;
    private final ObjectiveCard personal_objective_card;
    private final List<ResourceCard> player_hand;
    private final StarterCard personal_starter_card;
    private final String my_nickname;
    private final List<ObjectiveCard> personal_objective_cards_to_chose;
    private final List<StarterCard> personal_starter_cards_to_chose;
    private final List<PlayerDetails> playersData;
    private final boolean gameEnded;
    private final boolean gamePaused;
    private final String errorMessage;

    public GameView(GameModelInterface model, String nickname) {
        this.initial_player_nickname = model.getAllPlayers().getFirst().getNickname();
        this.common_board = model.getCommonBoard();
        this.game_id =  model.getGameId();
        this.num_required_players_to_start = model.getNumPlayersToPlay();
        this.actual_status = model.getStatus();
        this.current_player_nickname = model.getPlayersConnected().peek().getNickname();
        // this.winners = model.getWinners();
        this.leaderboard = model.getLeaderBoard();
        this.my_nickname = nickname;
        this.personal_board = model.getPersonalBoard(this.my_nickname);
        this.personal_starter_cards_to_chose = model.getStarterCardsToChoose(this.my_nickname);
        this.personal_starter_card = model.getStarterCard(this.my_nickname);
        this.personal_objective_cards_to_chose =  model.getPersonalObjectiveCardsToChoose(this.my_nickname);
        this.personal_objective_card = model.getObjectiveCard(this.my_nickname);
        this.player_hand = model.getPlayerHand(this.my_nickname);
        this.gameEnded = model.isEnded();
        this.gamePaused = model.isPaused();
        this.playersData = model.getPlayersDetails();
        this.errorMessage = model.getErrorMessage();
    }

    public StarterCard getPersonalStarterCard(){
        return personal_starter_card;
    }

    public GameStatus getStatus(){
        return actual_status;
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getCurrentPlayerNickname() {
        return current_player_nickname;
    }

    public String getFirstPlayerNickname() {
        return initial_player_nickname;
    }

    public String getMyNickname(){
        return my_nickname;
    }

    public List<PlayerDetails> getPlayerDetails(){
        return playersData;
    }

    public List<ObjectiveCard> getPersonalObjectiveCardsToChose() {
        return personal_objective_cards_to_chose;
    }

    public List<StarterCard> getPersonalStarterCardsToChose() {
        return personal_starter_cards_to_chose;
    }

    public PersonalBoard getPersonalBoard() {
        return personal_board;
    }

    public CommonBoard getCommonBoard() {
        return common_board;
    }

    public List<ResourceCard> getPlayerHand() {
        return player_hand;
    }
}