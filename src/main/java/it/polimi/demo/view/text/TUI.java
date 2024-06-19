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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static it.polimi.demo.view.text.StaticPrinterTUI.print;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Text User Interface class
 */
public class TUI extends UI {

    private String nickname;

    /**
     * constructor
     */
    public TUI() {
        initializer();
    }

    /**
     * initializer
     */
    @Override
    public void initializer() {
        AnsiConsole.systemInstall();
    }


    // ********************* aux ********************* //

    /**
     * clears the screen
     */
    public void clearScreen() {
        try {
            Runtime.getRuntime().exec("clear");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * shows the title
     */
    public void show_title() {
        clearScreen();
        String message = ansi().fg(YELLOW).a("""
                  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄   ▄▄▄▄▄▄▄▄▄▄▄  ▄       ▄                                                      \s
                ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░▌ ▐░░░░░░░░░░░▌▐░▌     ▐░▌                                                     \s
                ▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀▀▀  ▐░▌   ▐░▌                                                      \s
                ▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌            ▐░▌ ▐░▌                                                       \s
                ▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄▄▄    ▐░▐░▌                                                        \s
                ▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌    ▐░▌                                                         \s
                ▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀▀▀    ▐░▌░▌                                                        \s
                ▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌            ▐░▌ ▐░▌                                                       \s
                ▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄▄▄  ▐░▌   ▐░▌                                                      \s
                ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░▌ ▐░░░░░░░░░░░▌▐░▌     ▐░▌                                                     \s
                 ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀   ▀▀▀▀▀▀▀▀▀▀▀  ▀       ▀                                                      \s
                                                                                                                                    \s
                 ▄▄        ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄            ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄\s
                ▐░░▌      ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░▌          ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌
                ▐░▌░▌     ▐░▌▐░█▀▀▀▀▀▀▀█░▌ ▀▀▀▀█░█▀▀▀▀ ▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌           ▀▀▀▀█░█▀▀▀▀ ▐░█▀▀▀▀▀▀▀▀▀\s
                ▐░▌▐░▌    ▐░▌▐░▌       ▐░▌     ▐░▌     ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌               ▐░▌     ▐░▌         \s
                ▐░▌ ▐░▌   ▐░▌▐░█▄▄▄▄▄▄▄█░▌     ▐░▌     ▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░▌               ▐░▌     ▐░█▄▄▄▄▄▄▄▄▄\s
                ▐░▌  ▐░▌  ▐░▌▐░░░░░░░░░░░▌     ▐░▌     ▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░▌               ▐░▌     ▐░░░░░░░░░░░▌
                ▐░▌   ▐░▌ ▐░▌▐░█▀▀▀▀▀▀▀█░▌     ▐░▌     ▐░▌       ▐░▌▐░█▀▀▀▀█░█▀▀ ▐░█▀▀▀▀▀▀▀█░▌▐░▌               ▐░▌      ▀▀▀▀▀▀▀▀▀█░▌
                ▐░▌    ▐░▌▐░▌▐░▌       ▐░▌     ▐░▌     ▐░▌       ▐░▌▐░▌     ▐░▌  ▐░▌       ▐░▌▐░▌               ▐░▌               ▐░▌
                ▐░▌     ▐░▐░▌▐░▌       ▐░▌     ▐░▌     ▐░█▄▄▄▄▄▄▄█░▌▐░▌      ▐░▌ ▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄▄▄  ▄▄▄▄█░█▄▄▄▄  ▄▄▄▄▄▄▄▄▄█░▌
                ▐░▌      ▐░░▌▐░▌       ▐░▌     ▐░▌     ▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌
                 ▀        ▀▀  ▀         ▀       ▀       ▀▀▀▀▀▀▀▀▀▀▀  ▀         ▀  ▀         ▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀\s
                """).reset().toString();
        if (System.console() != null) {
            System.out.println(new String(message.getBytes(), System.console().charset()));
        } else {
            System.out.println(message);
        }
    }

    /**
     * shows the game ended
     * @param model The model view containing game state information.
     */
    @Override
    public void show_gameEnded(ModelView model) {
        clearScreen();
        String message_1 = ansi().fg(YELLOW).a("""
                  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄       ▄▄  ▄▄▄▄▄▄▄▄▄▄▄       ▄▄▄▄▄▄▄▄▄▄▄  ▄▄        ▄  ▄▄▄▄▄▄▄▄▄▄   ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄ \s
                ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░▌     ▐░░▌▐░░░░░░░░░░░▌     ▐░░░░░░░░░░░▌▐░░▌      ▐░▌▐░░░░░░░░░░▌ ▐░░░░░░░░░░░▌▐░░░░░░░░░░▌\s
                ▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌▐░▌░▌   ▐░▐░▌▐░█▀▀▀▀▀▀▀▀▀      ▐░█▀▀▀▀▀▀▀▀▀ ▐░▌░▌     ▐░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌
                ▐░▌          ▐░▌       ▐░▌▐░▌▐░▌ ▐░▌▐░▌▐░▌               ▐░▌          ▐░▌▐░▌    ▐░▌▐░▌       ▐░▌▐░▌          ▐░▌       ▐░▌
                ▐░▌ ▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄█░▌▐░▌ ▐░▐░▌ ▐░▌▐░█▄▄▄▄▄▄▄▄▄      ▐░█▄▄▄▄▄▄▄▄▄ ▐░▌ ▐░▌   ▐░▌▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░▌       ▐░▌
                ▐░▌▐░░░░░░░░▌▐░░░░░░░░░░░▌▐░▌  ▐░▌  ▐░▌▐░░░░░░░░░░░▌     ▐░░░░░░░░░░░▌▐░▌  ▐░▌  ▐░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌
                ▐░▌ ▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌   ▀   ▐░▌▐░█▀▀▀▀▀▀▀▀▀      ▐░█▀▀▀▀▀▀▀▀▀ ▐░▌   ▐░▌ ▐░▌▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░▌       ▐░▌
                ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌               ▐░▌          ▐░▌    ▐░▌▐░▌▐░▌       ▐░▌▐░▌          ▐░▌       ▐░▌
                ▐░█▄▄▄▄▄▄▄█░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄▄▄      ▐░█▄▄▄▄▄▄▄▄▄ ▐░▌     ▐░▐░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄█░▌
                ▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌     ▐░░░░░░░░░░░▌▐░▌      ▐░░▌▐░░░░░░░░░░▌ ▐░░░░░░░░░░░▌▐░░░░░░░░░░▌\s
                 ▀▀▀▀▀▀▀▀▀▀▀  ▀         ▀  ▀         ▀  ▀▀▀▀▀▀▀▀▀▀▀       ▀▀▀▀▀▀▀▀▀▀▀  ▀        ▀▀  ▀▀▀▀▀▀▀▀▀▀   ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀ \s
                                                                                                                                         \s
                """).reset().toString();
        if (System.console() != null) {
            System.out.println(new String(message_1.getBytes(), System.console().charset()));
        } else {
            System.out.println(message_1);
        }

        clearScreen();
        String message_2 = ansi().fg(YELLOW).a("""   
                ▄▄▌  ▄▄▄ . ▄▄▄· ·▄▄▄▄  ▄▄▄ .▄▄▄  ▄▄▄▄·        ▄▄▄· ▄▄▄  ·▄▄▄▄ \s
                ██•  ▀▄.▀·▐█ ▀█ ██▪ ██ ▀▄.▀·▀▄ █·▐█ ▀█▪▪     ▐█ ▀█ ▀▄ █·██▪ ██\s
                ██▪  ▐▀▀▪▄▄█▀▀█ ▐█· ▐█▌▐▀▀▪▄▐▀▀▄ ▐█▀▀█▄ ▄█▀▄ ▄█▀▀█ ▐▀▀▄ ▐█· ▐█▌
                ▐█▌▐▌▐█▄▄▌▐█ ▪▐▌██. ██ ▐█▄▄▌▐█•█▌██▄▪▐█▐█▌.▐▌▐█ ▪▐▌▐█•█▌██. ██\s
                .▀▀▀  ▀▀▀  ▀  ▀ ▀▀▀▀▀•  ▀▀▀ .▀  ▀·▀▀▀▀  ▀█▄▀▪ ▀  ▀ .▀  ▀▀▀▀▀▀•\s                                                                                            \s
                """).reset().toString();
        if (System.console() != null) {
            System.out.println(new String(message_2.getBytes(), System.console().charset()));
        } else {
            System.out.println(message_2);
        }

        int ranking = 1; // Start ranking
        StringBuilder output = new StringBuilder();

        for (Map.Entry<Player, Integer> entry : model.getLeaderBoard().entrySet()) {
            output.append("#")
                    .append(ranking)
                    .append(" ")
                    .append(entry.getKey().getNickname())
                    .append(": ")
                    .append(entry.getValue())
                    .append(" points\n");
            ranking++; // Increment ranking for the next entry
        }
        StaticPrinterTUI.print(output.toString());
    }

    // *********************** SHOW METHODS  *********************** //

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
    public void show_successfulMove(Coordinate coord) {}

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
    public void show_othersPersonalBoard(ModelView model, int playerIndex) {
        clearScreen();
        if (model.getAllPlayers().size() > playerIndex)
            TuiPersonalBoardGraphics.showPersonalBoard(model.getAllPlayers().get(playerIndex).getPersonalBoard());
        clearScreen();
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
        for (Player p : gameModel.getPlayersConnected()) {
            if (p.getReadyToStart()) {
                StaticPrinterTUI.print(p.getNickname() + ": ready\n");
            } else {
                StaticPrinterTUI.print(p.getNickname() + ": joined\n");
            }
        }
        clearScreen();
    }

    @Override
    public void show_noAvailableGamesToJoin(String msgToVisualize) {}

    @Override
    public void show_noConnectionError() {
        StaticPrinterTUI.print("CONNECTION TO SERVER LOST!");
        System.exit(-1);
    }

    @Override
    public void show_chosenNickname(String nickname) {}

    @Override
    public void show_nextTurn(ModelView model, String nickname) {}

    @Override
    public void show_cardDrawn(ModelView model, String nickname) {}

    @Override
    public void show_orientation(String message) {
        StaticPrinterTUI.print(message + "\n" + "\t> Choose card orientation (f:FRONT / b:BACK): ");
        StaticPrinterTUI.printNoNewLine(ansi().cursorDownLine().a(""));
    }

    @Override
    public void show_messageSent(ModelView model, String nickname) {
        Message mess = model.getChat().getLastMessage();
        StaticPrinterTUI.print(ansi().a(mess.sender().getNickname() + ": " + mess.text()));
    }

    @Override
    public void show_gameStarted(ModelView model) {
        clearScreen();
        show_title();
    }

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

    @Override
    public void show_insertNickname() {
        clearScreen();
        StaticPrinterTUI.printNoNewLine(ansi().cursor(Constants.row_gameID, 0).a("> Insert your nickname: "));
    }

    @Override
    public void show_insertNumOfPlayers() {
        clearScreen();
        StaticPrinterTUI.printNoNewLine(ansi().cursor(Constants.row_gameID, 0).a("> Choose the number of players for this game: "));
    }

    /**
     * shows options
     */
    @Override
    public void show_options() {
        this.clearScreen();
        this.show_title();
        StaticPrinterTUI.print(ansi().cursor(9, 0).a("""
                > Select one option:
                \tpress (c) to create a new game
                \tpress (j) to join to a randomly an existent game
                \tpress (js) to join to a specific game
                \t
                \t -> Useful commands that can be used at any point in the game:
                \t\t  type "/c [msg]" to send a public message
                \t\t  type "/cs [playerName] [msg]" to send a private message
                \t\t  type "/leave, /quit, /exit" to leave the game
                \t""").fg(DEFAULT));
    }

    /**
     * Shows the game id
     */
    @Override
    public void show_insertGameId() {
        clearScreen();
        StaticPrinterTUI.printNoNewLine("> Input the GameId: ");
    }

    /**
     * shows invalid input
     */
    @Override
    public void show_invalidInput() {
        StaticPrinterTUI.print("[WARNING]: invalid input");
    }

    /**
     * shows which card to place
     */
    @Override
    public void show_whichCardToPlace() {
        clearScreen();
        StaticPrinterTUI.print(Ansi.ansi().bold().fg(Ansi.Color.MAGENTA).a(
                "\n> Select which card from your hand you want to place (1 / 2 / 3):").reset().toString());
    }

    /**
     * shows which objective card to choose
     */
    @Override
    public void show_whichObjectiveToChoose() {
        StaticPrinterTUI.print("> Select which objective card you want to choose (1 / 2):");
    }

    /**
     * shows create game message
     * @param nickname The nickname of the player who created the game.
     */
    @Override
    public void show_createGame(String nickname) {
        this.nickname = nickname;
        clearScreen();
        show_title();
        StaticPrinterTUI.print("> Creating a new game...");
    }

    /**
     * shows join random game message
     * @param nickname The nickname of the player who joined the game.
     */
    @Override
    public void show_joinRandom(String nickname) {
        this.nickname = nickname;
        clearScreen();
        show_title();
        StaticPrinterTUI.print("> Connecting to the first available game...");
    }

    /**
     * shows join specific game message
     * @param idGame The id of the game to join.
     * @param nickname The nickname of the player who joined the game.
     */
    @Override
    public void show_join(int idGame, String nickname) {
        this.nickname = nickname;
        clearScreen();
        show_title();
        StaticPrinterTUI.print("> You have selected to join to Game with id: '" + idGame + "', trying to connect");
    }

    @Override
    public void show_readyToStart(ModelView gameModel, String s) {}

    @Override
    public void show_pawnPositions(ModelView model){
    }
}
