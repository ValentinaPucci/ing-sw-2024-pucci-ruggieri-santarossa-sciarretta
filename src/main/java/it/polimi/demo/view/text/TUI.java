package it.polimi.demo.view.text;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import it.polimi.demo.DefaultValues;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.model.interfaces.PlayerIC;
import it.polimi.demo.view.flow.UI;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;
import static it.polimi.demo.view.text.PrintAsync.*;

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
     * @param input the string of the important event to add
     */
    @Override
    public void addImportantEvent(String input) {
        //Want to show only the first maxnum_of_last_event_tobe_showed important event happened
        if (importantEvents.size() + 1 >= DefaultValues.maxnum_of_last_event_tobe_showed) {
            importantEvents.remove(0);
        }
        importantEvents.add(input);
        show_important_events();
    }

    // ********************* aux ********************* //

    /**
     * Resizes the console
     */
    public void resize() {
        try {
            new ProcessBuilder("cmd", "/c", "mode con:cols=160 lines=50").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            //couldn't resize the terminal window
        }
    }

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
     * Shows the publisher's name
     */
    @Override
    public void show_publisher() {
        // this.resize();
        clearScreen();
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println(ansi().cursor(1, 1).fg(YELLOW).a("""
                                                                                                           \s
                                                                                                           \s
                  ,----..                                                                                  \s
                 /   /   \\                                   ,--,                                          \s
                |   :     :  __  ,-.                 ,---, ,--.'|    ,---.                                 \s
                .   |  ;. /,' ,'/ /|             ,-+-. /  ||  |,    '   ,'\\                                \s
                .   ; /--` '  | |' | ,--.--.    ,--.'|'   |`--'_   /   /   |                               \s
                ;   | ;    |  |   ,'/       \\  |   |  ,"' |,' ,'| .   ; ,. :                               \s
                |   : |    '  :  / .--.  .-. | |   | /  | |'  | | '   | |: :                               \s
                .   | '___ |  | '   \\__\\/: . . |   | |  | ||  | : '   | .; :                               \s
                '   ; : .'|;  : |   ," .--.; | |   | |  |/ '  : |_|   :    |                               \s
                '   | '/  :|  , ;  /  /  ,.  | |   | |--'  |  | '.'\\   \\  /                                \s
                |   :    /  ---'  ;  :   .'   \\|   |/      ;  :    ;`----'                                 \s
                 \\   \\ .'         |  ,     .-./'---'       |  ,   /                                        \s
                  `---`            `--`---'                 ---`-'                                         \s
                  ,----..                                   ___                                            \s
                 /   /   \\                                ,--.'|_    ,--,                                  \s
                |   :     :  __  ,-.                      |  | :,' ,--.'|    ,---.        ,---,            \s
                .   |  ;. /,' ,'/ /|                      :  : ' : |  |,    '   ,'\\   ,-+-. /  | .--.--.   \s
                .   ; /--` '  | |' | ,---.     ,--.--.  .;__,'  /  `--'_   /   /   | ,--.'|'   |/  /    '  \s
                ;   | ;    |  |   ,'/     \\   /       \\ |  |   |   ,' ,'| .   ; ,. :|   |  ,"' |  :  /`./  \s
                |   : |    '  :  / /    /  | .--.  .-. |:__,'| :   '  | | '   | |: :|   | /  | |  :  ;_    \s
                .   | '___ |  | ' .    ' / |  \\__\\/: . .  '  : |__ |  | : '   | .; :|   | |  | |\\  \\    `. \s
                '   ; : .'|;  : | '   ;   /|  ," .--.; |  |  | '.'|'  : |_|   :    ||   | |  |/  `----.   \\\s
                '   | '/  :|  , ; '   |  / | /  /  ,.  |  ;  :    ;|  | '.'\\   \\  / |   | |--'  /  /`--'  /\s
                |   :    /  ---'  |   :    |;  :   .'   \\ |  ,   / ;  :    ;`----'  |   |/     '--'.     / \s
                 \\   \\ .'          \\   \\  / |  ,     .-./  ---`-'  |  ,   /         '---'        `--'---'  \s
                  `---`             `----'   `--`---'               ---`-'                                 \s
                """).reset());

        try {
            Thread.sleep(DefaultValues.time_publisher_showing_seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.show_titleMyShelfie();
    }

    /**
     * Shows the game title
     */
    public void show_titleMyShelfie() {
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
    @Override
    public void show_gameEnded(GameModelImmutable model) {
        clearScreen();
        // resize();
        show_titleMyShelfie();
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println(ansi().cursor(DefaultValues.row_nextTurn, 0).fg(GREEN).a("""

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
        ).println(ansi().cursor(DefaultValues.row_nextTurn + 10, 0).bg(CYAN).a("""

                █     █▀▀▀  █▀▀█  █▀▀▄  █▀▀▀  █▀▀█    █▀▀█  █▀▀▀█  █▀▀█  █▀▀█  █▀▀▄
                █     █▀▀▀  █▄▄█  █  █  █▀▀▀  █▄▄▀    █▀▀▄  █   █  █▄▄█  █▄▄▀  █  █
                █▄▄█  █▄▄▄  █  █  █▄▄▀  █▄▄▄  █  █    █▄▄█  █▄▄▄█  █  █  █  █  █▄▄
                        
                """).bg(DEFAULT).reset());


        int i = 1;
        int classif = 1;
        StringBuilder ris = new StringBuilder();
//        for (Map.Entry<PlayerIC, Integer> entry : model.getLeaderBoard().entrySet()) {
//            printAsync("");
//            ris.append(ansi().fg(WHITE).cursor(DefaultValues.row_leaderboard + i, DefaultValues.col_leaderboard)
//                    .a("#" + classif + " "
//                            + entry.getKey().getNickname() + ": "
//                            + entry.getValue() + " points").fg(DEFAULT));
//            i += 2;
//            classif++;
//        }

        printAsync(ris);

    }

    // *********************** SHOW METHODS  *********************** //

    /**
     * Shows all players' nicknames
     *
     * @param model
     */
    // todo: implement the right toString method in PlayerIC
    public void show_allPlayers(GameModelImmutable model) {
        printAsync("Current Players: \n" + model.getPlayersConnected().toString());
    }

    /**
     * @param gameModel the model that has the player hand that needs to be shown
     */
    @Override
    public void show_playerHand(GameModelImmutable gameModel) {

    }

    /**
     * @param nickname the player that grabbed the tiles
     * @param model    the model in which the player grabbed the tiles
     */
    @Override
    public void show_cardChosen(String nickname, GameModelImmutable model) {

    }

    /**
     * show the common board
     * @param model the model that has the common board to show
     */
    public void show_commonBoard(GameModelImmutable model) {

    }

    /**
     * show the personal board
     * @param nickname the player whose personal board we want to show
     * @param model the model that has the personal board to show
     */
    public void show_personalBoard(String nickname, GameModelImmutable model) {

    }

    /**
     * @param gameModel the model that has the common cards to show
     */
    @Override
    public void show_commonObjectives(GameModelImmutable gameModel) {

    }

    /**
     * Shows the player's goal card
     * @param p the player whose goal card we want to show
     */
    public void show_personalObjectiveCard(PlayerIC p) {
        printAsync(p.getChosenObjectiveCard().toString());
    }

    /**
     * @param gameModel model where events happen
     * @param nick      player's nickname
     */
    @Override
    public void show_playerJoined(GameModelImmutable gameModel, String nick) {
        clearScreen();
        show_titleMyShelfie();
        printAsync(ansi().cursor(10, 0).a("GameID: [" + gameModel.getGameId().toString() + "]\n").fg(DEFAULT));
        clearScreen();
        System.out.flush();
        StringBuilder ris = new StringBuilder();

        clearScreen();
        int i = 0;
        for (PlayerIC p : gameModel.getPlayersConnected()) {
            if (p.getReadyToStart()) {
                ris.append(ansi().cursor(12 + i, 0)).append("[EVENT]: ").append(p.getNickname()).append(" is ready!\n");
            } else {
                ris.append(ansi().cursor(12 + i, 0)).append("[EVENT]: ").append(p.getNickname()).append(" has joined!\n");
            }
            i++;
        }
        printAsyncNoCursorReset(ris);

        clearScreen();
        for (PlayerIC p : gameModel.getPlayersConnected())
            if (!p.getReadyToStart() && p.getNickname().equals(nick))
                printAsyncNoCursorReset(ansi().cursor(17, 0).fg(WHITE).a("> When you are ready to start, enter (y): \n"));
        clearScreen();
        System.out.flush();
    }

    /**
     * @param gameModel     model where events happen
     * @param nicknameofyou player's nickname
     */
    @Override
    protected void show_youReadyToStart(GameModelImmutable gameModel, String nicknameofyou) {

    }

    /**
     * Shows the important events
     */
    public void show_important_events() {

        clearScreen();
        StringBuilder ris = new StringBuilder();
        int i = 0;
        int longestImportantEvent = importantEvents.stream().map(String::length).reduce(0, (a, b) -> a > b ? a : b);
        ris.append(ansi().fg(GREEN).cursor(DefaultValues.row_important_events + i, DefaultValues.col_important_events - 1).bold().a("Latest Events:").fg(DEFAULT).boldOff());
        for (String s : importantEvents) {
            ris.append(ansi().fg(WHITE).cursor(DefaultValues.row_important_events + 1 + i, DefaultValues.col_important_events).a(s).a(" ".repeat(longestImportantEvent - s.length())).fg(DEFAULT));
            i++;
        }
        printAsync(ris);

        printAsync(ansi().cursor(DefaultValues.row_input, 0));
    }

    /**
     * Shows the chat messages
     *
     * @param model
     */
    public void show_messages(GameModelImmutable model) {
        String ris = String.valueOf(ansi().fg(GREEN).cursor(DefaultValues.row_chat, DefaultValues.col_chat - 1).bold().a("Latest Messages:").fg(DEFAULT).boldOff()) +
                ansi().fg(WHITE).cursor(DefaultValues.row_chat + 1, DefaultValues.col_chat).a(model.getChat().toString(this.nickname)).fg(DEFAULT);
        printAsync(ris);
        if (!model.getChat().getMsgs().isEmpty()) {
            printAsync(ansi().cursor(DefaultValues.row_input, 0));
        }
    }

    /**
     * @param model the model in which search for the longest message
     * @return the length of the longest message
     */
    @Override
    public int getLengthLongestMessage(GameModelImmutable model) {
        return model.getChat().getMsgs().stream()
                .map(Message::getText)
                .reduce((a, b) -> a.length() > b.length() ? a : b)
                .toString().length();
    }

    /**
     * @param msg   the message to add
     * @param model the model to which add the message
     */
    @Override
    public void addMessage(Message msg, GameModelImmutable model) {
        show_messages(model);
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
        printAsyncNoCursorReset(ris);
    }

    /**
     * Stuff that always needs to be visible
     *
     * @param model
     */
    public void show_alwaysShowForAll(GameModelImmutable model) {
        this.clearScreen();
//        show_titleMyShelfie();
//        show_gameId(model);
//        show_commonBoard(model);
//        show_commonObjectives(model);
        show_messages(model);
        show_important_events();
    }

    /**
     * Shows the game id
     *
     * @param gameModel
     */
    public void show_gameId(GameModelImmutable gameModel) {
        printAsync(ansi().cursor(DefaultValues.row_gameID, 0).bold().a("Game with id: [" + gameModel.getGameId() + "]").boldOff());
    }

    /**
     * Shows the next player
     *
     * @param gameModel
     */
    public void show_nextTurn(GameModelImmutable gameModel) {
        printAsync(ansi().cursor(DefaultValues.row_nextTurn, 0).bold().a("Next turn! It's up to: "
                + gameModel.getPlayersConnected().peek().getNickname()).boldOff());
    }

    /**
     * Shows a welcome message
     *
     * @param nick
     */
    public void show_welcome(String nick) {
        printAsync(ansi().cursor(DefaultValues.row_nextTurn + 1, 0).bold().a("Welcome " + nick).boldOff());
    }

    /**
     * Clears important events' list
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
        printAsync(ansi().fg(WHITE).bg(RED).bold().a("CONNECTION TO SERVER LOST!")
                .boldOff().fg(DEFAULT).bgDefault());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(-1);
    }

    /**
     * Asks the player to pick a direction
     */
    @Override
    public void show_orientation() {
        printAsync("\t> Choose card orientation (F = FRONT, B = BACK): ");
        printAsyncNoCursorReset(ansi().cursorDownLine().a(""));
    }

    /**
     * Asks the player to pick up tiles
     */
    @Override
    protected void show_askChooseCardMainMsg() {

    }

    /**
     * clears console
     *
     * @param msg
     * @param model
     */
    public void removeInput(String msg, GameModelImmutable model) {
        printAsync(ansi().cursor(DefaultValues.row_input, 0).a(msg).a(" ".repeat(getLengthLongestMessage(model))));
        printAsyncNoLine(ansi().cursorDownLine());
    }

    /**
     * Shows the messages sent
     *
     * @param model    the model where the message need to be shown
     * @param nickname the sender's nickname
     */
    @Override
    public void show_messageSent(GameModelImmutable model, String nickname) {
        this.show_alwaysShow(model, nickname);
    }


    /**
     * Shows the updated shelves
     *
     * @param model    the model in which the player is found
     * @param nickname the player who positioned the tile
     */
    @Override
    public void show_cardPlaced(GameModelImmutable model, String nickname) {
        this.show_alwaysShow(model, nickname);
    }

    /**
     * Shows messages on game start
     *
     * @param model model where the game has started
     */
    @Override
    public void show_gameStarted(GameModelImmutable model) {
        this.clearScreen();
        this.show_titleMyShelfie();
//        this.show_allPlayers(model);
        this.show_alwaysShowForAll(model);
//        this.show_gameId(model);
    }

    /**
     * Shows a message for the next turn or when a player reconnects
     *
     * @param model    model where events happen
     * @param nickname nick of reconnected player (or of the player that is now in turn)
     */
    @Override
    public void show_nextTurnOrPlayerReconnected(GameModelImmutable model, String nickname) {
        this.show_alwaysShow(model, nickname);
    }

    /**
     * Tells the player to return to the main menu
     */
    @Override
    public void show_returnToMenuMsg() {
        printAsyncNoCursorReset("\nPress any key to return to the menu");
    }

    /**
     * Asks the player to choose a column
     */
    @Override
    protected void show_askCardCoordinatesMainMsg() {

    }

    /**
     * Asks the player to insert his nickname
     */
    @Override
    public void show_insertNicknameMsg() {
        clearScreen();
        printAsyncNoCursorReset(ansi().cursor(DefaultValues.row_gameID, 0).a("> Insert your nickname: "));
    }

    /**
     * Shows chosen nickname
     *
     * @param nickname nickname just chosen by the player
     */
    @Override
    public void show_chosenNickname(String nickname) {
        printAsync(ansi().cursor(DefaultValues.row_gameID + 2, 0).a("> Your nickname is: " + nickname));
    }

    /**
     * Asks the player to choose number of tiles to pick up
     */
    @Override
    public void show_insertNumOfPlayersMsg() {
        clearScreen();
        printAsyncNoCursorReset(ansi().cursor(DefaultValues.row_gameID, 0).a("> Choose the number of players for this game: "));
    }

    @Override
    public void show_chosenNumOfPLayers(int n) {
        printAsync(ansi().cursor(DefaultValues.row_gameID + 2, 0).a("> You have chosen " + n + " players"));
    }

    /**
     * Shows initial menu
     */
    @Override
    public void show_menuOptions() {
        this.clearScreen();
        this.show_titleMyShelfie();
        printAsyncNoCursorReset(ansi().cursor(9, 0).a("""
                > Select one option:
                \t(c) Create a new Game
                \t(j) Join to a random Game
                \t(js) Join a specific Game by idGame
                \t(x) Reconnect
                \t(.) to leave
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
        printAsyncNoCursorReset("> Input the GameId ('.' to leave): ");
    }

    /**
     * Generic error message
     */
    @Override
    public void show_NaNMsg() {
        printAsync("> NaN");
    }

    /**
     * Asks which tile to place
     */
    @Override
    public void show_whichCardToPlaceMsg() {
        printAsync("> Select which card from your hand you want to place:");
    }

    /**
     * Creating game message
     *
     * @param nickname player's nickname
     */
    @Override
    public void show_creatingNewGameMsg(String nickname) {
        this.clearScreen();
        this.show_titleMyShelfie();
        printAsync("> Creating a new game...");
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
        this.show_titleMyShelfie();
        printAsyncNoCursorReset("> Connecting to the first available game...");
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
        this.show_titleMyShelfie();
        printAsync("> You have selected to join to Game with id: '" + idGame + "', trying to connect");
        this.nickname = nickname;
    }

    /**
     * Messages that always need to be on screen
     *
     * @param model
     * @param nick
     */
    public void show_alwaysShow(GameModelImmutable model, String nick) {
        show_alwaysShowForAll(model);
        show_personalObjectiveCard(model.getPlayerEntity(nick));
        if (!model.getPlayerEntity(nick).getHand().isEmpty())
            show_playerHand(model);
        else {
            if (!model.getPlayersConnected().peek().getHand().isEmpty())
                show_cardChosen(model.getPlayersConnected().peek().getNickname(), model);
        }
        show_personalBoard(nick, model);
        show_nextTurn(model);
        show_welcome(nick);

        printAsync(ansi().cursor(DefaultValues.row_input, 0));
    }


}
