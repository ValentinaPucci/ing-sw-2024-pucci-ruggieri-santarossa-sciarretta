package it.polimi.demo.view;

import it.polimi.demo.controller.GameController;
import it.polimi.demo.model.DefaultValues;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.board.CommonBoard;
import it.polimi.demo.model.board.CommonBoardNode;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class TUI extends UI {

    /**
     * The username inserted by the user.
     */
    private String nickname;

    /**
     * The number of players inserted by the user when creating a new game.
     */
    private int numOfPlayers;

    /**
     * The game ID inserted by the user when joining an existing game.
     */
    private int gameID = -1;
    /**
     * If true, the user is waiting for other players to join the game.
     */
    private boolean waitingForPlayers = false;
    /**
     * The list of players in the game the user is waiting for.
     */
    private List<String> playersNameList;

    @Override
    public void run() {

    }

    /**
     * Enumeration representing the state of the game from the perspective of the player
     */
    private enum State {
        MY_TURN,
        NOT_MY_TURN,
        ENDED
    }

    /**
     * Current state of the player associated to this GameUI.
     */
    private State state = State.NOT_MY_TURN;

    /**
     * Lock used to synchronize methods inside Textual GameUI.
     */
    private final Object lock = new Object();

    /**
     * The latest GameView received from the server.
     * Contains data about every aspect of the current Game (board state, player state...).
     */
    private GameModelImmutable last_model;

    private char selected_where_to_place_letter;

    //UI IMPLEMENTS RUNNABLE

    /**
     * Asks the user to insert the number of players and the number of common goal cards, then sends a CREATE event.
     */
    public void createGame() {
        Scanner s = new Scanner(System.in);
        do {
            System.out.print("How many players? (" + DefaultValues.MinNumOfPlayer + " to " + DefaultValues.MaxNumOfPlayer + ") ");
            numOfPlayers = TUIUtils.nextInt(s);
            if (numOfPlayers < DefaultValues.MinNumOfPlayer || numOfPlayers > DefaultValues.MaxNumOfPlayer)
                System.out.println("Number of players should be between " + DefaultValues.MinNumOfPlayer
                        + " and " + DefaultValues.MaxNumOfPlayer + ".");
        } while (numOfPlayers < DefaultValues.MinNumOfPlayer || numOfPlayers > DefaultValues.MaxNumOfPlayer);

        //notifyListeners(lst, startUIListener -> startUIListener.createGame(numberOfPlayers, this.nickname));

        waitingForPlayers = true;
    }


    /**
     * Asks the user to insert the ID of the game to join, then sends a JOIN event.
     */
    public void joinGame() {
        Scanner s = new Scanner(System.in);
        do {
            System.out.print("Which game do you want to join? ");
            gameID = TUIUtils.nextInt(s);
            if (gameID <= 0)
                System.out.println("GameID is a positive number!");
        } while (gameID <= 0);

//        try {
//            notifyListeners(lst, startUIListener -> startUIListener.joinGame(gameID, this.nickname));
//        } catch (IllegalArgumentException | IllegalStateException e) {
//            showError(e.getMessage());
//        }
        waitingForPlayers = true;
    }


    @Override
    public void update(GameModelImmutable model) {

    }

    @Override
    public void gameEnded(GameModelImmutable model) {

    }

    /**
     * Shows the list of games on the server only if the user has inserted a username.
     *
     * @param o array of strings representing the list of games on the server
     */
    @Override
    public void showGamesList(List<GameDetails> o) {
        if (this.nickname != null && !waitingForPlayers) {
            System.out.print(ansi().eraseScreen(Ansi.Erase.BACKWARD).cursor(1, 1).reset());

            if (!o.isEmpty()) {
                System.out.println("List of games on the server:");
                System.out.println("ID:\tPlayers:");
                for (GameDetails gameDetails : o) {
                    StringBuilder string = new StringBuilder();

                    string.append(" ").append(gameDetails.gameID()).append("\t");
                    for (PlayerDetails playerInfo : gameDetails.playersInfo()) {
                        if (playerInfo.isConnected())
                            string.append(playerInfo.username()).append("\t");
                        else
                            string.append(ansi().fgBrightBlack().a(playerInfo.username()).reset()).append("\t");
                    }
                    if (gameDetails.isStarted()) {
                        string.append("(STARTED)");
                    } else if (gameDetails.isFull()) {
                        string.append("(FULL)");
                    }
                    System.out.println(string);
                }
            } else {
                System.out.println("There are no games on the server.");
            }
            System.out.println("Select an option:");
            System.out.println(" 1. Create a new game");
            System.out.println(" 2. Join an existing game");
        }
    }

    /**
     * Shows an error message and the menu.
     *
     * @param err error message to show
     */
    @Override
    public void showError(String err) {
        System.out.println(err);
        //notifyListeners(lst, StartUIListener::exit);
    }

    /**
     * Shows the list of connected players.
     *
     * @param o a list of player usernames
     */
    @Override
    public void showPlayersList(List<String> o) {
        if (this.playersNameList == null) {
            System.out.print(ansi().eraseScreen(Ansi.Erase.BACKWARD).cursor(1, 1).reset());

            if (gameID == -1) {
                System.out.println("Game created successfully.");
            } else {
                System.out.println("Game #" + gameID);
            }
            System.out.println("List of connected players:");
            for (String s : o) {
                System.out.println(" " + s);
            }
        } else {
            o.stream().filter(s -> !this.playersNameList.contains(s)).forEach(x -> System.out.println(" " + x));
        }

        this.playersNameList = o;
    }

    /**
     * This method erases the screen.
     */
    @Override
    public void close() {
        System.out.print(ansi().eraseScreen(Ansi.Erase.BACKWARD).cursor(1, 1).reset());
    }

    /**
     * This method is used to get the current state of the player.
     *
     * @return the current state
     */
    private State getState() {
        synchronized (lock) {
            return this.state;
        }
    }

    /**
     * This method is used to set the current state of the player.
     *
     * @param state the state to set the player to
     */
    private void setState(State state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }
    }

    /**
     * Runs the Textual GameUI.
     * This method enables the usage of AnsiConsole for custom textual output
     * and enters a loop to wait for and execute player's turns.
     */
