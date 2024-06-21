package it.polimi.demo.model;

import it.polimi.demo.Constants;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.polimi.demo.model.exceptions.*;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


public class ModelTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new Model();
        model.setNumPlayersToPlay(3);
    }


    //--------------------methods for player + connection/disconnection management-------------------

    @Test
    public void testGetAllPlayers() {
        assertEquals(0, model.getAllPlayers().size());
        // Add some players
        addPlayersToGameModel(3);
        assertEquals(3, model.getAllPlayers().size());
    }

    @Test
    public void testSetPlayerAsReadyToStart() {
        addPlayersToGameModel(3);
        Player player = model.getAllPlayers().get(0);
        assertFalse(player.getReadyToStart());
        model.setPlayerAsReadyToStart(player);
        assertTrue(player.getReadyToStart());
    }

    @Test
    public void testArePlayersReadyToStartAndEnough() {
        assertFalse(model.arePlayersReadyToStartAndEnough());
        // Add enough players to start the game
        addPlayersToGameModel(3);
        assertFalse(model.arePlayersReadyToStartAndEnough());
        // set all players as ready
        model.getAllPlayers().forEach(Player::setAsReadyToStart);
        System.out.println(model.getAllPlayers().get(0).getReadyToStart());
        System.out.println(model.getAllPlayers().get(1).getReadyToStart());
        System.out.println(model.getAllPlayers().get(2).getReadyToStart());
        assertTrue(model.arePlayersReadyToStartAndEnough());
    }

    @Test
    public void testGetIdentityOfPlayer() {
        assertNull(model.getIdentityOfPlayer("Player1"));
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        model.addPlayer(player1);
        model.addPlayer(player2);
        assertEquals(model.getAllPlayers().getFirst(), model.getIdentityOfPlayer("Player1"));
        assertEquals(model.getAllPlayers().get(1), model.getIdentityOfPlayer("Player2"));
        assertNull(model.getIdentityOfPlayer("UnknownPlayer"));
    }

    @Test
    public void testAddPlayer() {
        assertEquals(0, model.getAllPlayers().size());
        // Add a player
        Player player = new Player("Player1");
        model.addPlayer(player);
        assertEquals(1, model.getAllPlayers().size());
        assertTrue(model.getAllPlayers().contains(player));
        // Attempt to offer the same player again
        // assertThrows(PlayerAlreadyConnectedException.class, () -> model.addPlayer(player));
        //Add other players
        Player player2 = new Player("Player2");
        model.addPlayer(player2);
        assertEquals(2, model.getAllPlayers().size());
        assertTrue(model.getAllPlayers().contains(player2));

        Player player3 = new Player("Player3");
        model.addPlayer(player3);
        assertEquals(3, model.getAllPlayers().size());
        assertTrue(model.getAllPlayers().contains(player3));
        //assertThrows(PlayerAlreadyConnectedException.class, () -> model.addPlayer(player3));

        System.out.println(model.getNumPlayersToPlay());

        Player player4 = new Player("Player4");
        //assertThrows(MaxPlayersLimitException.class,()-> model.addPlayer(player4));

        assertEquals(3, model.getAllPlayers().size());
        System.out.println(model.getAllPlayers().getFirst().getNickname());
        System.out.println(model.getAllPlayers().get(1).getNickname());
        System.out.println(model.getAllPlayers().get(2).getNickname());


    }

    @Test
    public void testRemovePlayer() {
        addPlayersToGameModel(Constants.minNumOfPlayer);
        Player playerToRemove = model.getAllPlayers().getFirst();
        assertEquals(Constants.minNumOfPlayer, model.getAllPlayers().size());
        model.removePlayer(playerToRemove);
        assertFalse(model.getAllPlayers().contains(playerToRemove));
        assertEquals(Constants.minNumOfPlayer - 1, model.getAllPlayers().size());
    }

    // Utility method to offer a specific number of players to the game model
    private void addPlayersToGameModel(int numPlayers) {
        for (int i = 0; i < numPlayers; i++) {
            model.addPlayer(new Player("Player" + i));
        }
    }


