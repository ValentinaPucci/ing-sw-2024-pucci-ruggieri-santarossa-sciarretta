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
import java.util.List;
import java.util.Map;

import static it.polimi.demo.view.text.StaticPrinterTUI.print;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Text User Interface class
 */
public class TUI extends UI {

    /**
     * The nickname of the player.
     */
    private String nickname;

    /**
     * constructor for textual user interface
     */
    public TUI() { AnsiConsole.systemInstall(); }


    // ********************* aux ********************* //

    /**
     * clears the screen
     */
    public void resetConsole() {
        try {
            Runtime.getRuntime().exec("clear");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * shows the title
     */
    public void displayTitle() {
        resetConsole();
//        String message = ansi().fg(YELLOW).a("""
//                  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄   ▄▄▄▄▄▄▄▄▄▄▄  ▄       ▄                                                      \s
//                ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░▌ ▐░░░░░░░░░░░▌▐░▌     ▐░▌                                                     \s
//                ▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀▀▀  ▐░▌   ▐░▌                                                      \s
//                ▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌            ▐░▌ ▐░▌                                                       \s
//                ▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄▄▄    ▐░▐░▌                                                        \s
//                ▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌    ▐░▌                                                         \s
//                ▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀▀▀    ▐░▌░▌                                                        \s
//                ▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌            ▐░▌ ▐░▌                                                       \s
//                ▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄▄▄  ▐░▌   ▐░▌                                                      \s
//                ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░▌ ▐░░░░░░░░░░░▌▐░▌     ▐░▌                                                     \s
//                 ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀   ▀▀▀▀▀▀▀▀▀▀▀  ▀       ▀                                                      \s
//                                                                                                                                    \s
//                 ▄▄        ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄            ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄\s
//                ▐░░▌      ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░▌          ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌
//                ▐░▌░▌     ▐░▌▐░█▀▀▀▀▀▀▀█░▌ ▀▀▀▀█░█▀▀▀▀ ▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌           ▀▀▀▀█░█▀▀▀▀ ▐░█▀▀▀▀▀▀▀▀▀\s
//                ▐░▌▐░▌    ▐░▌▐░▌       ▐░▌     ▐░▌     ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌               ▐░▌     ▐░▌         \s
//                ▐░▌ ▐░▌   ▐░▌▐░█▄▄▄▄▄▄▄█░▌     ▐░▌     ▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░▌               ▐░▌     ▐░█▄▄▄▄▄▄▄▄▄\s
//                ▐░▌  ▐░▌  ▐░▌▐░░░░░░░░░░░▌     ▐░▌     ▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░▌               ▐░▌     ▐░░░░░░░░░░░▌
//                ▐░▌   ▐░▌ ▐░▌▐░█▀▀▀▀▀▀▀█░▌     ▐░▌     ▐░▌       ▐░▌▐░█▀▀▀▀█░█▀▀ ▐░█▀▀▀▀▀▀▀█░▌▐░▌               ▐░▌      ▀▀▀▀▀▀▀▀▀█░▌
//                ▐░▌    ▐░▌▐░▌▐░▌       ▐░▌     ▐░▌     ▐░▌       ▐░▌▐░▌     ▐░▌  ▐░▌       ▐░▌▐░▌               ▐░▌               ▐░▌
//                ▐░▌     ▐░▐░▌▐░▌       ▐░▌     ▐░▌     ▐░█▄▄▄▄▄▄▄█░▌▐░▌      ▐░▌ ▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄▄▄  ▄▄▄▄█░█▄▄▄▄  ▄▄▄▄▄▄▄▄▄█░▌
//                ▐░▌      ▐░░▌▐░▌       ▐░▌     ▐░▌     ▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌
//                 ▀        ▀▀  ▀         ▀       ▀       ▀▀▀▀▀▀▀▀▀▀▀  ▀         ▀  ▀         ▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀\s
//                """).reset().toString();
//        if (System.console() != null) {
//            System.out.println(new String(message.getBytes(), System.console().charset()));
//        } else {
//            System.out.println(message);
//        }
    }

    /**
     * show game ended
     * @param model The model view containing game state information.
     */
    @Override
    public void displayGameEnded(ModelView model) {
        resetConsole();
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

        resetConsole();
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

    /**
     * show the player hand
     * @param gameModel The model view containing game state information.
     * @param nickname The nickname of the player.
     */
    @Override
    public void displayPlayerHand(ModelView gameModel, String nickname) {
        List<ResourceCard> player_hand = gameModel.getPlayerEntity(nickname).getHand();
        for (ResourceCard c : player_hand) {
            if (c instanceof GoldCard) {
                System.out.println("** GOLD CARD **" + " " + "(" + (player_hand.indexOf(c) + 1) + ")" + "\n");
                TuiCardGraphics.displayGoldCard((GoldCard) c);
            }
            else {
                System.out.println("** RESOURCE CARD **" + " " + "(" + (player_hand.indexOf(c) + 1) + ")" + "\n");
                TuiCardGraphics.displayResourceCard(c);
            }
        }
    }

    /**
     * show the objective cards
     * @param gameModel The model view containing game state information.
     */
    @Override
    public void displayObjectiveCards(ModelView gameModel) {
        resetConsole();
        StaticPrinterTUI.print("First, we show you the two objective cards you can choose from: \n");
        List<ObjectiveCard> objectiveCards = gameModel.getObjectiveCards(nickname);
        TuiCardGraphics.displayObjectiveCard(objectiveCards.get(0));
        TuiCardGraphics.displayObjectiveCard(objectiveCards.get(1));
        resetConsole();
    }

    /**
     * show the starter cards
     * @param gameModel The model view containing game state information.
     */
    @Override
    public void displayStarterCards(ModelView gameModel) {
        resetConsole();
        List<StarterCard> starterCards = gameModel.getStarterCards(nickname);
        TuiCardGraphics.displayStarterCardFront(starterCards.get(0));
        TuiCardGraphics.displayStarterCardBack(starterCards.get(1));
        resetConsole();
    }

    /**
     * show the card chosen
     * @param nickname The nickname of the player.
     * @param model The model view containing game state information.
     */
    @Override
    public void displayCardChosen(String nickname, ModelView model) {
        resetConsole();
        if (model.getPlayerEntity(nickname).getChosenGameCard() instanceof GoldCard) {
            System.out.println("You have chosen the following Gold Card: \n");
            TuiCardGraphics.displayGoldCard((GoldCard) model.getPlayerEntity(nickname).getChosenGameCard());
        }
        else {
            System.out.println("You have chosen the following Resource Card: \n");
            TuiCardGraphics.displayResourceCard(model.getPlayerEntity(nickname).getChosenGameCard());
        }
        resetConsole();
    }

    /**
     * show the illegal move
     */
    @Override
    public void displayIllegalMove() {
        resetConsole();
        StaticPrinterTUI.print("Illegal move! Try again and choose different coordinates! \n");
        resetConsole();
    }

    /**
     * show the illegal move
     * @param message The reason for the illegal move.
     */
    @Override
    public void displayIllegalMoveBecauseOf(String message) {
        resetConsole();
        StaticPrinterTUI.print("Illegal move! " + message + "\n");
        resetConsole();
    }

    /**
     * show the successful move
     * @param coord The coordinates of the successful move.
     */
    @Override
    public void displaySuccessfulMove(Coordinate coord) {}

    /**
     * show where to draw from and associate an index to each option
     */
    @Override
    public void displayWhereToDrawFrom() {
        resetConsole();
        StaticPrinterTUI.print("""                       
                        Select the index of the card you want to draw:\s
                         1: Resource Deck
                         2: First Resource Card on the table
                         3: Second Resource Card on the table
                         4: Gold Deck
                         5: First Gold Card on the table
                         6: Second Gold Card on the table""");
        resetConsole();
    }

    /**
     * show the common board
     * @param model the model that has the common board to show
     */
    @Override
    public void displayCommonBoard(ModelView model) {
        resetConsole();
        //TuiCommonBoardGraphics.showCommonBoard(model.getCommonBoard());
        resetConsole();
    }

    /**
     * show that my turn is finished
     */
    @Override
    public void displayMyTurnIsFinished() {
        resetConsole();
        StaticPrinterTUI.print("Your turn is finished. Now, wait until it is again your turn! \n");
        resetConsole();
    }

    /**
     * show the personal board
     * @param nickname the player whose personal board we want to show
     * @param model the model that has the personal board to show
     */
    @Override
    public void displayPersonalBoard(String nickname, ModelView model) {
        resetConsole();
        TuiPersonalBoardGraphics.showPersonalBoard(model.getPlayerEntity(nickname).getPersonalBoard());
        resetConsole();
    }

    /**
     * show the other players' personal boards
     * @param model The model view containing game state information.
     * @param playerIndex The index of the player whose personal board is to be shown.
     */
    @Override
    public void displayOthersPersonalBoard(ModelView model, int playerIndex) {
        resetConsole();
        if (model.getAllPlayers().size() > playerIndex)
            TuiPersonalBoardGraphics.showPersonalBoard(model.getAllPlayers().get(playerIndex).getPersonalBoard());
        resetConsole();
    }

    /**
     * show the player joined
     * @param modelView The model view containing game state information.
     * @param nick The nickname of the player who joined.
     */
    @Override
    protected void displayPlayerLeft(ModelView modelView, String nick) {}

    /**
     * show the personal objective card
     * @param gameModel The model view containing game state information.
     */
    @Override
    public void displayPersonalObjectiveCard(ModelView gameModel) {
        resetConsole();
        System.out.println("Your chosen objective card is: \n");
        TuiCardGraphics.displayObjectiveCard(gameModel.getPlayerEntity(nickname).getChosenObjectiveCard());
        resetConsole();
    }

    /**
     * show the player joined
     * @param gameModel The model view containing game state information.
     * @param nick The nickname of the player who joined.
     */
    @Override
    public void displayPlayerJoined(ModelView gameModel, String nick) {
        resetConsole();
        for (Player p : gameModel.getPlayersConnected()) {
            if (p.getReadyToStart()) {
                StaticPrinterTUI.print(p.getNickname() + ": ready\n");
            } else {
                StaticPrinterTUI.print(p.getNickname() + ": joined\n");
            }
        }
        resetConsole();
    }

    /**
     * shoe a connection error
     */
    @Override
    public void displayNoConnectionError() {
        StaticPrinterTUI.print("CONNECTION TO SERVER LOST!");
        System.exit(-1);
    }

    /**
     * show the chosen nickname
     * @param nickname The chosen nickname.
     */
    @Override
    public void displayChosenNickname(String nickname) {}

    /**
     * show the next turn
     * @param model The model view containing game state information.
     * @param nickname The nickname of the player who has the next turn.
     */
    @Override
    public void displayNextTurn(ModelView model, String nickname) {}

    /**
     * show the card drawn
     * @param model The model view containing game state information.
     * @param nickname The nickname of the player who drew the card.
     */
    @Override
    public void displayCardDrawn(ModelView model, String nickname) {}

    /**
     * show the orientation
     * @param message The message indicating orientation.
     */
    @Override
    public void displayOrientation(String message) {
        StaticPrinterTUI.print(message + "\n" + "\t> Choose card orientation (f:FRONT / b:BACK): ");
        StaticPrinterTUI.printNoNewLine(ansi().cursorDownLine().a(""));
    }

    /**
     * show the message sent
     * @param model The model view containing game state information.
     * @param nickname The nickname of the player who sent the message.
     */
    @Override
    public void displayMessageSent(ModelView model, String nickname) {
        Message mess = model.getChat().getLastMessage();
        if(nickname.equals("Everyone")){
            StaticPrinterTUI.print(ansi().a(mess.sender().getNickname() + " (to everyone): "+ mess.text()));
        }else{
            StaticPrinterTUI.print(ansi().a(mess.sender().getNickname() + " (to you): " + mess.text()));
        }
    }

    /**
     * show the game started
     * @param model The model view containing game state information.
     */
    @Override
    public void displayGameStarted(ModelView model) {
        resetConsole();
        displayTitle();
    }

    /**
     * show the menu
     */
    @Override
    public void displayMenu() {
        StaticPrinterTUI.print("\nPress any key to return to the menu");
    }

    /**
     * show generic message
     * @param msg The message to show.
     */
    @Override
    public void displayGenericMessage(String msg) {
        resetConsole();
        StaticPrinterTUI.print(Ansi.ansi().bold().fg(Ansi.Color.MAGENTA).a(msg).reset());
    }

    /**
     * show generic error
     * @param msg The error message to show.
     */
    @Override
    public void displayGenericError(String msg) {
        resetConsole();
        StaticPrinterTUI.print(Ansi.ansi().bold().fg(Ansi.Color.RED).a("\n> Select which card from your hand you want to place (1 / 2 / 3):").reset().toString());
    }

    /**
     * show insert nickname
     */
    @Override
    public void displayInsertNickname() {
        resetConsole();
        StaticPrinterTUI.printNoNewLine(ansi().cursor(Constants.row_gameID, 0).a("> Insert your nickname: "));
    }

    /**
     * shows the number of players to insert
     */
    @Override
    public void displayInsertNumOfPlayers() {
        resetConsole();
        StaticPrinterTUI.printNoNewLine(ansi().cursor(Constants.row_gameID, 0).a("> Choose the number of players for this game: "));
    }

    /**
     * shows options
     */
    @Override
    public void startTheGame() {
        this.resetConsole();
        this.displayTitle();
        StaticPrinterTUI.print(ansi().cursor(9, 0).a("""
                > Select one option:
                \ttype (c) to create a new game
                \ttype (j) to join to a random existent game
                \ttype (js) to join to a specific game
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
    public void displayInsertGameId() {
        resetConsole();
        StaticPrinterTUI.printNoNewLine("> Input the GameId: ");
    }

    /**
     * shows invalid input
     */
    @Override
    public void displayInvalidInput() {
        StaticPrinterTUI.print("[WARNING]: invalid input");
    }

    /**
     * shows which card to place
     */
    @Override
    public void displayWhichCardToPlace() {
        resetConsole();
        StaticPrinterTUI.print(Ansi.ansi().bold().fg(Ansi.Color.MAGENTA).a(
                "\n> Select which card from your hand you want to place (1 / 2 / 3):").reset().toString());
    }

    /**
     * shows which objective card to choose
     */
    @Override
    public void displayWhichObjectiveToChoose() {
        StaticPrinterTUI.print("> Select which objective card you want to choose (1 / 2):");
    }

    /**
     * shows create game message
     * @param nickname The nickname of the player who created the game.
     */
    @Override
    public void displayCreateGame(String nickname) {
        this.nickname = nickname;
        resetConsole();
        displayTitle();
        StaticPrinterTUI.print("> Creating a new game...");
    }

    /**
     * shows join random game message
     * @param nickname The nickname of the player who joined the game.
     */
    @Override
    public void displayJoinRandom(String nickname) {
        this.nickname = nickname;
        resetConsole();
        displayTitle();
        StaticPrinterTUI.print("> Connecting to the first available game...");
    }

    /**
     * shows join specific game message
     * @param idGame The id of the game to join.
     * @param nickname The nickname of the player who joined the game.
     */
    @Override
    public void displayJoin(int idGame, String nickname) {
        this.nickname = nickname;
        resetConsole();
        displayTitle();
        StaticPrinterTUI.print("> You have selected to join to Game with id: '" + idGame + "', trying to connect");
    }

    /**
     * show ready to start
     * @param gameModel The model view containing game state information.
     * @param s The nickname of the player who is ready to start.
     */
    @Override
    public void displayReadyToStart(ModelView gameModel, String s) {}

    /**
     * show pawn positions
     * @param model The model view containing game state information.
     */
    @Override
    public void displayPawnPositions(ModelView model) {}
}