//    @Override
//    public void run() {
//        AnsiConsole.systemInstall();
//
//        while (true) {
//            while (getState() == State.NOT_MY_TURN) {
//                synchronized (lock) {
//                    try {
//                        lock.wait();
//                    } catch (InterruptedException ignored) {
//                    }
//                }
//            }
//
//            if (getState() == State.MY_TURN) {
//                inputThread = new Thread(() -> {
//                    try {
//                        executeTurn();
//                    } catch (InterruptedException ignored) {
//                    }
//                });
//                inputThread.start();
//                try {
//                    inputThread.join();
//                } catch (InterruptedException ignored) {
//                }
//            } else if (getState() == State.ENDED) {
//                break;
//            }
//        }
//    }

    /**
     *
     */

    //TODO: VA COLLEGATO AL PLAYER
    private int selectCardFromHand(ArrayList player_hand) throws InterruptedException {
        Scanner input = new Scanner(System.in);
        int selectedCard = 0;

        while (selectedCard < 1 || selectedCard > 3) {
            System.out.println("Select which card to play from your hand (enter a number between 1 and 3):");
            showHand(player_hand);

            // Verifica se l'input è un intero
            if (input.hasNextInt()) {
                selectedCard = input.nextInt();
                if (selectedCard < 1 || selectedCard > 3) {
                    System.out.println("Please enter a number between 1 and 3.");
                }
            } else {
                // Se l'input non è un intero, consuma l'input errato per evitare un loop infinito
                input.next();
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
            }

        }
        return selectedCard;
    }

    //TODO: DEVO MOSTRARE LA PERSONAL BOARD CON LETTERE NEI POSTI IN CUI POSSO INSERIRE UNA CARTA
    public char selectWhereToPlaceCard(ArrayList available_positions) {

        Scanner input = new Scanner(System.in);
        selected_where_to_place_letter = '\0'; // Valore iniziale non valido

        while (!available_positions.contains(selected_where_to_place_letter)) {
            System.out.println("Select where to place the card (enter the letter of the angle where you want to insert your selected card):");
            // Mostra le posizioni disponibili
            System.out.println("Available positions:");
            for (Object ch : available_positions) {
                System.out.println(ch);
            }

            // Verifica se l'input è una lettera maiuscola
            if (input.hasNext()) {
                selected_where_to_place_letter = input.next().charAt(0);
            } else {
                // Se l'input non è una lettera maiuscola, consuma l'input errato per evitare un loop infinito
                input.next();
                System.out.println("Invalid input. Please enter a valid letter in CAPS.");
            }
        }
        System.out.println("Card inserted in position: " + selected_where_to_place_letter);
        return selected_where_to_place_letter;
    }


    public Orientation selectCardOrientation(ResourceCard card) {
        Scanner input = new Scanner(System.in);
        String selected_orientation;

        while (true) {
            System.out.println("Select whether to use Front or Back of the card selected: [F / B]");
            selected_orientation = input.next().toUpperCase();

            if (selected_orientation.equals("F") || selected_orientation.equals("B")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter F or B.");
            }
        }
        return selected_orientation.equals("F") ? Orientation.FRONT : Orientation.BACK;
    }

    public Orientation selectCardOrientation(StarterCard card) {
        Scanner input = new Scanner(System.in);
        String selected_orientation;

        while (true) {
            System.out.println("Select whether to use Front or Back of your starterCard: [F / B]");
            selected_orientation = input.next().toUpperCase();

            if (selected_orientation.equals("F") || selected_orientation.equals("B")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter F or B.");
            }
        }
        return selected_orientation.equals("F") ? Orientation.FRONT : Orientation.BACK;
    }


    public void showPoints(ArrayList<Player> players){
        System.out.println("Current scores\n");
        for(int i = 0; i< players.size(); i++){
            System.out.println(players.get(i).getNickname() + ": " + players.get(i).getCurrentPoints());
        }
    }



    public int selectWhereToDrawCard() {
        int selected_where_to_draw_index = 0;

        Scanner input = new Scanner(System.in);

        while (selected_where_to_draw_index < 1 || selected_where_to_draw_index > 6) {
            System.out.println("Select from where you want to draw a card: ");
            System.out.println(
                    "     *              1: Resource Deck\n" +
                    "     *              2: First Resource Card on the table\n" +
                    "     *              3: Second Resource Card on the table\n" +
                    "     *              4: Gold Deck\n" +
                    "     *              5: First Gold Card on the table\n" +
                    "     *              6: Second Gold Card on the table");

            // Verifica se l'input è un intero
            if (input.hasNextInt()) {
                selected_where_to_draw_index = input.nextInt();
                if (selected_where_to_draw_index < 1 || selected_where_to_draw_index > 3) {
                    System.out.println("Please enter a number between 1 and 6.");
                }
            } else {
                // Se l'input non è un intero, consuma l'input errato per evitare un loop infinito
                input.next();
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
            }
        }
        return selected_where_to_draw_index;
    }




    private void showHand(ArrayList<ResourceCard> player_hand) {
        int i = 1;
        for (ResourceCard card : player_hand) {
            //TODO: definire card.getFront() e card.getBack()
            //System.out.println("Card number: " + i + ", Front: " + card.getFront() + ", Back: " + card.getBack());
            i++;
        }
    }


    private void showCommonBoard(CommonBoard common_board) {
        System.out.println("CommonBoard:");
        for (CommonBoardNode node : common_board.getBoardNodes()) {
            boolean[] players = node.getPlayers();
            for (int i = 0; i < players.length; i++) {
                if (players[i]) {
                    System.out.print("Player" + i + " at position" + node.getNodeNumber() + "\t");
                }
                System.out.println();
            }
        }

        System.out.println("Table Cards:");
        for (int row = 0; row < common_board.getTableCards()[row].length; row++) {
            for (int col = 0; col < common_board.getTableCards()[col].length; col++) {
                System.out.print(common_board.getTableCards()[row][col] + "\t");
            }
            System.out.println();
        }
    }
}
