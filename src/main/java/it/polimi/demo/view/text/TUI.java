package it.polimi.demo.view.text;

import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.enumerations.Coordinate;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import it.polimi.demo.Constants;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.ModelView;
import it.polimi.demo.view.dynamic.UI;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static it.polimi.demo.view.text.StaticPrinterTUI.print;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;
import static it.polimi.demo.view.text.StaticPrinterTUI.*;

/**
 * This class is father to both the TUI  implementation<br>
 * it manages all the event that can occur<br>
 */
public class TUI extends UI {

    private String nickname;

    /**
     * constructor
     */
    public TUI() {
        init();
    }

    /**
     * init
     */
    @Override
    public void init() {
        AnsiConsole.systemInstall();
        importantEvents = new ArrayList<>();
    }

    /**
     * @param input the string of the important event to offer
     */
    @Override
    public void addImportantEvent(String input) {
        if (importantEvents.size() + 1 >= Constants.maxnum_of_last_event_tobe_showed) {
            importantEvents.remove(0);
        }
        importantEvents.add(input);
        show_important_events();
    }

    // ********************* aux ********************* //

    /**
     * Clears the console
     */
    public void clearScreen() {
        try {
            Runtime.getRuntime().exec("clear");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Shows the game title
     */
    public void show_titleCodexNaturalis() {
        clearScreen();
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println(ansi().fg(YELLOW).a("""
                 ▄████████  ▄██████▄  ████████▄     ▄████████ ▀████    ▐████▀
                ███    ███ ███    ███ ███   ▀███   ███    ███   ███▌   ████▀\s
                ███    █▀  ███    ███ ███    ███   ███    █▀     ███  ▐███  \s
                ███        ███    ███ ███    ███  ▄███▄▄▄        ▀███▄███▀  \s
                ███        ███    ███ ███    ███ ▀▀███▀▀▀        ████▀██▄   \s
                ███    █▄  ███    ███ ███    ███   ███    █▄    ▐███  ▀███  \s
                ███    ███ ███    ███ ███   ▄███   ███    ███  ▄███     ███▄\s
                ████████▀   ▀██████▀  ████████▀    ██████████ ████       ███▄
                
                ░█▀█░█▀█░▀█▀░█░█░█▀▄░█▀█░█░░░▀█▀░█▀▀
                ░█░█░█▀█░░█░░█░█░█▀▄░█▀█░█░░░░█░░▀▀█
                ░▀░▀░▀░▀░░▀░░▀▀▀░▀░▀░▀░▀░▀▀▀░▀▀▀░▀▀▀
                
                """).reset());
    }

    /**
     * Shows the last panel
     *
     * @param model where the game is ended
     */
    // todo: check for correctness
    @Override
    public void show_gameEnded(ModelView model) {
        clearScreen();
        show_titleCodexNaturalis();
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println(ansi().cursor(Constants.row_nextTurn, 0).fg(YELLOW).a("""

                ░██████╗░░█████╗░███╗░░░███╗███████╗        ███████╗███╗░░██╗██████╗░███████╗██████╗░
                ██╔════╝░██╔══██╗████╗░████║██╔════╝        ██╔════╝████╗░██║██╔══██╗██╔════╝██╔══██╗
                ██║░░██╗░███████║██╔████╔██║█████╗░░        █████╗░░██╔██╗██║██║░░██║█████╗░░██║░░██║
                ██║░░╚██╗██╔══██║██║╚██╔╝██║██╔══╝░░        ██╔══╝░░██║╚████║██║░░██║██╔══╝░░██║░░██║
                ╚██████╔╝██║░░██║██║░╚═╝░██║███████╗        ███████╗██║░╚███║██████╔╝███████╗██████╔╝
                ░╚═════╝░╚═╝░░╚═╝╚═╝░░░░░╚═╝╚══════╝        ╚══════╝╚═╝░░╚══╝╚═════╝░╚══════╝╚═════╝░
                                
                """).fg(DEFAULT).reset());

        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println(ansi().cursor(Constants.row_nextTurn + 10, 0).bg(CYAN).a("""

                █     █▀▀▀  █▀▀█  █▀▀▄  █▀▀▀  █▀▀█    █▀▀█  █▀▀▀█  █▀▀█  █▀▀█  █▀▀▄
                █     █▀▀▀  █▄▄█  █  █  █▀▀▀  █▄▄▀    █▀▀▄  █   █  █▄▄█  █▄▄▀  █  █
                █▄▄█  █▄▄▄  █  █  █▄▄▀  █▄▄▄  █  █    █▄▄█  █▄▄▄█  █  █  █  █  █▄▄
                        
                """).bg(DEFAULT).reset());


        int i = 1;
        int classif = 1;
        StringBuilder ris = new StringBuilder();
        for (Map.Entry<Player, Integer> entry : model.getLeaderBoard().entrySet()) {
            StaticPrinterTUI.print("");
            ris.append(ansi().fg(WHITE).cursor(Constants.row_leaderboard + i, Constants.col_leaderboard)
                    .a("#" + classif + " "
                            + entry.getKey().getNickname() + ": "
                            + entry.getValue() + " points\n").fg(GREEN));
            i += 2;
            classif++;
        }
        print(ris);
    }

    // *********************** SHOW METHODS  *********************** //

    /**
     * @param gameModel the model that has the player hand that needs to be shown
     */
    @Override
    public void show_playerHand(ModelView gameModel, String nickname) {
        List<ResourceCard> player_hand = gameModel.getPlayerEntity(nickname).getHand();
        for (ResourceCard c : player_hand) {
            if (c instanceof GoldCard) {
                System.out.println("** GOLD CARD **" + " " + "(" + (player_hand.indexOf(c) + 1) + ")" + "\n");
                TuiCardGraphics.showGoldCard((GoldCard) c);
            }
            else {
                System.out.println("** RESOURCE CARD **" + " " + "(" + (player_hand.indexOf(c) + 1) + ")" + "\n");
                TuiCardGraphics.showResourceCard(c);
            }
        }
    }

    @Override
    public void show_objectiveCards(ModelView gameModel) {
        clearScreen();
        StaticPrinterTUI.print("First, we show you the two objective cards you can choose from: \n");
        List<ObjectiveCard> objectiveCards = gameModel.getObjectiveCards(nickname);
        TuiCardGraphics.showObjectiveCard(objectiveCards.get(0));
        TuiCardGraphics.showObjectiveCard(objectiveCards.get(1));
        clearScreen();
    }

    @Override
    public void show_starterCards(ModelView gameModel) {
        clearScreen();
        List<StarterCard> starterCards = gameModel.getStarterCards(nickname);
        TuiCardGraphics.showStarterCardFront(starterCards.get(0));
        TuiCardGraphics.showStarterCardBack(starterCards.get(1));
        clearScreen();
    }

    /**
     * @param nickname the player that grabbed the tiles
     * @param model    the model in which the player grabbed the tiles
     */
    @Override
    public void show_cardChosen(String nickname, ModelView model) {
        clearScreen();
        if (model.getPlayerEntity(nickname).getChosenGameCard() instanceof GoldCard) {
            System.out.println("You have chosen the following Gold Card: \n");
            TuiCardGraphics.showGoldCard((GoldCard) model.getPlayerEntity(nickname).getChosenGameCard());
        }
        else {
            System.out.println("You have chosen the following Resource Card: \n");
            TuiCardGraphics.showResourceCard(model.getPlayerEntity(nickname).getChosenGameCard());
        }
        clearScreen();
    }

    @Override
    public void show_illegalMove() {
        clearScreen();
        StaticPrinterTUI.print("Illegal move! Try again and choose different coordinates! \n");
        clearScreen();
    }

    @Override
    public void show_illegalMoveBecauseOf(String message) {
        clearScreen();
        StaticPrinterTUI.print("Illegal move! " + message + "\n");
        clearScreen();
    }

    @Override
    protected void show_successfulMove(Coordinate coord) {

    }

    @Override
    public void show_whereToDrawFrom() {
        clearScreen();
        StaticPrinterTUI.print("""                       
                        Select the index of the card you want to draw:\s
                         1: Resource Deck
                         2: First Resource Card on the table
                         3: Second Resource Card on the table
                         4: Gold Deck
                         5: First Gold Card on the table
                         6: Second Gold Card on the table""");
        clearScreen();
    }

    /**
     * show the common board
     * @param model the model that has the common board to show
     */
    @Override
    public void show_commonBoard(ModelView model) {
        clearScreen();
        TuiCommonBoardGraphics.showCommonBoard(model.getCommonBoard());
        clearScreen();
    }

    @Override
    public void show_myTurnIsFinished() {
        clearScreen();
        StaticPrinterTUI.print("Your turn is finished. Now, wait until it is again your turn! \n");
        clearScreen();
    }

    /**
     * show the personal board
     * @param nickname the player whose personal board we want to show
     * @param model the model that has the personal board to show
     */
    @Override
    public void show_personalBoard(String nickname, ModelView model) {
        clearScreen();
        TuiPersonalBoardGraphics.showPersonalBoard(model.getPlayerEntity(nickname).getPersonalBoard());
        clearScreen();
    }

    @Override
    protected void show_othersPersonalBoard(ModelView model, int playerIndex) {
        clearScreen();
        if(model.getAllPlayers().size() > playerIndex)
            TuiPersonalBoardGraphics.showPersonalBoard(model.getAllPlayers().get(playerIndex).getPersonalBoard());
        clearScreen();
    }

    @Override
    public void show_commonObjectives(ModelView gameModel) {

    }

    @Override
    public void show_personalObjectiveCard(ModelView gameModel) {
        clearScreen();
        System.out.println("Your chosen objective card is: \n");
        TuiCardGraphics.showObjectiveCard(gameModel.getPlayerEntity(nickname).getChosenObjectiveCard());
        clearScreen();
    }

    @Override
    public void show_playerJoined(ModelView gameModel, String nick) {
        clearScreen();
        show_titleCodexNaturalis();
        StaticPrinterTUI.print(ansi().cursor(10, 0).a("GameID: [" + gameModel.getGameId().toString() + "]\n").fg(DEFAULT));
        clearScreen();
        System.out.flush();
        StringBuilder ris = new StringBuilder();

        clearScreen();
        int i = 0;
        for (Player p : gameModel.getPlayersConnected()) {
            if (p.getReadyToStart()) {
                ris.append(ansi().cursor(12 + i, 0)).append("[EVENT]: ").append(p.getNickname()).append(" is ready!\n");
            } else {
                ris.append(ansi().cursor(12 + i, 0)).append("[EVENT]: ").append(p.getNickname()).append(" has joined!\n");
            }
            clearScreen();
            i++;
        }
        printNoNewLine(ris);

        clearScreen();
        for (Player p : gameModel.getPlayersConnected())
            if (!p.getReadyToStart() && p.getNickname().equals(nick))
                StaticPrinterTUI.print(ansi().cursor(17, 0).fg(WHITE).a("> When you are ready to start, enter (y): \n"));
        clearScreen();
        System.out.flush();
    }

    /**
     * Shows the important gameFacts
     */
    public void show_important_events() {

        clearScreen();
        StringBuilder ris = new StringBuilder();
        int i = 0;
        int longestImportantEvent = importantEvents.stream().map(String::length).reduce(0, (a, b) -> a > b ? a : b);
        ris.append(ansi().fg(GREEN).cursor(Constants.row_important_events + i, Constants.col_important_events - 1).bold().a("Latest Events: ").fg(DEFAULT).boldOff());
        for (String s : importantEvents) {
            ris.append(ansi().fg(WHITE).cursor(Constants.row_important_events + 1 + i, Constants.col_important_events).a(s).a(" ".repeat(longestImportantEvent - s.length())).fg(DEFAULT));
            i++;
        }
        print(ris);
        clearScreen();

        StaticPrinterTUI.print(ansi().cursor(Constants.row_input, 0));
    }


    /**
     * Error message when there are no games to join
     *
     * @param msgToVisualize message that needs visualisation
     */
    @Override
    public void show_noAvailableGamesToJoin(String msgToVisualize) {
        String ris = ansi().fg(RED).cursor(11, 4).bold().a(msgToVisualize).fg(DEFAULT).boldOff() +
                String.valueOf(ansi().fg(RED).cursor(12, 4).bold().a(" Try later or create a new game!").fg(DEFAULT).boldOff());
        ansi().fg(DEFAULT);
        printNoNewLine(ris);
    }

    /**
     * Clears important gameFacts' list
     */
    @Override
    public void resetImportantEvents() {
        this.importantEvents = new ArrayList<>();
        this.nickname = null;
    }

    /**
     * Shows error if there's no connection
     */
    @Override
    protected void show_noConnectionError() {
        this.clearScreen();
        StaticPrinterTUI.print(ansi().fg(WHITE).bg(RED).bold().a("CONNECTION TO SERVER LOST!")
                .boldOff().fg(DEFAULT).bgDefault());
        System.exit(-1);
    }

    @Override
    protected void show_chosenNickname(String nickname) {

    }

    @Override
    protected void show_nextTurn(ModelView model, String nickname) {

    }

    @Override
    protected void show_cardDrawn(ModelView model, String nickname) {

    }

    /**
     * Asks the player to pick a direction
     */
    @Override
    public void show_orientation(String message) {
        StaticPrinterTUI.print(message + "\n" + "\t> Choose card orientation (f:FRONT / b:BACK): ");
        printNoNewLine(ansi().cursorDownLine().a(""));
    }

    /**
     * Shows the messages sent
     *
     * @param model    the model where the message need to be shown
     * @param nickname the sender's nickname
     */
    @Override
    public void show_messageSent(ModelView model, String nickname) {
        Message mess = model.getChat().getLastMessage();
        StaticPrinterTUI.print(ansi().cursor(Constants.row_chat, Constants.col_chat).a(mess.sender().getNickname() + ": " + mess.text()));
    }

    /**
     * Shows messages on game start
     *
     * @param model model where the game has started
     */
    @Override
    public void show_gameStarted(ModelView model) {
        clearScreen();
        show_titleCodexNaturalis();
        show_important_events();
    }

    /**
     * Tells the player to return to the main menu
     */
    @Override
    public void show_menu() {
        StaticPrinterTUI.print("\nPress any key to return to the menu");
    }

    @Override
    public void show_genericMessage(String msg) {
        clearScreen();
        StaticPrinterTUI.print(Ansi.ansi().bold().fg(Ansi.Color.MAGENTA).a(msg).reset());
    }

    @Override
    public void show_genericError(String msg) {
        clearScreen();
        StaticPrinterTUI.print(Ansi.ansi().bold().fg(Ansi.Color.RED).a("\n> Select which card from your hand you want to place (1 / 2 / 3):").reset().toString());
    }

    /**
     * Asks the player to insert his nickname
     */
    @Override
    public void show_insertNicknameMsg() {
        clearScreen();
        printNoNewLine(ansi().cursor(Constants.row_gameID, 0).a("> Insert your nickname: "));
    }

    /**
     * Asks the player to choose number of tiles to pick up
     */
    @Override
    public void show_insertNumOfPlayersMsg() {
        clearScreen();
        printNoNewLine(ansi().cursor(Constants.row_gameID, 0).a("> Choose the number of players for this game: "));
    }

    /**
     * Shows initial menu
     */
    @Override
    public void show_menuOptions() {
        this.clearScreen();
        this.show_titleCodexNaturalis();
        StaticPrinterTUI.print(ansi().cursor(9, 0).a("""
                > Select one option:
                \tpress (c) to create a new game
                \tpress (j) to join to a randomly an existent game
                \tpress (js) to join to a specific game
                \t
                \t -> Useful commands that can be used at any point in the game:
                \t\t  type "/c [msg]" to send a public message!
                \t\t  type "/cs [playerName] [msg]" to send a private message!
                \t\t  type "/quit" and you can leave the game!
                \t""").fg(DEFAULT));
    }

    /**
     * Asks for the game id
     */
    @Override
    public void show_inputGameIdMsg() {
        clearScreen();
        printNoNewLine("> Input the GameId: ");
    }

    /**
     * Generic error message
     */
    @Override
    public void show_invalidInput() {
        StaticPrinterTUI.print("[WARNING]: invalid input");
    }

    /**
     * Asks which tile to place
     */
    @Override
    public void show_whichCardToPlaceMsg() {
        clearScreen();
        StaticPrinterTUI.print(Ansi.ansi().bold().fg(Ansi.Color.MAGENTA).a(
                "\n> Select which card from your hand you want to place (1 / 2 / 3):").reset().toString());
    }

    @Override
    public void show_whichObjectiveToChooseMsg() {
        StaticPrinterTUI.print("> Select which objective card you want to choose (1 / 2):");
    }

    /**
     * Creating game message
     *
     * @param nickname player's nickname
     */
    @Override
    public void show_creatingNewGameMsg(String nickname) {
        this.clearScreen();
        this.show_titleCodexNaturalis();
        StaticPrinterTUI.print("> Creating a new game...");
        this.nickname = nickname;
    }

    /**
     * Join first available game message
     *
     * @param nickname player's nickname
     */
    @Override
    public void show_joiningFirstAvailableMsg(String nickname) {
        this.clearScreen();
        this.show_titleCodexNaturalis();
        StaticPrinterTUI.print("> Connecting to the first available game...");
        this.nickname = nickname;
    }

    /**
     * Shows the game id of the game that it's been joined
     *
     * @param idGame   id of the game the player is trying to join
     * @param nickname player's nickname
     */
    @Override
    public void show_joiningToGameIdMsg(int idGame, String nickname) {
        this.clearScreen();
        this.show_titleCodexNaturalis();
        StaticPrinterTUI.print("> You have selected to join to Game with id: '" + idGame + "', trying to connect");
        this.nickname = nickname;
    }

    @Override
    public void show_ReadyToStart(ModelView gameModel, String s) {

    }

}
