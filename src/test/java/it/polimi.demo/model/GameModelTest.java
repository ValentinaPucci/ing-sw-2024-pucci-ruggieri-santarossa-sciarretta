package it.polimi.demo.model;

import it.polimi.demo.model.DefaultValues;
import it.polimi.demo.model.GameModel;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import javafx.animation.Animation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.listener.ListenersHandler;
import it.polimi.demo.model.board.CommonBoard;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.chat.Chat;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.exceptions.*;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.ConcreteDeck;
import it.polimi.demo.model.GameModel;
import it.polimi.demo.model.interfaces.PlayerIC;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

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
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        gameModel.addPlayer(player1);
        gameModel.addPlayer(player2);
        assertEquals(player1, gameModel.getPlayerEntity("Player1"));
        assertEquals(player2, gameModel.getPlayerEntity("Player2"));
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
            gameModel.addPlayer(new Player("Player" + i));
        }
    }
    @Test
    public void testSetPlayerAsConnected() {
        Player player1 = new Player("Player1");

        assertFalse(gameModel.areConnectedPlayersEnough());
        gameModel.addPlayer(player1);

        Player player2 = new Player("Player2");
        gameModel.addPlayer(player2);
        assertEquals(2, gameModel.getAllPlayers().size());

        assertThrows(IllegalArgumentException.class, () -> gameModel.setPlayerAsConnected(player1));
        assertThrows(IllegalArgumentException.class, () -> gameModel.setPlayerAsConnected(player2));

        player1.setAsConnected();
        gameModel.setPlayerAsConnected(player1);

        assertEquals(1, gameModel.getPlayersConnected().size());
        assertNotEquals(2, gameModel.getPlayersConnected().size());

        assertTrue(gameModel.isTheCurrentPlayerConnected());
        assertFalse(gameModel.areConnectedPlayersEnough());
        player2.setAsConnected();
        gameModel.setPlayerAsConnected(player2);
        assertTrue(gameModel.areConnectedPlayersEnough());
    }

    @Test
    public void testSetPlayerAsDisconnected() {

        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        // add and connect a player to the game
        gameModel.addPlayer(player1);
        player1.setAsConnected();
        gameModel.setPlayerAsConnected(player1);

        // Verify that the player1 is connected
        assertTrue(player1.getIsConnected());
        // Disconnect player1
        gameModel.setPlayerAsDisconnected(player1);
        //Verify that the player1 is disconnect and not ready to start
        assertFalse(player1.getIsConnected());
        assertFalse(player1.getReadyToStart());
        //Verify that the player1 is not in players_connected list

        assertFalse(gameModel.getPlayersConnected().contains(player1));

        // Verifica che il metodo notify_playerDisconnected sia stato chiamato con i parametri corretti
        //verify(listener_handler).notify_playerDisconnected(gameModel, player1.getNickname());

        // Add and connect player2
        gameModel.addPlayer(player2);
        player2.setAsConnected();
        gameModel.setPlayerAsConnected(player2);


        // Disconnect player2
        gameModel.setPlayerAsDisconnected(player2);
        assertFalse(player2.getIsConnected());

        // Verifica che il metodo notify_onlyOnePlayerConnected sia stato chiamato
        //verify(listener_handler).notify_onlyOnePlayerConnected(gameModel, DefaultValues.secondsToWaitReconnection);
    }

    @Test
    public void testReconnectPlayer() {
        Player player1 = new Player("Player1");
        gameModel.addPlayer(player1);
        player1.setAsConnected();
        gameModel.setPlayerAsConnected(player1);

        // Verify that a player can't reconnect if he is already connected
        assertThrows(PlayerAlreadyConnectedException.class, () -> gameModel.reconnectPlayer(player1));

        // Disconnect player1
        gameModel.setPlayerAsDisconnected(player1);

        // Verify that the player can reconnect after being disconnected
        assertDoesNotThrow(() -> gameModel.reconnectPlayer(player1));
        assertTrue(player1.getIsConnected());
        // Verifica che il metodo notify_playerReconnected sia stato chiamato con i parametri corretti
        //verify(listener_handler).notify_playerReconnected(gameModel, player1.getNickname());
    }

    @Test
    public void testConnectPlayerInOrder() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        Player player3 = new Player("Player3");
        Player player4 = new Player("Player4");


        gameModel.addPlayer(player1);
        gameModel.addPlayer(player2);
        gameModel.addPlayer(player3);

        player1.setAsConnected();
        gameModel.setPlayerAsConnected(player1);
        player2.setAsConnected();
        gameModel.setPlayerAsConnected(player2);
        player3.setAsConnected();
        gameModel.setPlayerAsConnected(player3);

        assertEquals(player2, gameModel.getPlayersConnected().get(1));
        assertEquals(player3, gameModel.getPlayersConnected().get(2));
        assertEquals(player1, gameModel.getPlayersConnected().getFirst());

        assertTrue(player2.getIsConnected());
        assertTrue(gameModel.getPlayersConnected().contains(player2));

        //disconnect a player in the middle of players_connected
        gameModel.setPlayerAsDisconnected(player2);
        assertFalse(gameModel.getPlayersConnected().contains(player2));


        assertThrows(IllegalArgumentException.class, () -> gameModel.connectPlayerInOrder(player4));
        assertEquals(0, gameModel.getAllPlayers().indexOf(player2) - 1);
        assertEquals(0, gameModel.getAllPlayers().indexOf(player1));

        assertEquals(player3, gameModel.getPlayersConnected().get(1));
        assertEquals(player1, gameModel.getPlayersConnected().getFirst());
        gameModel.connectPlayerInOrder(player2);


        // Verify that players were added correctly to players_connected list
        assertTrue(gameModel.getPlayersConnected().contains(player1));
        assertTrue(gameModel.getPlayersConnected().contains(player2));
        assertTrue(gameModel.getPlayersConnected().contains(player3));

        // Verify that the players were added in correct order
        assertEquals(player2, gameModel.getPlayersConnected().get(1));
        assertEquals(player1, gameModel.getPlayersConnected().get(0));
        assertEquals(player3, gameModel.getPlayersConnected().get(2));

        //disconnect the firstPlayer
        gameModel.setPlayerAsDisconnected(player1);
        assertFalse(gameModel.getPlayersConnected().contains(player1));
        gameModel.connectPlayerInOrder(player1);

        assertEquals(player2, gameModel.getPlayersConnected().get(0));
        assertEquals(player3, gameModel.getPlayersConnected().get(1));
        assertEquals(player1, gameModel.getPlayersConnected().get(2));
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

    }

    //place resource card
    //place gold card
    //draw card
    //calculate final score
    //declare winner


    //--------------------------managing status --------------------------------------

    @Test
    public void testSetStatus(){
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        gameModel.addPlayer(player1);
        gameModel.addPlayer(player2);
        gameModel.addPlayer(player3);
        player1.setAsConnected();
        gameModel.setPlayerAsConnected(player1);
        player2.setAsConnected();
        gameModel.setPlayerAsConnected(player2);
        player3.setAsConnected();
        gameModel.setPlayerAsConnected(player3);

        gameModel.setStatus(GameStatus.RUNNING);
        GameStatus expectedStatus = GameStatus.RUNNING;
        assertEquals(expectedStatus ,gameModel.getStatus());

    }





    //----------------------------listeners--------------------------------------------
    @Test
    public void testAddListener() {
        Remote remoteListener = null;
        gameModel.addListener((GameListener) remoteListener);
        assertTrue(gameModel.getListenersHandler().contains((GameListener) remoteListener));
        assertEquals(1,gameModel.getListenersHandler().size());
        gameModel.removeListener((GameListener) remoteListener);
        assertEquals(0,gameModel.getListenersHandler().size());
    }













}
