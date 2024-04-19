package it.polimi.demo.model;

import it.polimi.demo.model.DefaultValues;
import it.polimi.demo.model.GameModel;
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
import it.polimi.demo.model.GameModel;
import it.polimi.demo.model.interfaces.PlayerIC;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameModelTest {

    private GameModel gameModel;

    @BeforeEach
    public void setUp() {
        gameModel = new GameModel();
    }

    /*
    @Test
    public void testAreConnectedPlayersEnough() {
        assertFalse(gameModel.areConnectedPlayersEnough());
        // Add enough players to start the game
        Player player = new Player("Player1");
        gameModel.addPlayer(player);
        Player player2 = new Player("Player2");
        gameModel.addPlayer(player2);

        assertTrue(gameModel.areConnectedPlayersEnough());
    }

    @Test
    public void testIsTheCurrentPlayerConnected() {
        assertFalse(gameModel.isTheCurrentPlayerConnected());
        // Aggiungi giocatori sufficienti per iniziare la partita
        addPlayersToGameModel(DefaultValues.MinNumOfPlayer);
        assertTrue(gameModel.isTheCurrentPlayerConnected());
    }*/
/*
    @Test
    public void testGetBeginnerPlayer() {
        assertNull(gameModel.getBeginnerPlayer());
        addPlayersToGameModel(DefaultValues.MinNumOfPlayer);
        assertNotNull(gameModel.getBeginnerPlayer());
    }*/

    @Test
    public void testGetAllPlayers() {
        assertEquals(0, gameModel.getAllPlayers().size());
        // Add some players
        addPlayersToGameModel(3);
        assertEquals(3, gameModel.getAllPlayers().size());
    }

    @Test
    public void testSetPlayerAsReadyToStart() {
        addPlayersToGameModel(DefaultValues.MinNumOfPlayer);
        Player player = gameModel.getAllPlayers().get(0);
        assertFalse(player.getReadyToStart());
        gameModel.setPlayerAsReadyToStart(player);
        assertTrue(player.getReadyToStart());
    }

    @Test
    public void testArePlayersReadyToStartAndEnough() {
        assertFalse(gameModel.arePlayersReadyToStartAndEnough());
        // Add enough players to start the game
        addPlayersToGameModel(DefaultValues.MinNumOfPlayer);
        assertFalse(gameModel.arePlayersReadyToStartAndEnough());
        // set all players as ready
        gameModel.getAllPlayers().forEach(Player::setAsReadyToStart);
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
    public void testAddPlayer() {
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
        gameModel.addPlayer(player4);

        //Attempt to add more players than the maximum limit
        assertThrows(MaxPlayersLimitException.class, () -> gameModel.addPlayer(new Player("player5")));

    }

    @Test
    public void testRemovePlayer() {
        addPlayersToGameModel(DefaultValues.MinNumOfPlayer);
        Player playerToRemove = gameModel.getAllPlayers().get(0);
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


}

