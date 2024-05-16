package it.polimi.demo.view;

import it.polimi.demo.model.Player;
import it.polimi.demo.model.board.CommonBoard;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.interfaces.GameModelInterface;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class GameView implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String initial_player_nickname;
    private final CommonBoard common_board;
    private final int game_id;
    private final int num_required_players_to_start;
    private final String current_player_nickname;
    //private final List<Player> winners;
    private final Map<Player, Integer> leaderboard;
    private final PersonalBoard personal_board;
    private List<ObjectiveCard> personal_objective_cards_to_chose;
    //private final ObjectiveCard personal_objective_card;
    private List<StarterCard> personal_starter_cards_to_chose;
    //private final StarterCard personal_starter_card;
    private final List<PlayerDetails> playersData;
    private final boolean gameEnded;
    private final boolean gamePaused;
    private final String errorMessage;

    // IMPORTANT attributes
    private final String my_nickname;
    private final GameStatus actual_status;

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
//        this.personal_objective_card = model.getAllPlayers()
//                .stream()
//                .filter(player -> player.getNickname().equals(nickname))
//                .findFirst()
//                .map(PlayerIC::getChosenObjectiveCard) // Map the Optional<Player> to Optional<ChosenObjectiveCard>
//                .orElse(null); // Provide a default value if the Optional is empty
//        this.personal_objective_cards_to_chose = new ArrayList<>(Objects.requireNonNull(model.getAllPlayers()
//                .stream()
//                .filter(player -> player.getNickname().equals(nickname))
//                .findFirst()
//                .map(PlayerIC::getSecretObjectiveCards)// Map the Optional<Player> to Optional<ChosenObjectiveCard>
//                .orElse(null)));
//        this.personal_starter_card = model.getAllPlayers()
//                .stream()
//                .filter(player -> player.getNickname().equals(nickname))
//                .findFirst()
//                .map(PlayerIC::getStarterCard) // Map the Optional<Player> to Optional<ChosenObjectiveCard>
//                .orElse(null); // Provide a default value if the Optional is empty
//        this.personal_starter_cards_to_chose = new ArrayList<>(Objects.requireNonNull(model.getAllPlayers()
//                .stream()
//                .filter(player -> player.getNickname().equals(nickname))
//                .findFirst()
//                .map(PlayerIC::getStarterCardToChose)// Map the Optional<Player> to Optional<ChosenObjectiveCard>
//                .orElse(null)));
        this.gameEnded = model.isEnded();
        this.gamePaused = model.isPaused();
        this.playersData = model.getPlayersDetails();
        this.errorMessage = model.getErrorMessage();
    }

//    public StarterCard getPersonalStarterCard() {
//        return personal_starter_card;
//    }

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
}