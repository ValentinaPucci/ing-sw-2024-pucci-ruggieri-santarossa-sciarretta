package it.polimi.demo.model;

import it.polimi.demo.model.board.BoardCellCoordinate;
import it.polimi.demo.model.board.Corner;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.enumerations.Color;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.enumerations.Resource;
import it.polimi.demo.model.exceptions.GameEndedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelGeneralTest {

    private Model model = new Model();

    @BeforeEach
    public void setUp() {
        model = new Model();
        model.setNumPlayersToPlay(2);
    }

    @Test
    public void testGeneral() throws GameEndedException {

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

        model.setStatus(GameStatus.FIRST_ROUND);

        model.chooseCardFromHand(p1, 0);
        model.placeStarterCard(p1, Orientation.FRONT);

        model.chooseCardFromHand(p2, 0);
        model.placeStarterCard(p2, Orientation.BACK);

        // todo: you need to create those cards correctly setting the right coordinates!! see actual_corners in cardsCollection
        Corner[][] filledCorner = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner[i][j] = new Corner(new BoardCellCoordinate(i, j));
                filledCorner[i][j].setCornerResource(Resource.MUSHROOM);
            }
        }

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.BLUE, 0, filledCorner);
        ResourceCard card2 = new ResourceCard(2, Orientation.BACK, Color.PURPLE, 0, filledCorner);
        GoldCard card3 = new GoldCard(3, Orientation.FRONT, Color.GREEN, 1, filledCorner);
        GoldCard card4 = new GoldCard(4, Orientation.BACK, Color.RED, 1, filledCorner);
        card3.setGoldCard(0, 0, 0, 0, false, false, false, false);
        card4.setGoldCard(0, 0, 0, 0, false, false, false, false);

        model.placeCard(card1, p1, 250, 250);
        model.placeCard(card2, p1, 250, 251);
        model.placeCard(card3, p1, 251, 250);
        model.placeCard(card4, p1, 251, 251);

        // todo: check this error
//        model.placeCard(card1, p1, 249, 249);
        model.placeCard(card3, p1, 249, 249);
//        model.placeCard(card3, p1, 248, 248);
//        model.placeCard(card2, p1, 247, 237);

    }

}
