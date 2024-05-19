package it.polimi.demo.view.UI.TUI;

import it.polimi.demo.listener.UIListener;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.enumerations.Resource;
import it.polimi.demo.view.UI.GameUI;
import it.polimi.demo.view.GameView;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static it.polimi.demo.listener.Listener.notifyListeners;
import static it.polimi.demo.view.UI.TUI.TextualUtils.*;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

public class TextualGameUI extends GameUI {

    /** Enumeration representing the state of the game from the perspective of the player */
    private enum State {
        MY_TURN,
        NOT_MY_TURN,
        ENDED
    }

    public TextualGameUI() {
        AnsiConsole.systemInstall();
    }

    /** Current state of the player associated to this GameUI. */
    private State state = State.NOT_MY_TURN;

    /** Lock used to synchronize methods inside Textual GameUI. */
    private final Object lock = new Object();

    /** The latest GameView received from the server.
     * Contains data about every aspect of the current Game (board state, player state...).
     * */
    private GameView lastGameView;

    /**
     * This method is used to get the current state of the player.
     * @return the current state
     */
    private State getState() {
        synchronized (lock) {
            return this.state;
        }
    }

    /**
     * This method is used to set the current state of the player.
     * @param state  the state to set the player to
     */
    private void setState(State state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }
    }

    /**
     * It is used to perform the turn of the player. In particular, this turn
     * starts with the selection of the card to play and then goes ahead
     * with the placement of the card on the board.
     */
    private void executeMyTurn() {
        if (getState() == State.MY_TURN) {
            GameStatus current_status = lastGameView.getStatus();
            switch (current_status) {
                case FIRST_ROUND -> {
                    System.out.println("It's your turn to play the starter card!");
                    // Here we show both sides of the starter card
                    showStarterCards();
                    Orientation o = chooseCardOrientation();
                    notifyListeners(lst, UIListener -> UIListener.placeStarterCard(o));
                }

                case RUNNING, SECOND_LAST_ROUND -> {
                    this.showPlayerHand(lastGameView.getPlayerHand());
                    notifyListeners(lst, UIListener -> UIListener.chooseCard(
                            askIndex("Select the index of the card you want to play")));
                    TuiPersonalBoardGraphics.showPersonalBoard(lastGameView.getPersonalBoard());
                    notifyListeners(lst, UIListener -> UIListener.placeCard(
                            askIndex("Select the x coordinate of the card you want to place"),
                            askIndex("Select the y coordinate of the card you want to place"),
                            chooseCardOrientation()));
                    notifyListeners(lst, UIListener -> UIListener.drawCard(
                            askIndex("""
                                    Select the index of the card you want to draw:\s
                                     1: Resource Deck
                                     2: First Resource Card on the table
                                     3: Second Resource Card on the table
                                     4: Gold Deck
                                     5: First Gold Card on the table
                                     6: Second Gold Card on the table""")));

                    //TODO: devo ricevere la gameView aggiornata prima di stampare personal e common board
                    TuiPersonalBoardGraphics.showPersonalBoard(lastGameView.getPersonalBoard());
                    TuiCommonBoardGraphics.showCommonBoard(lastGameView.getCommonBoard());
                }

                case LAST_ROUND -> {
                    this.showPlayerHand(lastGameView.getPlayerHand());
                    notifyListeners(lst, UIListener -> UIListener.chooseCard(
                            askIndex("Select the index of the card you want to play")));
                    TuiPersonalBoardGraphics.showPersonalBoard(lastGameView.getPersonalBoard());
                    notifyListeners(lst, UIListener -> UIListener.placeCard(
                            askIndex("Select the x coordinate of the card you want to place"),
                            askIndex("Select the y coordinate of the card you want to place"),
                            chooseCardOrientation()));
                    //TODO: devo ricevere la gameView aggiornata prima di stampare personal e common board
                    TuiPersonalBoardGraphics.showPersonalBoard(lastGameView.getPersonalBoard());
                    TuiCommonBoardGraphics.showCommonBoard(lastGameView.getCommonBoard());
                }
            }
            setState(State.NOT_MY_TURN);
        }
    }

    @Override
    public void update(GameView gameView) {

        // Here we update the game view
        this.lastGameView = gameView;

        if (gameView.isGamePaused()) {
            this.gamePaused();
            setState(State.NOT_MY_TURN);
            return;
        }
        if (gameView.getErrorMessage() != null){
            System.out.println("\n" + ansi().fg(RED).bold().a(gameView.getErrorMessage()).reset() + "\n");
        }

        // Here we update the personal board
        updatePersonalBoard(gameView);

    }

    @Override
    public void nextTurn() {
        if (lastGameView.getCurrentPlayerNickname().equals(lastGameView.getMyNickname())) {
            System.out.println("Your turn!");
            setState(State.MY_TURN);
            // Every time we call the update someone (hopefully) will execute his/her turn.
            executeMyTurn();
        }
    }

    @Override
    public void gameEnded(GameView gameView) {

    }

    /**
     * This method is called when the game is paused.
     */
    public void gamePaused() {
        System.out.println();
        updateBoard(lastGameView);
        System.out.println(ansi().bold().fg(Ansi.Color.YELLOW)
                .a("""
                        Game has been paused since you're the only player left in the game.
                        Waiting for someone else to reconnect...
                        """).reset());
    }

    private void showStarterCards() {
        List<StarterCard> starter_cards = lastGameView.getPersonalStarterCardsToChose();
        TuiCardGraphics.showStarterCard(starter_cards.get(0));
        TuiCardGraphics.showStarterCard(starter_cards.get(1));
    }

    private void showStarterCard() {
        StarterCard starter_card = lastGameView.getPersonalStarterCard();
        TuiCardGraphics.showStarterCard(starter_card);
    }

    public int askIndex(String message) {
        Scanner s = new Scanner(System.in);
        System.out.print(message + ", enter here the index required: ");
        return nextInt(s);
    }

    public Orientation chooseCardOrientation() {
        Scanner s = new Scanner(System.in);
        int choice;
        do {
            System.out.print("Choose the card orientation you want to use: \n 1: FRONT \n 2: BACK \n");
            choice = nextInt(s);
            if (choice < 1 || choice > 3){
                System.out.print("Invalid input. Type 1 or 2.");
            }
        }  while (choice < 1 || choice > 2);
        if (choice == 1)
            return Orientation.FRONT;
        else
            return Orientation.BACK;
    }

    private void updatePersonalBoard(GameView gameView) {
        System.out.print(ansi().eraseScreen(Ansi.Erase.BACKWARD).cursor(1, 1).reset());
        TuiPersonalBoardGraphics.showPersonalBoard(gameView.getPersonalBoard());
    }

    private void updateBoard(GameView gameView) {
        clearScreen(gameView);
    }

    private void clearScreen(GameView gameView) {
        System.out.print(ansi().eraseScreen(Ansi.Erase.BACKWARD).cursor(1, 1).reset());
        //this.showPlayers(gameView);
        this.showCommonBoard(gameView);
    }

    private void showCommonBoard(GameView gameView) {
        TuiCommonBoardGraphics.showCommonBoard(gameView.getCommonBoard());
    }

    private void showPlayerHand(List<ResourceCard> player_hand){
        for(int i = 0; i < 3; i++){
            if (player_hand.get(i) instanceof GoldCard)
                showGoldCard((GoldCard)player_hand.get(i));
            else
                showResourceCard(player_hand.get(i));
        }
    }

    private void showResourceCard(ResourceCard card){
        TuiCardGraphics.showResourceCard(card);
    }

    private void showGoldCard(GoldCard card){
        TuiCardGraphics.showGoldCard(card);
    }

}
