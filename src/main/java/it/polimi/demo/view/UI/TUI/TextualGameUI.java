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
     * This method performs the player's turn. It asks the user to select the tiles to place and the column to place them in.
     */
    private void performTurn() throws InterruptedException {
            if (getState() == State.MY_TURN) {
                notifyListeners(lst, UIListener::performTurn);
            }
            setState(State.NOT_MY_TURN);
    }

    public Orientation chooseCardOrientation() {
        Scanner s = new Scanner(System.in);
        int choice = -1;

        while (choice < 1 || choice > 2){
            System.out.print("Choose the card orientation you want to use: \n 1: FRONT \n 2:BACK");
            choice = TextualUtils.nextInt(s);
            if (choice < 1 || choice > 3){
                System.out.print("Invalid input. Type 1 or 2.");
            }
        }
        if (choice == 1)
            return Orientation.FRONT;
        else
            return Orientation.BACK;
    }

    public void placeCard(ResourceCard chosenCard){


    }

    public void drawCard(int x){

    }


    @Override
    public void update(GameView gameView) {

    }

    @Override
    public void gameEnded(GameView gameView) {

    }

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
                inputThread = new Thread(() -> {
                    try {
                        performTurn();
                    } catch (InterruptedException ignored) {}
                });
                inputThread.start();
                try {
                    inputThread.join();
                } catch (InterruptedException ignored) {}
            } else if (getState() == State.ENDED){
                break;
            }
        }
    }
}