//    @Test
//    public void testReconnectPlayer() {
//        Player player1 = new Player("Player1");
//        gameModel.addPlayer(player1);
//        gameModel.getBeginnerPlayer().setAsConnected();
//        gameModel.setPlayerAsConnected(gameModel.getBeginnerPlayer());
//
//        // Verify that a player can't reconnect if he is already connected
//        assertThrows(PlayerAlreadyConnectedException.class, () -> gameModel.reconnectPlayer(gameModel.getBeginnerPlayer()));
//
//        // Disconnect player1
//        gameModel.setPlayerAsDisconnected(gameModel.getBeginnerPlayer());
//
//        // Verify that the player can reconnect after being disconnected
//        assertDoesNotThrow(() -> gameModel.reconnectPlayer(gameModel.getBeginnerPlayer()));
//        assertTrue(gameModel.getBeginnerPlayer().getIsConnected());
//
//    }

//    @Test
//    public void testConnectPlayerInOrder() {
//        Player player1 = new Player("Player1");
//        Player player2 = new Player("Player2");
//        Player player3 = new Player("Player3");
//        Player player4 = new Player("Player4");
//
//
//        gameModel.addPlayer(player1);
//        gameModel.addPlayer(player2);
//        gameModel.addPlayer(player3);
//
//        gameModel.getAllPlayers().getFirst().setAsConnected();
//        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().getFirst());
//        gameModel.getAllPlayers().get(1).setAsConnected();
//        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().get(1));
//        gameModel.getAllPlayers().get(2).setAsConnected();
//        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().get(2));
//
//        assertEquals(gameModel.getAllPlayers().get(1), gameModel.getPlayersConnected().get(1));
//        assertEquals(gameModel.getAllPlayers().get(2), gameModel.getPlayersConnected().get(2));
//        assertEquals(gameModel.getAllPlayers().get(0), gameModel.getPlayersConnected().getFirst());
//
//        assertTrue(gameModel.getAllPlayers().get(1).getIsConnected());
//        assertTrue(gameModel.getPlayersConnected().contains(gameModel.getAllPlayers().get(1)));
//
//        //disconnect a player in the middle of players_connected
//        gameModel.setPlayerAsDisconnected(gameModel.getAllPlayers().get(1));
//        assertFalse(gameModel.getPlayersConnected().contains(gameModel.getAllPlayers().get(1)));
//
//
//        assertThrows(IllegalArgumentException.class, () -> gameModel.connectPlayerInOrder(player4));
//        assertEquals(0, gameModel.getAllPlayers().indexOf(gameModel.getAllPlayers().get(1)) - 1);
//        assertEquals(0, gameModel.getAllPlayers().indexOf(gameModel.getAllPlayers().get(0)));
//
//        assertEquals(gameModel.getAllPlayers().get(2), gameModel.getPlayersConnected().get(1));
//        assertEquals(gameModel.getAllPlayers().get(0), gameModel.getPlayersConnected().getFirst());
//        gameModel.connectPlayerInOrder(gameModel.getAllPlayers().get(1));
//
//
//        // Verify that players were added correctly to players_connected list
//        assertTrue(gameModel.getPlayersConnected().contains(gameModel.getAllPlayers().get(0)));
//        assertTrue(gameModel.getPlayersConnected().contains(gameModel.getAllPlayers().get(1)));
//        assertTrue(gameModel.getPlayersConnected().contains(gameModel.getAllPlayers().get(2)));
//
//        // Verify that the players were added in correct order
//        assertEquals(gameModel.getAllPlayers().get(1), gameModel.getPlayersConnected().get(1));
//        assertEquals(gameModel.getAllPlayers().get(0), gameModel.getPlayersConnected().get(0));
//        assertEquals(gameModel.getAllPlayers().get(2), gameModel.getPlayersConnected().get(2));
//
//        //disconnect the firstPlayer
//        gameModel.setPlayerAsDisconnected(gameModel.getAllPlayers().get(0));
//        assertFalse(gameModel.getPlayersConnected().contains(gameModel.getAllPlayers().get(0)));
//        gameModel.connectPlayerInOrder(gameModel.getAllPlayers().get(0));
//
//        assertEquals(gameModel.getAllPlayers().get(1), gameModel.getPlayersConnected().get(0));
//        assertEquals(gameModel.getAllPlayers().get(2), gameModel.getPlayersConnected().get(1));
//        assertEquals(gameModel.getAllPlayers().get(0), gameModel.getPlayersConnected().get(2));
//    }
//
//    //------------------------game logic management------------------------
//    @Test
//    public void testDealCards() {
//        addPlayersToGameModel(3);
//
//        gameModel.getCommonBoard().setPlayerCount(gameModel.getAllPlayers().size());
//        assertEquals(3, gameModel.getAllPlayers().size());
//        gameModel.getCommonBoard().initializeBoard();
//
//
//        //verify that common_board is initialized correctly
//        assertTrue(gameModel.getCommonBoard().getBoardNodes()[0].isPlayerPresent(0));
//        assertNotNull(gameModel.getCommonBoard().getDecks().getFirst());
//        assertNotNull(gameModel.getCommonBoard().getDecks().get(1));
//        assertNotNull(gameModel.getCommonBoard().getDecks().get(2));
//
//        assertNotNull(gameModel.getResourceDeck());
//        assertNotNull(gameModel.getGoldDeck());
//        assertNotNull(gameModel.getObjectiveDeck());
//        assertNotNull(gameModel.getStarterDeck());
//
//
//        //assertNotNull(gameModel.getCommonBoard().drawFromConcreteDeck(0));
//        assertEquals(14, gameModel.getCommonBoard().getObjectiveConcreteDeck().size());
//
//        gameModel.dealCards();
//
//
//        // Verify that all players has received the correct cards
//
//        for (int i = 0; i < gameModel.getAllPlayers().size(); i++) {
//            Player player = gameModel.getAllPlayers().get(i);
//            {
//                assertNull(player.getStarterCard());
//                assertEquals(3, player.getCardHand().size());
//                assertEquals(2, player.getSecretObjectiveCards().size());
//            }
//        }
//
//
//        //Verify that the common decks have been correctly reduced of cards
//        assertEquals(32, gameModel.getCommonBoard().getResourceConcreteDeck().size());
//        assertEquals(35, gameModel.getCommonBoard().getGoldConcreteDeck().size());
//
//        assertEquals(8, gameModel.getCommonBoard().getObjectiveConcreteDeck().size());
//
//        assertEquals(6, gameModel.getCommonBoard().getStarterConcreteDeck().size());
//
//        assertEquals(gameModel.getAllPlayers().getFirst().getStarterCardToChose().get(0).getId(), gameModel.getAllPlayers().getFirst().getStarterCardToChose().get(0).getId());
//        assertEquals(gameModel.getAllPlayers().get(1).getStarterCardToChose().get(0).getId(), gameModel.getAllPlayers().get(1).getStarterCardToChose().get(0).getId());
//        assertEquals(gameModel.getAllPlayers().get(2).getStarterCardToChose().get(0).getId(), gameModel.getAllPlayers().get(2).getStarterCardToChose().get(0).getId());
//
//        GoldCard gold = gameModel.getCommonBoard().getGoldConcreteDeck().popGoldCard();
//        System.out.println(gold.getId());
//        System.out.println(gold.getButterflyRequired());
//        System.out.println(gold.getLeafRequired());
//        System.out.println(gold.getMushroomRequired());
//        System.out.println(gold.getWolfRequired());
//        System.out.println(gold.getPoints());
//        System.out.println(gold.getCornerAtNE());
//        System.out.println(gold.getCornerAtNW());
//        System.out.println(gold.getCornerAtSW());
//        System.out.println(gold.getCornerAtSE());
//
//        GoldCard gold1 = gameModel.getCommonBoard().getGoldConcreteDeck().popGoldCard();
//        System.out.println(gold1.getId());
//        System.out.println(gold1.getButterflyRequired());
//        System.out.println(gold1.getLeafRequired());
//        System.out.println(gold1.getMushroomRequired());
//        System.out.println(gold1.getWolfRequired());
//        System.out.println(gold1.getPoints());
//        System.out.println(gold1.getCornerAtNE());
//        System.out.println(gold1.getCornerAtNW());
//        System.out.println(gold1.getCornerAtSW());
//        System.out.println(gold1.getCornerAtSW().resource);
//        System.out.println(gold1.getCornerAtSW().item);
//        System.out.println(gold1.getCornerAtSE());
//
//
//        ResourceCard res = gameModel.getCommonBoard().getResourceConcreteDeck().popResourceCard();
//        System.out.println(res.getId());
//        System.out.println(res.getPoints());
//        System.out.println(res.getCornerAtSW());
//        System.out.println(res.getCornerAtNE());
//        System.out.println(res.getCornerAtSE());
//        System.out.println(res.getCornerAtNW());
//
//        ResourceCard res1 = gameModel.getCommonBoard().getResourceConcreteDeck().popResourceCard();
//        System.out.println(res1.getId());
//        System.out.println(res1.getPoints());
//        System.out.println(res1.getCornerAtSW().resource);
//        System.out.println(res1.getCornerAtNE());
//        System.out.println(res1.getCornerAtSE());
//        System.out.println(res1.getCornerAtNW());
//
//    }

    @Test
    public void testPlaceResourceCard() throws GameEndedException {
        //initialize game
        addPlayersToGameModel(3);
        model.getCommonBoard().setPlayerCount(model.getAllPlayers().size());
        model.getCommonBoard().initializeBoard();
        model.dealCards();
        //set the starter card for each player
        model.getAllPlayers().getFirst().setStarterCard(model.getAllPlayers().getFirst().getStarterCardToChose().get(0));
        model.getAllPlayers().get(1).setStarterCard(model.getAllPlayers().get(1).getStarterCardToChose().get(0));
        model.getAllPlayers().get(2).setStarterCard(model.getAllPlayers().get(2).getStarterCardToChose().get(0));

        //System.out.println(gameModel.getAllPlayers().getFirst().getStarterCardToChose()[0]);
        //System.out.println(gameModel.getAllPlayers().getFirst().getStarterCardToChose()[1]);

        //set the objective card for each player
        model.getAllPlayers().getFirst().setChosenObjectiveCard(model.getAllPlayers().getFirst().getSecretObjectiveCards().get(0));
        model.getAllPlayers().get(1).setChosenObjectiveCard(model.getAllPlayers().get(1).getSecretObjectiveCards().get(0));
        model.getAllPlayers().get(2).setChosenObjectiveCard(model.getAllPlayers().get(2).getSecretObjectiveCards().get(0));

        System.out.println(model.getAllPlayers().getFirst().getSecretObjectiveCards().get(0));
//
//        for (Player player : gameModel.getAllPlayers()) {
//            player.playStarterCard();
//            //player.getPersonalBoard().bruteForcePlaceCardSE(gameModel.getAllPlayers().getFirst().getHand().getFirst(), 500,500);
//            //System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().;
//        }

        //Check resource counter for points ERROR
        //assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_wolves());//-1
        //assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_mushrooms());
        //assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_leaves());

//
//
//
//         //Test for placement of resources cards
//        assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][501].getLevel());
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0));
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0).points);
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1));
//
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtNW()); //butterfly
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtNW().item);//Optional.empty
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtNE()); //leaf
//        //System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtSW()); //problem
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtSE().item); //problem
//
//
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0).getCornerAtNE().is_visible);
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0).getCornerAtNE().resource); //null (not present)
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().getFirst().getCornerAtNW()); //Butterfly
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSW().resource); //Optional.empty
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSW().item); //Optional.empty
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSW().is_visible);//true
//
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSE().resource); //Optional.empty
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSE().item); //Optional.empty
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSE().is_visible);//true
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtNE().getCoordinate());
//
//
//        gameModel.placeCard(gameModel.getAllPlayers().getFirst().getHand().get(1), gameModel.getAllPlayers().getFirst(), 501, 501);
//        //assertEquals(2,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][501].getLevel()); //problem
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().size());


        System.out.println(model.getAllPlayers().get(1).getHand().get(2));
        System.out.println(model.getAllPlayers().get(1).getHand().get(2).getCornerAtSE());
        System.out.println(model.getAllPlayers().get(1).getHand().get(2).getCornerAtSW());
        System.out.println(model.getAllPlayers().get(1).getHand().get(2).getCornerAtNE());
        System.out.println(model.getAllPlayers().get(1).getHand().get(2).getCornerAtNW().item);
        System.out.println(model.getAllPlayers().get(1).getHand().get(2).getPoints());
        System.out.println(model.getAllPlayers().get(1).getHand().get(2).type);

        System.out.println(model.getAllPlayers().get(2).getHand().get(2));
        System.out.println(model.getAllPlayers().get(2).getHand().get(2).getCornerAtSE());
        System.out.println(model.getAllPlayers().get(2).getHand().get(2).getCornerAtSW());
        System.out.println(model.getAllPlayers().get(2).getHand().get(2).getCornerAtNE().is_visible);
        System.out.println(model.getAllPlayers().get(2).getHand().get(2).getCornerAtNW().item);
        System.out.println(model.getAllPlayers().get(2).getHand().get(2).points);
        System.out.println(model.getAllPlayers().get(2).getHand().get(2).type);

        GoldCard card = (GoldCard) model.getAllPlayers().get(2).getHand().get(2);
        System.out.println(card.getMushroomRequired());
        System.out.println(card.getButterflyRequired());

