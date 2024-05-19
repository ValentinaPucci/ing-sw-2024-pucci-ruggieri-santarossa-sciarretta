package it.polimi.demo.model.interfaces;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.CardsCollection;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.board.CommonBoard;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.view.PlayerDetails;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface GameModelInterface {

    void addListener(GameListener listener);

    void removeListener(GameListener listener);

    GameStatus getStatus();

    CommonBoard getCommonBoard();

    List<Player> getAllPlayers();

    LinkedList<Player> getPlayersConnected();

    int getFinalPlayerIndex();

    PlayerIC getFirstPlayer();

    boolean isEnded();

    boolean isPaused();

    List<PlayerDetails> getPlayersDetails();

    String getErrorMessage();

    int getGameId();

    List<String> getAllNicknames();

    int getNumPlayersToPlay();

    boolean isStarted();

    boolean isFull();

    void reconnectPlayer(Player player);

    List<GameListener> getListeners();

    void drawCard(Player p, int i) throws GameEndedException;

    void placeCard(ResourceCard card, Player p, int x, int y);

    void placeCard(GoldCard card, Player p, int x, int y);

    List<Player> getWinners();

    Map<Player, Integer> getLeaderBoard();

    PersonalBoard getPersonalBoard(String myNickname);

    List<StarterCard> getStarterCardsToChoose(String myNickname);

    StarterCard getStarterCard(String myNickname);

    List<ObjectiveCard> getPersonalObjectiveCardsToChoose(String myNickname);

    ObjectiveCard getObjectiveCard(String myNickname);

    List<ResourceCard> getPlayerHand(String myNickname);


}
