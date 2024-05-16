package it.polimi.demo.view.UI.TUI;

import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.view.UI.GameUI;
import it.polimi.demo.view.GameView;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.*;

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

//    @Override
//    public void run() {
//        AnsiConsole.systemInstall();
//        //System.out.println("RUN pre while");
//        System.out.println(getState());
//        while (true) {
//            while (getState() == State.NOT_MY_TURN) {
//                synchronized (lock) {
//                    try {
//                        System.out.println("possibile deadlock qui");
//                        lock.wait();
//                    } catch (InterruptedException ignored) {}
//                }
//            }
//            if (getState() == State.MY_TURN) {
//                executeMyTurn();
////                inputThread = new Thread(this::executeMyTurn);
////                inputThread.start();
////                try {
////                    //System.out.println("sono in run e ho fatto la join1");
////                    inputThread.join();
////                    //System.out.println("sono in run e ho fatto la join2");
////                } catch (InterruptedException ignored) {}
//            } else if (getState() == State.ENDED){
//                break;
//            }
//        }
//    }

    //    /**
//     * This method is used to get the current state of the player.
//     * @return the current state
//     */
//    private State getState() {
//        synchronized (lock) {
//            return this.state;
//        }
//    }
//
//    /**
//     * This method is used to set the current state of the player.
//     * @param state  the state to set the player to
//     */
//    private void setState(State state) {
//        synchronized (lock) {
//            this.state = state;
//            lock.notifyAll();
//        }
//    }

//    @Override
//    public void run() {
//        AnsiConsole.systemInstall();
//        while (true) {
//            try {
//                synchronized (lock) {
//                    while (getState() == State.NOT_MY_TURN) {
//                        lock.wait();
//                    }
//                }
//                State currentState = getState(); // Get the current state
//                if (currentState == State.MY_TURN) {
//                    System.out.println("It's my turn!");
//                    Future<?> future = executor.submit(this::executeMyTurn); // Execute my turn asynchronously
//                    // Wait for the execution to finish
//                    try {
//                        future.get(); // This will block until the task is completed
//                    } catch (InterruptedException | ExecutionException e) {
//                        // Handle exceptions
//                        e.printStackTrace();
//                    }
//                }
//                if (currentState == State.ENDED) {
//                    break;
//                }
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            };
//        }
//    }

    @Override
    public void run() {
        AnsiConsole.systemInstall();
        inputThread = new Thread(this::executeMyTurn); // Execute my turn asynchronously
        inputThread.start();
        // Wait for the execution to finish
        try {
            inputThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to get the current state of the player.
     * @return the current state
     */
    private State getState() {
        synchronized (lock) { // Synchronize access to shared state
            return state;
        }
    }

    /**
     * This method is used to set the current state of the player.
     * @param state  the state to set the player to
     */
    private void setState(State state) {
        synchronized (lock) { // Synchronize access to shared state
            this.state = state;
            lock.notifyAll(); // Notify waiting threads that the state has changed
        }
    }

    /**
     * It is used to perform the turn of the player. In particular, this turn
     * starts with the selection of the card to play and then goes ahead
     * with the placement of the card on the board.
     */
    private void executeMyTurn() {
        while (true) {
            if (lastGameView != null) {
                GameStatus current_status = lastGameView.getStatus();
                if (getState() == State.MY_TURN) {
                    switch (current_status) {
                        case FIRST_ROUND -> {
                            //this.showStarterCards();
                            notifyListeners(lst, UIListener -> UIListener.placeStarterCard(chooseCardOrientation()));
                            setState(State.NOT_MY_TURN);
                        }
                        
                        case RUNNING, SECOND_LAST_ROUND -> {
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

                        case LAST_ROUND -> {
                            notifyListeners(lst, UIListener -> UIListener.chooseCard(
                                    askIndex("Select the index of the card you want to play")));
                            notifyListeners(lst, UIListener -> UIListener.placeCard(
                                    askIndex("Select the x coordinate of the card you want to place"),
                                    askIndex("Select the y coordinate of the card you want to place"),
                                    chooseCardOrientation()));
                            setState(State.NOT_MY_TURN);
                        }
                    }
                }
            }
            else
                System.out.println("GameView is null");
        }
    }

    private void showStarterCards() {
        System.out.println("These are the details of your starter card:\n " +
                lastGameView.getPersonalStarterCardsToChose().getFirst().toString());
        System.out.println("These are the details of your starter card:\n " +
                lastGameView.getPersonalStarterCardsToChose().getLast().toString());
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
            System.out.print("Choose the card orientation you want to use: \n 1:FRONT \n 2:BACK \n");
            choice = nextInt(s);
            if (choice < 1 || choice > 3) {
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
        this.lastGameView = gameView;
        // System.out.println("ciao sono entrato in update"); ci entra!
        if (gameView.isGamePaused()) {
            this.gamePaused();
            setState(State.NOT_MY_TURN);
            return;
        }
        if (gameView.getErrorMessage() != null){
            System.out.println("\n" + ansi().fg(RED).bold().a(gameView.getErrorMessage()).reset() + "\n");
        }
        if (gameView.getCurrentPlayerNickname().equals(gameView.getMyNickname()) && getState() == State.MY_TURN) {
            return;
        }

        this.updateBoard(gameView);

        if (gameView.getCurrentPlayerNickname().equals(gameView.getMyNickname())) {
            System.out.println("Your turn!");
            setState(State.MY_TURN);
        } else {
            System.out.println("Player " + gameView.getCurrentPlayerNickname() + "'s turn.");
            setState(State.NOT_MY_TURN);
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
        if (inputThread != null) {
            inputThread.interrupt();
        }
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
        System.out.println(ansi().fg(Ansi.Color.BLUE).a("\nCurrent COMMON BOARD:").reset());
        //TODO: to implement
    }
}