//
//
//        System.out.println(gameModel.getAllPlayers().getFirst().getStarterCard());
//        System.out.println(gameModel.getAllPlayers().getFirst().getStarterCard().getCornerAtNW().is_visible); //true
//        assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getLevel());
//        System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getCornerFromCell().is_visible); //true
//        System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getCornerFromCell().getBoard_coordinate().getX());
//        assertNotNull(gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getCornerFromCell());
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1));
//
//        System.out.println(gameModel.getAllPlayers().getFirst().getStarterCard().getCornerAtNW().resource);
//        System.out.println(gameModel.getAllPlayers().getFirst().getStarterCard().getCornerAtNE().resource);
//        System.out.println(gameModel.getAllPlayers().getFirst().getStarterCard().getCornerAtSW().resource);
//        System.out.println(gameModel.getAllPlayers().getFirst().getStarterCard().getCornerAtSE().resource);
//
//        assertEquals(1, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_butterflies());
//
//        //Resource Card on starterCard
//        gameModel.placeCard(gameModel.getAllPlayers().getFirst().getHand().get(1), gameModel.getAllPlayers().getFirst(), 501, 501);
//        assertEquals(2,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][501].getLevel());
//        assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][502].getLevel());
//
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtNW().resource);
//        assertEquals(0,gameModel.getAllPlayers().getFirst().getPersonalBoard().getPoints());
//
//
//
//        //assertEquals(1, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_butterflies()); //PROBLEM
//        //System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getCornerFromCell().resource);
//
//        assertEquals(2, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_leaves());
//        assertEquals(1, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_potions());
//        assertEquals(1, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_wolves());
//        assertEquals(1, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_butterflies());
//        assertEquals(1, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_mushrooms());
//
//
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0));
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0).getCornerAtNE().getCoordinate());
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0).getCornerAtSE().getCoordinate());
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtNE());
//        System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][502].getCornerFromCell().getCoordinate());
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtNE().getCoordinate());
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().size());
//        gameModel.getAllPlayers().getFirst().removeFromHand(gameModel.getAllPlayers().getFirst().getHand().get(1));
//        assertEquals(2,gameModel.getAllPlayers().getFirst().getHand().size());
//
//        gameModel.setStatus(GameStatus.RUNNING);
//
//        gameModel.drawCard(gameModel.getAllPlayers().getFirst(), 6);
//        assertEquals(3,gameModel.getAllPlayers().getFirst().getHand().size());
//
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0));
//
//        gameModel.placeCard(gameModel.getAllPlayers().getFirst().getHand().get(0), gameModel.getAllPlayers().getFirst(), 501, 502);
//        assertEquals(2,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][502].getLevel());
//        assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[502][502].getLevel());
//        assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getPoints());
//        gameModel.getAllPlayers().getFirst().removeFromHand(gameModel.getAllPlayers().getFirst().getHand().get(0));
//        assertEquals(2,gameModel.getAllPlayers().getFirst().getHand().size());
//
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0));
//        assertEquals(2, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_butterflies());
//
//
//        //goldCard on ResourceCard
//        //gameModel.placeCard((GoldCard) gameModel.getAllPlayers().getFirst().getHand().get(0), gameModel.getAllPlayers().getFirst(), 502, 501);
//
//        //Controllare sta eccezione
//        //assertThrows(IllegalMoveException.class, ()-> gameModel.placeCard((GoldCard) gameModel.getAllPlayers().getFirst().getHand().get(0), gameModel.getAllPlayers().getFirst(), 502, 501));
//        //assertEquals(2,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[502][501].getLevel());
//
//        gameModel.getAllPlayers().getFirst().removeFromHand(gameModel.getAllPlayers().getFirst().getHand().get(0));
//        assertEquals(1,gameModel.getAllPlayers().getFirst().getHand().size());
//
//        gameModel.drawCard(gameModel.getAllPlayers().getFirst(), 6);
//        assertEquals(2,gameModel.getAllPlayers().getFirst().getHand().size());
//
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0));
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1));
//
//        //GoldCard on ResourceCard
//        //gameModel.placeCard((GoldCard) gameModel.getAllPlayers().getFirst().getHand().get(1), gameModel.getAllPlayers().getFirst(), 501, 503);
//        //assertEquals(2,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][503].getLevel());
//
//        //GoldCard on GoldCard
//        //Controllare sta eccezione
//        //assertThrows(IllegalMoveException.class, ()-> gameModel.placeCard((GoldCard) gameModel.getAllPlayers().getFirst().getHand().get(0), gameModel.getAllPlayers().getFirst(), 501, 504));
//
//        gameModel.drawCard(gameModel.getAllPlayers().getFirst(), 4);
//        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(2));
//
//        //GoldCard on StarterCard
//        gameModel.placeCard((GoldCard) gameModel.getAllPlayers().getFirst().getHand().get(2), gameModel.getAllPlayers().getFirst(), 500, 500);
//        //assertThrows(IllegalMoveException.class, ()-> gameModel.placeCard((GoldCard) gameModel.getAllPlayers().getFirst().getHand().get(2), gameModel.getAllPlayers().getFirst(), 500, 500));
//        assertEquals(2,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getLevel());
//        System.out.println("*********");
//        //assertEquals(3, gameModel.getAllPlayers().getFirst().getPersonalBoard().getPoints());
//        System.out.println(((GoldCard)gameModel.getAllPlayers().getFirst().getHand().get(2)).getIsPotionRequired());
//
//        assertEquals(1, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_leaves());
//        assertEquals(2, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_potions());
//        assertEquals(1, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_wolves());
//        assertEquals(2, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_butterflies());
//        assertEquals(0, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_mushrooms());
//        assertEquals(0, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_parchments());
//
//        System.out.println(gameModel.getCommonBoard().getCommonObjectives());
//
//        //gameModel.calculateFinalScores();
//        gameModel.declareWinners();
//        System.out.println(gameModel.getAllPlayers().getFirst().getFinalScore());
//        System.out.println(gameModel.getWinners().getFirst().getNickname());
//        assertEquals(3,gameModel.getAllPlayers().getFirst().getPersonalBoard().getPoints());
//        assertEquals(0,gameModel.getCommonBoard().getCommonObjectives().getFirst().calculateScore(gameModel.getAllPlayers().getFirst().getPersonalBoard()));
//        assertEquals(2,gameModel.getCommonBoard().getCommonObjectives().get(1).calculateScore(gameModel.getAllPlayers().getFirst().getPersonalBoard()));
//        assertEquals(0,gameModel.getAllPlayers().getFirst().getChosenObjectiveCard().calculateScore(gameModel.getAllPlayers().getFirst().getPersonalBoard()));

