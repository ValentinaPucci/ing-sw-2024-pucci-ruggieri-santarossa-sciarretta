package it.polimi.demo.model;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.exceptions.*;
import it.polimi.demo.model.Player;

import java.rmi.Remote;
import java.util.NoSuchElementException;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class GameModelTest {

    private GameModel gameModel;

    @BeforeEach
    public void setUp() {
        gameModel = new GameModel();
        gameModel.setNumPlayersToPlay(3);
    }


    //--------------------methods for player + connection/disconnection management-------------------
    @Test
    public void testGetBeginnerPlayer() {
        assertThrows(NoSuchElementException.class, ()->gameModel.getBeginnerPlayer());
        addPlayersToGameModel(DefaultValues.MinNumOfPlayer);
        assertNotNull(gameModel.getBeginnerPlayer());
    }

    @Test
    public void testGetAllPlayers() {
        assertEquals(0, gameModel.getAllPlayers().size());
        // Add some players
        addPlayersToGameModel(3);
        assertEquals(3, gameModel.getAllPlayers().size());
    }

    @Test
    public void testSetPlayerAsReadyToStart() {
        addPlayersToGameModel(3);
        Player player = gameModel.getAllPlayers().get(0);
        assertFalse(player.getReadyToStart());
        gameModel.setPlayerAsReadyToStart(player);
        assertTrue(player.getReadyToStart());
    }

    @Test
    public void testArePlayersReadyToStartAndEnough() {
        assertFalse(gameModel.arePlayersReadyToStartAndEnough());
        // Add enough players to start the game
        addPlayersToGameModel(3);
        assertFalse(gameModel.arePlayersReadyToStartAndEnough());
        // set all players as ready
        gameModel.getAllPlayers().forEach(Player::setAsReadyToStart);
        System.out.println(gameModel.getAllPlayers().get(0).getReadyToStart());
        System.out.println(gameModel.getAllPlayers().get(1).getReadyToStart());
        System.out.println(gameModel.getAllPlayers().get(2).getReadyToStart());
        assertTrue(gameModel.arePlayersReadyToStartAndEnough());
    }

    @Test
    public void testGetPlayerEntity() {
        assertNull(gameModel.getPlayerEntity("Player1"));
        gameModel.addPlayer("Player1");
        gameModel.addPlayer("Player2");
        assertEquals(gameModel.getAllPlayers().getFirst(), gameModel.getPlayerEntity("Player1"));
        assertEquals(gameModel.getAllPlayers().get(1), gameModel.getPlayerEntity("Player2"));
        assertNull(gameModel.getPlayerEntity("UnknownPlayer"));
    }

    @Test
    public void testAddPlayer() {/*
        assertEquals(0, gameModel.getAllPlayers().size());
        // Add a player
        Player player = new Player("Player1");
        gameModel.addPlayer(player);
        assertEquals(1, gameModel.getAllPlayers().size());
        assertTrue(gameModel.getAllPlayers().contains(player));
        // Attempt to add the same player again
        assertThrows(PlayerAlreadyConnectedException.class, () -> gameModel.addPlayer(player));
        //Add other players
        Player player2 = new Player("Player2");
        gameModel.addPlayer(player2);
        assertEquals(2, gameModel.getAllPlayers().size());
        assertTrue(gameModel.getAllPlayers().contains(player2));

        Player player3 = new Player("Player3");
        gameModel.addPlayer(player3);
        assertEquals(3, gameModel.getAllPlayers().size());
        assertTrue(gameModel.getAllPlayers().contains(player3));
        assertThrows(PlayerAlreadyConnectedException.class, () -> gameModel.addPlayer(player3));

        Player player4 = new Player("Player4");
        gameModel.addPlayer(player4);*/
        addPlayersToGameModel(3);
        assertEquals(3, gameModel.getAllPlayers().size());
        System.out.println(gameModel.getAllPlayers().getFirst().getNickname());
        System.out.println(gameModel.getAllPlayers().get(1).getNickname());
        System.out.println(gameModel.getAllPlayers().get(2).getNickname());

        //Attempt to add more players than the maximum limit
        //assertThrows(MaxPlayersLimitException.class, () -> gameModel.addPlayer(new Player("player5")));

    }

    @Test
    public void testRemovePlayer() {
        addPlayersToGameModel(DefaultValues.MinNumOfPlayer);
        Player playerToRemove = gameModel.getAllPlayers().getFirst();
        assertEquals(DefaultValues.MinNumOfPlayer, gameModel.getAllPlayers().size());
        gameModel.removePlayer(playerToRemove);
        assertFalse(gameModel.getAllPlayers().contains(playerToRemove));
        assertEquals(DefaultValues.MinNumOfPlayer - 1, gameModel.getAllPlayers().size());
    }

    // Utility method to add a specific number of players to the game model
    private void addPlayersToGameModel(int numPlayers) {
        for (int i = 0; i < numPlayers; i++) {
            gameModel.addPlayer("Player" + i);
        }
    }
    @Test
    public void testSetPlayerAsConnected() {

        assertFalse(gameModel.areConnectedPlayersEnough());
        gameModel.addPlayer("Player1");
        gameModel.addPlayer("Player2");
        assertEquals(2, gameModel.getAllPlayers().size());

        //assertThrows(IllegalArgumentException.class, () -> gameModel.setPlayerAsConnected(gameModel.getAllPlayers().getFirst()));
        //assertThrows(IllegalArgumentException.class, () -> gameModel.setPlayerAsConnected(gameModel.getAllPlayers().get(1)));

        gameModel.getAllPlayers().getFirst().setAsConnected();
        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().getFirst());
        System.out.println(gameModel.getAllPlayers().getFirst().getIsConnected());

        assertEquals(1, gameModel.getPlayersConnected().size());
        assertNotEquals(2, gameModel.getPlayersConnected().size());

        assertTrue(gameModel.isTheCurrentPlayerConnected());
        assertFalse(gameModel.areConnectedPlayersEnough());
        gameModel.getAllPlayers().get(1).setAsConnected();
        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().get(1));
        assertTrue(gameModel.areConnectedPlayersEnough());
    }

    @Test
    public void testSetPlayerAsDisconnected() {

        // add and connect a player to the game
        gameModel.addPlayer("Player1");
        System.out.println(gameModel.getAllPlayers().size());
        gameModel.getAllPlayers().getFirst().setAsConnected();
        System.out.println((gameModel.getAllPlayers().getFirst().getIsConnected()));
        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().getFirst());

        // Verify that the player1 is connected
        assertTrue(gameModel.getAllPlayers().getFirst().getIsConnected());
        // Disconnect player1
        gameModel.setPlayerAsDisconnected(gameModel.getAllPlayers().getFirst());
        //Verify that the player1 is disconnect and not ready to start
        assertFalse(gameModel.getAllPlayers().getFirst().getIsConnected());
        assertFalse(gameModel.getAllPlayers().getFirst().getReadyToStart());
        //Verify that the player1 is not in players_connected list

        assertFalse(gameModel.getPlayersConnected().contains(gameModel.getAllPlayers().getFirst()));

        // Verifica che il metodo notify_playerDisconnected sia stato chiamato con i parametri corretti
        //verify(listener_handler).notify_playerDisconnected(gameModel, player1.getNickname());

        // Add and connect player2
        gameModel.addPlayer("Player2");
        gameModel.getAllPlayers().get(1).setAsConnected();
        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().get(1));


        // Disconnect player2
        gameModel.setPlayerAsDisconnected(gameModel.getAllPlayers().get(1));
        assertFalse(gameModel.getAllPlayers().get(1).getIsConnected());

        // Verifica che il metodo notify_onlyOnePlayerConnected sia stato chiamato
        //verify(listener_handler).notify_onlyOnePlayerConnected(gameModel, DefaultValues.secondsToWaitReconnection); */
    }

    @Test
    public void testReconnectPlayer() {
        gameModel.addPlayer("Player1");
        gameModel.getBeginnerPlayer().setAsConnected();
        gameModel.setPlayerAsConnected(gameModel.getBeginnerPlayer());

        // Verify that a player can't reconnect if he is already connected
        assertThrows(PlayerAlreadyConnectedException.class, () -> gameModel.reconnectPlayer(gameModel.getBeginnerPlayer()));

        // Disconnect player1
        gameModel.setPlayerAsDisconnected(gameModel.getBeginnerPlayer());

        // Verify that the player can reconnect after being disconnected
        assertDoesNotThrow(() -> gameModel.reconnectPlayer(gameModel.getBeginnerPlayer()));
        assertTrue(gameModel.getBeginnerPlayer().getIsConnected());

    }

    @Test
    public void testConnectPlayerInOrder() {
        //Player player1 = new Player("Player1");
        //Player player2 = new Player("Player2");
        //Player player3 = new Player("Player3");
        Player player4 = new Player("Player4");


        gameModel.addPlayer("Player1");
        gameModel.addPlayer("Player2");
        gameModel.addPlayer("Player3");

        gameModel.getAllPlayers().getFirst().setAsConnected();
        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().getFirst());
        gameModel.getAllPlayers().get(1).setAsConnected();
        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().get(1));
        gameModel.getAllPlayers().get(2).setAsConnected();
        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().get(2));

        assertEquals(gameModel.getAllPlayers().get(1), gameModel.getPlayersConnected().get(1));
        assertEquals(gameModel.getAllPlayers().get(2), gameModel.getPlayersConnected().get(2));
        assertEquals(gameModel.getAllPlayers().get(0), gameModel.getPlayersConnected().getFirst());

        assertTrue(gameModel.getAllPlayers().get(1).getIsConnected());
        assertTrue(gameModel.getPlayersConnected().contains(gameModel.getAllPlayers().get(1)));

        //disconnect a player in the middle of players_connected
        gameModel.setPlayerAsDisconnected(gameModel.getAllPlayers().get(1));
        assertFalse(gameModel.getPlayersConnected().contains(gameModel.getAllPlayers().get(1)));


        assertThrows(IllegalArgumentException.class, () -> gameModel.connectPlayerInOrder(player4));
        assertEquals(0, gameModel.getAllPlayers().indexOf(gameModel.getAllPlayers().get(1)) - 1);
        assertEquals(0, gameModel.getAllPlayers().indexOf(gameModel.getAllPlayers().get(0)));

        assertEquals(gameModel.getAllPlayers().get(2), gameModel.getPlayersConnected().get(1));
        assertEquals(gameModel.getAllPlayers().get(0), gameModel.getPlayersConnected().getFirst());
        gameModel.connectPlayerInOrder(gameModel.getAllPlayers().get(1));


        // Verify that players were added correctly to players_connected list
        assertTrue(gameModel.getPlayersConnected().contains(gameModel.getAllPlayers().get(0)));
        assertTrue(gameModel.getPlayersConnected().contains(gameModel.getAllPlayers().get(1)));
        assertTrue(gameModel.getPlayersConnected().contains(gameModel.getAllPlayers().get(2)));

        // Verify that the players were added in correct order
        assertEquals(gameModel.getAllPlayers().get(1), gameModel.getPlayersConnected().get(1));
        assertEquals(gameModel.getAllPlayers().get(0), gameModel.getPlayersConnected().get(0));
        assertEquals(gameModel.getAllPlayers().get(2), gameModel.getPlayersConnected().get(2));

        //disconnect the firstPlayer
        gameModel.setPlayerAsDisconnected(gameModel.getAllPlayers().get(0));
        assertFalse(gameModel.getPlayersConnected().contains(gameModel.getAllPlayers().get(0)));
        gameModel.connectPlayerInOrder(gameModel.getAllPlayers().get(0));

        assertEquals(gameModel.getAllPlayers().get(1), gameModel.getPlayersConnected().get(0));
        assertEquals(gameModel.getAllPlayers().get(2), gameModel.getPlayersConnected().get(1));
        assertEquals(gameModel.getAllPlayers().get(0), gameModel.getPlayersConnected().get(2));
    }

    //------------------------game logic management------------------------
    @Test
    public void testDealCards() {
        addPlayersToGameModel(3);

        gameModel.getCommonBoard().setPlayerCount(gameModel.getAllPlayers().size());
        assertEquals(3, gameModel.getAllPlayers().size());
        gameModel.getCommonBoard().initializeBoard();


        //verify that common_board is initialized correctly
        assertTrue(gameModel.getCommonBoard().getBoardNodes()[0].isPlayerPresent(0));
        assertNotNull(gameModel.getCommonBoard().getDecks().getFirst());
        assertNotNull(gameModel.getCommonBoard().getDecks().get(1));
        assertNotNull(gameModel.getCommonBoard().getDecks().get(2));

        //assertNotNull(gameModel.getCommonBoard().drawFromConcreteDeck(0));
        assertEquals(14, gameModel.getCommonBoard().getObjectiveConcreteDeck().size());

        gameModel.dealCards();


        // Verify that all players has received the correct cards

        for (int i = 0; i < gameModel.getAllPlayers().size(); i++) {
            Player player = gameModel.getAllPlayers().get(i);
            {
                assertNull(player.getStarterCard());
                assertEquals(3, player.getCardHand().size());
                assertEquals(2, player.getSecretObjectiveCards().length);
            }
        }


        //Verify that the common decks have been correctly reduced of cards
        assertEquals(32, gameModel.getCommonBoard().getResourceConcreteDeck().size());
        assertEquals(35, gameModel.getCommonBoard().getGoldConcreteDeck().size());

        assertEquals(8, gameModel.getCommonBoard().getObjectiveConcreteDeck().size());

        assertEquals(6, gameModel.getCommonBoard().getStarterConcreteDeck().size());

        assertEquals(gameModel.getAllPlayers().getFirst().getStarterCardToChose()[0].getId(), gameModel.getAllPlayers().getFirst().getStarterCardToChose()[1].getId());
        assertEquals(gameModel.getAllPlayers().get(1).getStarterCardToChose()[0].getId(), gameModel.getAllPlayers().get(1).getStarterCardToChose()[1].getId());
        assertEquals(gameModel.getAllPlayers().get(2).getStarterCardToChose()[0].getId(), gameModel.getAllPlayers().get(2).getStarterCardToChose()[1].getId());

        GoldCard gold = gameModel.getCommonBoard().getGoldConcreteDeck().popGoldCard();
        System.out.println(gold.getId());
        System.out.println(gold.getButterflyRequired());
        System.out.println(gold.getLeafRequired());
        System.out.println(gold.getMushroomRequired());
        System.out.println(gold.getWolfRequired());
        System.out.println(gold.getPoints());
        System.out.println(gold.getCornerAtNE());
        System.out.println(gold.getCornerAtNW());
        System.out.println(gold.getCornerAtSW());
        System.out.println(gold.getCornerAtSE());

        GoldCard gold1 = gameModel.getCommonBoard().getGoldConcreteDeck().popGoldCard();
        System.out.println(gold1.getId());
        System.out.println(gold1.getButterflyRequired());
        System.out.println(gold1.getLeafRequired());
        System.out.println(gold1.getMushroomRequired());
        System.out.println(gold1.getWolfRequired());
        System.out.println(gold1.getPoints());
        System.out.println(gold1.getCornerAtNE());
        System.out.println(gold1.getCornerAtNW());
        System.out.println(gold1.getCornerAtSW());
        System.out.println(gold1.getCornerAtSW().resource);
        System.out.println(gold1.getCornerAtSW().item);
        System.out.println(gold1.getCornerAtSE());


        ResourceCard res = gameModel.getCommonBoard().getResourceConcreteDeck().popResourceCard();
        System.out.println(res.getId());
        System.out.println(res.getPoints());
        System.out.println(res.getCornerAtSW());
        System.out.println(res.getCornerAtNE());
        System.out.println(res.getCornerAtSE());
        System.out.println(res.getCornerAtNW());

        ResourceCard res1 = gameModel.getCommonBoard().getResourceConcreteDeck().popResourceCard();
        System.out.println(res1.getId());
        System.out.println(res1.getPoints());
        System.out.println(res1.getCornerAtSW().resource);
        System.out.println(res1.getCornerAtNE());
        System.out.println(res1.getCornerAtSE());
        System.out.println(res1.getCornerAtNW());








    }
    @Test
    public void testPlaceResourceCard(){
        //initialize game
        addPlayersToGameModel(3);
        gameModel.getCommonBoard().setPlayerCount(gameModel.getAllPlayers().size());
        gameModel.getCommonBoard().initializeBoard();
        gameModel.dealCards();
        //set the starter card for each player
        gameModel.getAllPlayers().getFirst().setStarterCard(gameModel.getAllPlayers().getFirst().getStarterCardToChose()[0]);
        gameModel.getAllPlayers().get(1).setStarterCard(gameModel.getAllPlayers().get(1).getStarterCardToChose()[0]);
        gameModel.getAllPlayers().get(2).setStarterCard(gameModel.getAllPlayers().get(2).getStarterCardToChose()[0]);

        //System.out.println(gameModel.getAllPlayers().getFirst().getStarterCardToChose()[0]);
        //System.out.println(gameModel.getAllPlayers().getFirst().getStarterCardToChose()[1]);

        //set the objective card for each player
        gameModel.getAllPlayers().getFirst().setChosenObjectiveCard(gameModel.getAllPlayers().getFirst().getSecretObjectiveCards()[0]);
        gameModel.getAllPlayers().get(1).setChosenObjectiveCard(gameModel.getAllPlayers().get(1).getSecretObjectiveCards()[0]);
        gameModel.getAllPlayers().get(2).setChosenObjectiveCard(gameModel.getAllPlayers().get(2).getSecretObjectiveCards()[0]);

        for (Player player : gameModel.getAllPlayers()) {
            player.playStarterCard();
            //player.getPersonalBoard().bruteForcePlaceCardSE(gameModel.getAllPlayers().getFirst().getHand().getFirst(), 500,500);
            //System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().;
        }

        //Check resource counter for points ERROR
        //assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_wolves());//-1
        //assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_mushrooms());
        //assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_leaves());


        /*

         //Test for placement of resources cards
        gameModel.getAllPlayers().getFirst().getPersonalBoard().bruteForcePlaceCardSE(gameModel.getAllPlayers().getFirst().getHand().getFirst(), 500,500);
        assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][501].getLevel());
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0));
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0).points);
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1));

        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtNW()); //butterfly
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtNW().item);//Optional.empty
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtNE()); //leaf
        //System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtSW()); //problem
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtSE().item); //problem


        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0).getCornerAtNE().is_visible);
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0).getCornerAtNE().resource); //null (not present)
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().getFirst().getCornerAtNW()); //Butterfly
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSW().resource); //Optional.empty
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSW().item); //Optional.empty
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSW().is_visible);//true

        System.out.println(gameModel.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSE().resource); //Optional.empty
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSE().item); //Optional.empty
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSE().is_visible);//true
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtNE().getCoordinate());


        gameModel.placeCard(gameModel.getAllPlayers().getFirst().getHand().get(1), gameModel.getAllPlayers().getFirst(), 501, 501);
        //assertEquals(2,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][501].getLevel()); //problem
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().size());


        System.out.println(gameModel.getAllPlayers().get(1).getHand().get(2));
        System.out.println(gameModel.getAllPlayers().get(1).getHand().get(2).getCornerAtSE());
        System.out.println(gameModel.getAllPlayers().get(1).getHand().get(2).getCornerAtSW());
        System.out.println(gameModel.getAllPlayers().get(1).getHand().get(2).getCornerAtNE());
        System.out.println(gameModel.getAllPlayers().get(1).getHand().get(2).getCornerAtNW().item);
        System.out.println(gameModel.getAllPlayers().get(1).getHand().get(2).getPoints());
        System.out.println(gameModel.getAllPlayers().get(1).getHand().get(2).type);

        System.out.println(gameModel.getAllPlayers().get(2).getHand().get(2));
        System.out.println(gameModel.getAllPlayers().get(2).getHand().get(2).getCornerAtSE());
        System.out.println(gameModel.getAllPlayers().get(2).getHand().get(2).getCornerAtSW());
        System.out.println(gameModel.getAllPlayers().get(2).getHand().get(2).getCornerAtNE().is_visible);
        System.out.println(gameModel.getAllPlayers().get(2).getHand().get(2).getCornerAtNW().item);
        System.out.println(gameModel.getAllPlayers().get(2).getHand().get(2).points);
        System.out.println(gameModel.getAllPlayers().get(2).getHand().get(2).type);

        GoldCard card = (GoldCard) gameModel.getAllPlayers().get(2).getHand().get(2);
        System.out.println(card.getMushroomRequired());
        System.out.println(card.getButterflyRequired()); */



        System.out.println(gameModel.getAllPlayers().getFirst().getStarterCard());
        System.out.println(gameModel.getAllPlayers().getFirst().getStarterCard().getCornerAtNW().is_visible); //true
        assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getLevel());
        System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getCornerFromCell().is_visible); //true
        System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getCornerFromCell().getBoard_coordinate().getX());
        assertNotNull(gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getCornerFromCell());
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1));

        //Resource Card on starterCard
        gameModel.placeCard(gameModel.getAllPlayers().getFirst().getHand().get(1), gameModel.getAllPlayers().getFirst(), 501, 501);
        assertEquals(2,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][501].getLevel());
        assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][502].getLevel());

        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtNW().resource);
        //assertEquals(1, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_butterflies()); //PROBLEM
        //System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getCornerFromCell().resource);

        assertEquals(1, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_leaves());
        //assertEquals(1, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_potions()); //PROBLEM


        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0));
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0).getCornerAtNE().getCoordinate());
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0).getCornerAtSE().getCoordinate());
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtNE());
        System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][502].getCornerFromCell().getCoordinate());
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1).getCornerAtNE().getCoordinate());
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().size());
        gameModel.getAllPlayers().getFirst().removeFromHand(gameModel.getAllPlayers().getFirst().getHand().get(1));
        System.out.println(gameModel.getAllPlayers().getFirst().getHand());
        assertEquals(2,gameModel.getAllPlayers().getFirst().getHand().size());

        gameModel.drawCard(gameModel.getAllPlayers().getFirst(), 6);
        assertEquals(3,gameModel.getAllPlayers().getFirst().getHand().size());

        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0));

        gameModel.placeCard(gameModel.getAllPlayers().getFirst().getHand().get(0), gameModel.getAllPlayers().getFirst(), 501, 502);
        assertEquals(2,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][502].getLevel());
        assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[502][502].getLevel());
        gameModel.getAllPlayers().getFirst().removeFromHand(gameModel.getAllPlayers().getFirst().getHand().get(0));
        assertEquals(2,gameModel.getAllPlayers().getFirst().getHand().size());

        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0));

        //resourceCard on ResourceCard
        gameModel.placeCard(gameModel.getAllPlayers().getFirst().getHand().get(0), gameModel.getAllPlayers().getFirst(), 502, 501);
        assertEquals(2,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[502][501].getLevel());

        gameModel.getAllPlayers().getFirst().removeFromHand(gameModel.getAllPlayers().getFirst().getHand().get(0));
        assertEquals(1,gameModel.getAllPlayers().getFirst().getHand().size());

        gameModel.drawCard(gameModel.getAllPlayers().getFirst(), 6);
        assertEquals(2,gameModel.getAllPlayers().getFirst().getHand().size());

        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(0));
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(1));

        //GoldCard on ResourceCard
        gameModel.placeCard(gameModel.getAllPlayers().getFirst().getHand().get(1), gameModel.getAllPlayers().getFirst(), 501, 503);
        assertEquals(2,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][503].getLevel());

        //GoldCard on GoldCard
        assertThrows(IllegalMoveException.class, ()-> gameModel.placeCard((GoldCard) gameModel.getAllPlayers().getFirst().getHand().get(0), gameModel.getAllPlayers().getFirst(), 501, 504));

        gameModel.drawCard(gameModel.getAllPlayers().getFirst(), 4);
        System.out.println(gameModel.getAllPlayers().getFirst().getHand().get(2));

        //GoldCard on StarterCard
        assertThrows(IllegalMoveException.class, ()->gameModel.placeCard((GoldCard) gameModel.getAllPlayers().getFirst().getHand().get(2), gameModel.getAllPlayers().getFirst(), 500, 500));
        //assertEquals(2,gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getLevel()); TODO: ERROR


        //Test for drawCard
        gameModel.drawCard(gameModel.getAllPlayers().getFirst(), 1);
        assertEquals(4,gameModel.getAllPlayers().getFirst().getHand().size());
        gameModel.drawCard(gameModel.getAllPlayers().getFirst(), 2);
        assertEquals(5,gameModel.getAllPlayers().getFirst().getHand().size());
        gameModel.drawCard(gameModel.getAllPlayers().getFirst(), 3);
        assertEquals(6,gameModel.getAllPlayers().getFirst().getHand().size());
        gameModel.drawCard(gameModel.getAllPlayers().getFirst(), 4);
        assertEquals(7,gameModel.getAllPlayers().getFirst().getHand().size());
        gameModel.drawCard(gameModel.getAllPlayers().getFirst(), 5);
        assertEquals(8,gameModel.getAllPlayers().getFirst().getHand().size());

        //Test for  CalculateFinalScore
        System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_butterflies());
        System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_wolves());
        System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_mushrooms());
        System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_leaves());
        System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_feathers());
        System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_potions());
        System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_parchments());
        System.out.println(Arrays.toString(gameModel.getAllPlayers().getFirst().getSecretObjectiveCards()));

        assertEquals(2,gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_butterflies()); //1
        assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_wolves());//-1
        assertEquals(0,gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_mushrooms());
        assertEquals(0,gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_leaves());
        assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_feathers());
        assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_potions());
        assertEquals(1,gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_parchments());

        gameModel.calculateFinalScores();
        System.out.println(gameModel.getAllPlayers().getFirst().getFinalScore());
        System.out.println(gameModel.getAllPlayers().get(1).getFinalScore());
        System.out.println(gameModel.getAllPlayers().get(2).getFinalScore());

    }



    //calculate final score
    //declare winner


    //--------------------------managing status --------------------------------------

    @Test
    public void testSetStatus(){

        gameModel.addPlayer("Player1");
        gameModel.addPlayer("Player2");
        gameModel.addPlayer("Player3");
        gameModel.getAllPlayers().getFirst().setAsConnected();
        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().getFirst());
        gameModel.getAllPlayers().get(1).setAsConnected();
        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().get(1));
        gameModel.getAllPlayers().get(2).setAsConnected();
        gameModel.setPlayerAsConnected(gameModel.getAllPlayers().get(2));

        gameModel.setStatus(GameStatus.RUNNING);
        GameStatus expectedStatus = GameStatus.RUNNING;
        assertEquals(expectedStatus ,gameModel.getStatus());

    }

}
