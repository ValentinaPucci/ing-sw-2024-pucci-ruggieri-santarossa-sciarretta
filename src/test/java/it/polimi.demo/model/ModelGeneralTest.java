package it.polimi.demo.model;

import it.polimi.demo.model.board.BoardCellCoordinate;
import it.polimi.demo.model.board.Corner;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.view.text.TUI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelGeneralTest {

    private Model model = new Model();

    @BeforeEach
    public void setUp() {
        model = new Model();
    }

    @Test
    public void testGeneral() throws GameEndedException {

        model.setNumPlayersToPlay(2);

        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");

        model.addPlayer(p1);
        model.addPlayer(p2);

        model.setStatus(GameStatus.WAIT);
        model.setStatus(GameStatus.FIRST_ROUND);
        model.setStatus(GameStatus.RUNNING);
        model.setStatus(GameStatus.SECOND_LAST_ROUND);
        model.setStatus(GameStatus.LAST_ROUND);
        model.setStatus(GameStatus.ENDED);

        model.setStatus(GameStatus.WAIT);

        model.removePlayer(p1);
        model.removePlayer(p2);

        model.addPlayer(p1);
        model.addPlayer(p2);

        model.setPlayerAsReadyToStart(p1);
        model.setPlayerAsReadyToStart(p2);

        assert model.getStatus() == GameStatus.FIRST_ROUND;

        model.chooseCardFromHand(p1, 0);
        model.chooseCardFromHand(p2, 1);

        model.placeStarterCard(p1, Orientation.FRONT);
        model.placeStarterCard(p2, Orientation.BACK);

        Corner[][] filledCorner_1 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner_1[i][j] = new Corner(new BoardCellCoordinate(i, j));
                filledCorner_1[i][j].setCornerResource(Resource.MUSHROOM);
            }
        }
        filledCorner_1[0][0].setCoordinate(Coordinate.NW);
        filledCorner_1[0][1].setCoordinate(Coordinate.NE);
        filledCorner_1[1][0].setCoordinate(Coordinate.SW);
        filledCorner_1[1][1].setCoordinate(Coordinate.SE);

        Corner[][] filledCorner_2 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner_2[i][j] = new Corner(new BoardCellCoordinate(i, j));
                filledCorner_2[i][j].setCornerResource(Resource.LEAF);
            }
        }
        filledCorner_2[0][0].setCoordinate(Coordinate.NW);
        filledCorner_2[0][1].setCoordinate(Coordinate.NE);
        filledCorner_2[1][0].setCoordinate(Coordinate.SW);
        filledCorner_2[1][1].setCoordinate(Coordinate.SE);

        Corner[][] filledCorner_3 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner_3[i][j] = new Corner(new BoardCellCoordinate(i, j));
                filledCorner_3[i][j].setCornerItem(Item.FEATHER);
            }
        }
        filledCorner_3[0][0].setCoordinate(Coordinate.NW);
        filledCorner_3[0][1].setCoordinate(Coordinate.NE);
        filledCorner_3[1][0].setCoordinate(Coordinate.SW);
        filledCorner_3[1][1].setCoordinate(Coordinate.SE);

        Corner[][] filledCorner_4 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner_4[i][j] = new Corner(new BoardCellCoordinate(i, j));
                filledCorner_4[i][j].setCornerItem(Item.POTION);
            }
        }
        filledCorner_4[0][0].setCoordinate(Coordinate.NW);
        filledCorner_4[0][1].setCoordinate(Coordinate.NE);
        filledCorner_4[1][0].setCoordinate(Coordinate.SW);
        filledCorner_4[1][1].setCoordinate(Coordinate.SE);

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.BLUE, 0, filledCorner_1);
        filledCorner_1[0][0].setReference_card(card1);
        filledCorner_1[0][1].setReference_card(card1);
        filledCorner_1[1][0].setReference_card(card1);
        filledCorner_1[1][1].setReference_card(card1);

        ResourceCard card2 = new ResourceCard(2, Orientation.BACK, Color.PURPLE, 0, filledCorner_2);
        filledCorner_2[0][0].setReference_card(card2);
        filledCorner_2[0][1].setReference_card(card2);
        filledCorner_2[1][0].setReference_card(card2);
        filledCorner_2[1][1].setReference_card(card2);

        GoldCard card3 = new GoldCard(3, Orientation.FRONT, Color.GREEN, 1, filledCorner_3);
        filledCorner_3[0][0].setReference_card(card3);
        filledCorner_3[0][1].setReference_card(card3);
        filledCorner_3[1][0].setReference_card(card3);
        filledCorner_3[1][1].setReference_card(card3);

        GoldCard card4 = new GoldCard(4, Orientation.BACK, Color.RED, 1, filledCorner_4);
        filledCorner_4[0][0].setReference_card(card4);
        filledCorner_4[0][1].setReference_card(card4);
        filledCorner_4[1][0].setReference_card(card4);
        filledCorner_4[1][1].setReference_card(card4);

        card3.setGoldCard(0, 0, 0, 0, false, false, false, false);
        card4.setGoldCard(0, 0, 0, 0, false, false, false, false);

        model.declareWinners();

        TUI tui = new TUI();

        ModelView model_view = new ModelView(model);

        tui.displayGameEnded(model_view);

        model.placeCard(card1, p1, 25, 25);
        // hit illegal starter
        model.placeCard(card1, p1, 25, 25);

        model.placeCard(card2, p1, 25, 26);
        model.placeCard(card3, p1, 26, 25);
        model.placeCard(card4, p1, 26, 26);

        model.placeCard(card1, p1, 24, 24);
        // hit illegal resource
        model.placeCard(card1, p1, 24, 24);

        model.placeCard(card2, p1, 23, 23);

        model.placeCard(card3, p1, 23, 23);
        // hit illegal gold
        model.placeCard(card3, p1, 23, 23);

        model.placeCard(card4, p1, 22, 22);

        model.setStatus(GameStatus.RUNNING);

        model.drawCard(p1, 1);
        model.drawCard(p1, 2);
        model.drawCard(p1, 3);
        model.drawCard(p1, 4);
        model.drawCard(p1, 5);
        model.drawCard(p1, 6);

        model.getPlayersConnected().getFirst().getPersonalBoard().setPoints(20);
        model.getPlayersConnected().getLast().getPersonalBoard().setPoints(20);

        model.drawCard(p1, 1);
        assert model.getStatus() == GameStatus.SECOND_LAST_ROUND || model.getStatus() == GameStatus.LAST_ROUND;
        model.drawCard(p2, 5);
        assert model.getStatus() == GameStatus.LAST_ROUND;

        model.declareWinners();

        model_view = new ModelView(model);

        tui.displayGameEnded(model_view);

        model.getPlayersConnected().getFirst().getPersonalBoard().setPoints(15);
        model.getPlayersConnected().getLast().getPersonalBoard().setPoints(20);

        model.declareWinners();

        model_view = new ModelView(model);

        tui.displayGameEnded(model_view);

        p1.getCardHandIds();


        // Test model view methods
        model_view.getObjectiveCards("Player1");
        model_view.getStarterCards("Player1");
        model_view.getChat();
        model_view.getLeaderBoard();
        model_view.getLastCoordinate();


    }

    @Test
    public void testDeclareWinners() {
        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");
        Player p3 = new Player("Player3");

        model.setNumPlayersToPlay(3);

        model.addPlayer(p1);
        model.addPlayer(p2);
        model.addPlayer(p3);

        model.setPlayerAsReadyToStart(p1);
        model.setPlayerAsReadyToStart(p2);
        model.setPlayerAsReadyToStart(p3);

        assert model.getStatus() == GameStatus.FIRST_ROUND;

        model.chooseCardFromHand(p1, 0);
        model.chooseCardFromHand(p2, 1);
        model.chooseCardFromHand(p3, 1);

        model.declareWinners();

        TUI tui = new TUI();
        ModelView model_view = new ModelView(model);
        tui.displayGameEnded(model_view);

        model.getPlayersConnected().get(0).getPersonalBoard().setPoints(20);
        model.getPlayersConnected().get(1).getPersonalBoard().setPoints(20);
        model.getPlayersConnected().get(2).getPersonalBoard().setPoints(19);

        model.declareWinners();

        model_view = new ModelView(model);
        tui.displayGameEnded(model_view);


        //Test model view methods

        model_view.getPlayerEntity("Player1");
        assertEquals(model_view.getPlayerEntity("Player1").getNickname(), "Player1");
        model_view.getAllPlayers();
        assertEquals(model_view.getAllPlayers().size(), 3);
        model_view.getStatus();
        model_view.getCurrentPlayerNickname();
        model_view.getGameId();
        assertEquals(model_view.getGameId(), -1);
        model_view.getCommonBoard();
        model_view.getPlayersConnected();
        assertEquals(model_view.getPlayersConnected().size(), 3);
        model_view.getLastChosenOrientation();
//        model_view.getLastChosenCard();
    }

    @Test
    public void testEndGame() throws GameEndedException {

        model.setNumPlayersToPlay(2);

        Player p1 = new Player("Player1");
        Player p2 = new Player("Player2");

        model.addPlayer(p1);
        model.addPlayer(p2);

        model.setPlayerAsReadyToStart(p1);
        model.setPlayerAsReadyToStart(p2);

        for (int i = 0; i < 40; i++) {
            model.drawCard(p1, 1);
            model.drawCard(p2, 4);
        }

        model.drawCard(p1, 1);
        model.drawCard(p1, 1);
        model.drawCard(p2, 4);

        assertEquals(model.getStatus(), GameStatus.LAST_ROUND);
    }

}