//
//
//
//        //Test for drawCard
//        gameModel.drawCard(gameModel.getAllPlayers().getFirst(), 1);
//        assertEquals(4,gameModel.getAllPlayers().getFirst().getHand().size());
//        gameModel.drawCard(gameModel.getAllPlayers().getFirst(), 2);
//        assertEquals(5,gameModel.getAllPlayers().getFirst().getHand().size());
//        gameModel.drawCard(gameModel.getAllPlayers().getFirst(), 3);
//        assertEquals(6,gameModel.getAllPlayers().getFirst().getHand().size());
//        gameModel.drawCard(gameModel.getAllPlayers().getFirst(), 4);
//        assertEquals(7,gameModel.getAllPlayers().getFirst().getHand().size());
//        gameModel.drawCard(gameModel.getAllPlayers().getFirst(), 5);
//        assertEquals(8,gameModel.getAllPlayers().getFirst().getHand().size());
//
//        //test for nextTurn
//        gameModel.getAllPlayers().getFirst().setAsConnected();
//        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().getFirst());
//        gameModel.getAllPlayers().get(1).setAsConnected();
//        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().get(1));
//        gameModel.getAllPlayers().get(2).setAsConnected();
//        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().get(2));
//
//
//        System.out.println(gameModel.getPlayersConnected().getFirst().getNickname());
//        System.out.println(gameModel.getPlayersConnected().get(1).getNickname());
//        System.out.println(gameModel.getPlayersConnected().get(2).getNickname());
//
//        System.out.println(gameModel.getStatus());
//
//        gameModel.setStatus(GameStatus.RUNNING);
//
//
//        gameModel.nextTurn();
//        System.out.println(gameModel.getPlayersConnected().getFirst().getNickname());
//        System.out.println(gameModel.getPlayersConnected().get(1).getNickname());
//        System.out.println(gameModel.getPlayersConnected().get(2).getNickname());
//
//        gameModel.setStatus(GameStatus.WAIT);
//        assertThrows(GameNotStartedException.class, ()-> gameModel.nextTurn());
//
//        gameModel.setStatus(GameStatus.ENDED);
//        assertThrows(GameEndedException.class, ()-> gameModel.nextTurn());
//
//
//
//        System.out.println("*********");
//        //Test for declareWinners
//        System.out.println(gameModel.getWinners().getFirst().getNickname());



    }

    @Test
    public void testObjectiveCard(){
        addPlayersToGameModel(3);
        model.getCommonBoard().setPlayerCount(model.getAllPlayers().size());
        model.getCommonBoard().initializeBoard();
        model.dealCards();




    }







    //--------------------------managing status --------------------------------------

//    @Test
//    public void testSetStatus(){
//
//        //gameModel.addPlayer("Player1");
//        //gameModel.addPlayer("Player2");
//        //gameModel.addPlayer("Player3");
//        gameModel.getAllPlayers().getFirst().setAsConnected();
//        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().getFirst());
//        gameModel.getAllPlayers().get(1).setAsConnected();
//        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().get(1));
//        gameModel.getAllPlayers().get(2).setAsConnected();
//        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().get(2));
//
//        gameModel.setStatus(GameStatus.RUNNING);
//        GameStatus expectedStatus = GameStatus.RUNNING;
//        assertEquals(expectedStatus ,gameModel.getStatus());
//
//    }



}
