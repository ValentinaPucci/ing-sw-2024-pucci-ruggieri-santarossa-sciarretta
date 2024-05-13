package it.polimi.demo.view.UI.TUI;

import it.polimi.demo.listener.UIListener;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.view.UI.GameUI;
import it.polimi.demo.view.GameView;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static it.polimi.demo.listener.Listener.notifyListeners;
import static it.polimi.demo.view.UI.TUI.TextualUtils.*;
import static org.fusesource.jansi.Ansi.ansi;

public class TextualGameUI extends GameUI {

    /** Enumeration representing the state of the game from the perspective of the player */
    private enum State {
        MY_TURN,
        NOT_MY_TURN,
        ENDED
    }

    /** Current state of the player associated to this GameUI. */
    private State state = State.NOT_MY_TURN;

    /** Lock used to synchronize methods inside Textual GameUI. */
    private final Object lock = new Object();

    /** A thread dedicated to accepting inputs from the player. */
    private Thread inputThread;

    /** The latest GameView received from the server.
     * Contains data about every aspect of the current Game (board state, player state...).
     * */
    private GameView lastGameView;

    @Override
    public void run() {
        AnsiConsole.systemInstall();

        while (true) {
            while (getState() == State.NOT_MY_TURN) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException ignored) {}
                }
            }

            if (getState() == State.MY_TURN) {
                inputThread = new Thread(this::executeMyTurn);
                inputThread.start();
                try {
                    inputThread.join();
                } catch (InterruptedException ignored) {}
            } else if (getState() == State.ENDED){
                break;
            }
        }
    }

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

        while (getState() == State.MY_TURN) {
            notifyListeners(lst, UIListener -> UIListener.chooseCard(
                    askIndex("Select the index of the card you want to play")));
            notifyListeners(lst, UIListener -> UIListener.placeCard(
                    askIndex("Select the x coordinate of the card you want to place"),
                    askIndex("Select the y coordinate of the card you want to place"),
                    chooseCardOrientation()));
            notifyListeners(lst, UIListener -> UIListener.drawCard(
                    askIndex("""
                            Select the index of the card you want to draw;\s
                             1: Resource Deck
                             2: First Resource Card on the table
                             3: Second Resource Card on the table
                             4: Gold Deck
                             5: First Gold Card on the table
                             6: Second Gold Card on the table""")));
            setState(State.NOT_MY_TURN);

        }
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
            System.out.print("Choose the card orientation you want to use: \n 1: FRONT \n 2:BACK");
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

    @Override
    public void update(GameView gameView) {

    }

    @Override
    public void gameEnded(GameView gameView) {

    }
}
