package it.polimi.demo.Controller;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.*;
import it.polimi.demo.*;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.exceptions.IllegalMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import it.polimi.demo.controller.MainController;
import it.polimi.demo.controller.GameController;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.cards.*;


import java.rmi.RemoteException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Now we need to use p.setchosenCard(chosen_card) before every place card.
 */

public class MainControllerTest {

    @Test
    public void createGameTest() throws RemoteException {
        MainController mainController = new MainController();
        GameListener lis = new GameListener() {
            @Override
            public void modelChanged() throws RemoteException {

            }

            @Override
            public void gameEnded() throws RemoteException {

            }

            @Override
            public void genericGameStatus() throws RemoteException {

            }

            @Override
            public void gameStarted() throws RemoteException {

            }

            @Override
            public void playerJoinedGame() throws RemoteException {

            }

            @Override
            public void gameUnavailable() throws RemoteException {

            }

            @Override
            public void newGame() throws RemoteException {

            }

            @Override
            public void removedGame() throws RemoteException {

            }

            @Override
            public void updatedGame() throws RemoteException {

            }
        };
        Player p = new Player("Player1");
        Player p2 = new Player("Player2");
        Player p3 = new Player("Player3");

        mainController.createGame(lis, "Player1", 2, 1);
        mainController.getGames().get(1).addPlayer(p2.getNickname());
        mainController.getGames().get(1).setPlayerAsConnected(p2);
//        mainController.getGames().get(1).addPlayer(p3.getNickname());
//        mainController.getGames().get(1).setPlayerAsConnected(p3);
        ;
        Assertions.assertEquals(2, mainController.getGames().get(1).getModel().getGameId(), mainController.getGames().get(1).getModel().getGameId());
        Assertions.assertEquals(mainController.getGames().get(1).getModel().getPlayersConnected().size(), 2);
        System.out.println(" NUMDEFAULT: "+ mainController.getGames().get(1).getModel().getNumPlayersToPlay() + "Connected: " + mainController.getGames().get(1).getNumConnectedPlayers());
        // Remark: You start a agme, initialize a game and deal card all in one with startIfFull(), is also the only method that checks if the game has enough players to start.
        mainController.getGames().get(1).startIfFull();

        //set the starter card for each player
        mainController.getGames().get(1).getModel().getAllPlayers().getFirst().setStarterCard(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getStarterCardToChose()[0]);
        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getStarterCardToChose()[0]);
        mainController.getGames().get(1).getModel().getAllPlayers().get(1).setStarterCard(mainController.getGames().get(1).getModel().getAllPlayers().get(1).getStarterCardToChose()[0]);

        //set the objective card for each player
        mainController.getGames().get(1).getModel().getAllPlayers().getFirst().setChosenObjectiveCard(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getSecretObjectiveCards()[0]);
        mainController.getGames().get(1).getModel().getAllPlayers().get(1).setChosenObjectiveCard(mainController.getGames().get(1).getModel().getAllPlayers().get(1).getSecretObjectiveCards()[0]);

        // Play starter card
        mainController.getGames().get(1).getPlayers().getFirst().playStarterCard();
        mainController.getGames().get(1).getPlayers().get(1).playStarterCard();

        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getStarterCard());
        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getStarterCard().getCornerAtNW().is_visible); //true

        // Test that the starter card is placed correctly
        assertEquals(1,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getLevel());

        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getCornerFromCell().is_visible); //true
        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getCornerFromCell().getBoard_coordinate().getX());

        assertNotNull(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getCornerFromCell());

        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(1));
        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getStarterCard());
        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getStarterCard().getCornerAtNW().is_visible); //true

        assertEquals(1,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getLevel());

        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getCornerFromCell().is_visible); //true
        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getCornerFromCell().getBoard_coordinate().getX());

        assertNotNull(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getCornerFromCell());

        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(1));
        mainController.getGames().get(1).getModel().getAllPlayers().getFirst().setChosenGameCard(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(1));
        System.out.println(" Chosen Card: " + mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getChosenGameCard());
        mainController.getGames().get(1).placeCard(mainController.getGames().get(1).getModel().getAllPlayers().getFirst(), 501, 501, Orientation.BACK);


        // OLD PLACE CARD,
        // TODO: Change all the methods invocation, NOw you have to call setChosenGameCard before calling placeCard, that now does not
        //  have a card as parameter. Look at rows 130-132.
//
//        assertEquals(2,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][501].getLevel());
//        assertEquals(1,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][502].getLevel());
//
//        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(1).getCornerAtNW().resource);
//        //assertEquals(1, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_butterflies()); //PROBLEM
//        //System.out.println(gameModel.getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getCornerFromCell().resource);
//
//        assertEquals(1, mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getNum_leaves());
//        //assertEquals(1, gameModel.getAllPlayers().getFirst().getPersonalBoard().getNum_potions()); //PROBLEM
//
//
//        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(0));
//        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(0).getCornerAtNE().getCoordinate());
//        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(0).getCornerAtSE().getCoordinate());
//        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(1).getCornerAtNE());
//        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][502].getCornerFromCell().getCoordinate());
//        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(1).getCornerAtNE().getCoordinate());
//        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().size());
//        // Remove from hand test
//        mainController.getGames().get(1).getPlayers().getFirst().removeFromHand(mainController.getGames().get(1).getPlayers().getFirst().getHand().get(1));
//        System.out.println(mainController.getGames().get(1).getPlayers().getFirst().getHand());
//        assertEquals(2, mainController.getGames().get(1).getPlayers().getFirst().getHand().size());
//
//
//        mainController.getGames().get(1).getModel().drawCard(mainController.getGames().get(1).getPlayers().getFirst(), 6);
//        assertEquals(3, mainController.getGames().get(1).getPlayers().getFirst().getHand().size());
//
//        System.out.println(mainController.getGames().get(1).getPlayers().getFirst().getHand().get(0));
//
//        mainController.getGames().get(1).getModel().placeCard(mainController.getGames().get(1).getModel().getAllPlayers().getFirst(), 501, 502);
//        assertEquals(2,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][502].getLevel());
//        assertEquals(1,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getBoard()[502][502].getLevel());
//        mainController.getGames().get(1).getModel().getAllPlayers().getFirst().removeFromHand(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(0));
//        assertEquals(2,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().size());
//
//        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(0));
//
//        //resourceCard on ResourceCard
//        mainController.getGames().get(1).getModel().placeCard(mainController.getGames().get(1).getModel().getAllPlayers().getFirst(), 502, 501);
//        assertEquals(2,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getBoard()[502][501].getLevel());
//
//        mainController.getGames().get(1).getModel().getAllPlayers().getFirst().removeFromHand(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(0));
//        assertEquals(1,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().size());
//
//        mainController.getGames().get(1).getModel().drawCard(mainController.getGames().get(1).getModel().getAllPlayers().getFirst(), 6);
//        assertEquals(2,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().size());
//
//        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(0));
//        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(1));
//
//        // GoldCard on ResourceCard
//        mainController.getGames().get(1).getModel().placeCard(mainController.getGames().get(1).getModel().getAllPlayers().getFirst(), 501, 503);
//        assertEquals(2,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][503].getLevel());
//
//        // GoldCard on GoldCard
//        assertThrows(IllegalMoveException.class, ()-> mainController.getGames().get(1).getModel().placeCard(mainController.getGames().get(1).getModel().getAllPlayers().getFirst(), 501, 504));
//
//        mainController.getGames().get(1).getModel().drawCard(mainController.getGames().get(1).getModel().getAllPlayers().getFirst(), 4);
//        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(2));
//
//        // GoldCard on StarterCard
//        System.out.println("Gold Card --> " + mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(2));
//        System.out.println(mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().get(2));
//        System.out.println("Number of mushrooms: " + mainController.getGames().get(1).getPlayers().getFirst().getPersonalBoard().getNum_mushrooms());
//        System.out.println("Number of leaves: " + mainController.getGames().get(1).getPlayers().getFirst().getPersonalBoard().getNum_leaves());
//        System.out.println("Number of potions: " + mainController.getGames().get(1).getPlayers().getFirst().getPersonalBoard().getNum_potions());
//        System.out.println("Number of butterflies: " + mainController.getGames().get(1).getPlayers().getFirst().getPersonalBoard().getNum_butterflies());
//        System.out.println("Number of wolves: " + mainController.getGames().get(1).getPlayers().getFirst().getPersonalBoard().getNum_wolves());
//        System.out.println("Number of parchments: " + mainController.getGames().get(1).getPlayers().getFirst().getPersonalBoard().getNum_parchments());
//        System.out.println("Number of feathers: " + mainController.getGames().get(1).getPlayers().getFirst().getPersonalBoard().getNum_feathers());
//        assertThrows(IllegalMoveException.class, () -> mainController.getGames().get(1).getModel().placeCard(mainController.getGames().get(1).getModel().getAllPlayers().getFirst(), 500, 500));
//        //assertEquals(2,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getLevel());
//
//
//        //Test for drawCard
//        mainController.getGames().get(1).getModel().drawCard(mainController.getGames().get(1).getModel().getAllPlayers().getFirst(), 1);
//        assertEquals(4,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().size());
//        mainController.getGames().get(1).getModel().drawCard(mainController.getGames().get(1).getModel().getAllPlayers().getFirst(), 2);
//        assertEquals(5,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().size());
//        mainController.getGames().get(1).getModel().drawCard(mainController.getGames().get(1).getModel().getAllPlayers().getFirst(), 3);
//        assertEquals(6,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().size());
//        mainController.getGames().get(1).getModel().drawCard(mainController.getGames().get(1).getModel().getAllPlayers().getFirst(), 4);
//        assertEquals(7,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().size());
//        mainController.getGames().get(1).getModel().drawCard(mainController.getGames().get(1).getModel().getAllPlayers().getFirst(), 5);
//        assertEquals(8,mainController.getGames().get(1).getModel().getAllPlayers().getFirst().getHand().size());

        // TODO: missing GoldCard case, waiting for Model testing

    }


    @Test
    public void joinFirstAvaibleGameTest() throws RemoteException {
        MainController mainController = new MainController();
        GameListener lis = new GameListener() {
            @Override
            public void modelChanged() throws RemoteException {

            }

            @Override
            public void gameEnded() throws RemoteException {

            }

            @Override
            public void genericGameStatus() throws RemoteException {

            }

            @Override
            public void gameStarted() throws RemoteException {

            }

            @Override
            public void playerJoinedGame() throws RemoteException {

            }

            @Override
            public void gameUnavailable() throws RemoteException {

            }

            @Override
            public void newGame() throws RemoteException {

            }

            @Override
            public void removedGame() throws RemoteException {

            }

            @Override
            public void updatedGame() throws RemoteException {

            }
        };
        Player p = new Player("Player1");
        Player p2 = new Player("Player2");
        Player p3 = new Player("Player3");
        mainController.createGame(lis, "Player1", 2, 1);
        mainController.joinFirstAvailableGame(lis,  "Player2");
        assertEquals(2, mainController.getGames().get(1).getPlayers().size());
        // check if the player is set as connected
        assertEquals(2, mainController.getGames().get(1).getNumConnectedPlayers());

    }

    @Test
    public void joinGameTest() throws RemoteException{
        MainController mainController = new MainController();
        GameListener lis = new GameListener() {
            @Override
            public void modelChanged() throws RemoteException {

            }

            @Override
            public void gameEnded() throws RemoteException {

            }

            @Override
            public void genericGameStatus() throws RemoteException {

            }

            @Override
            public void gameStarted() throws RemoteException {

            }

            @Override
            public void playerJoinedGame() throws RemoteException {

            }

            @Override
            public void gameUnavailable() throws RemoteException {

            }

            @Override
            public void newGame() throws RemoteException {

            }

            @Override
            public void removedGame() throws RemoteException {

            }

            @Override
            public void updatedGame() throws RemoteException {

            }
        };
        Player p = new Player("Player1");
        Player p2 = new Player("Player2");
        Player p3 = new Player("Player3");
        mainController.createGame(lis, "Player1", 2, 1);

        mainController.joinGame(lis, "Player2", 1);
        assertEquals(2, mainController.getGames().get(1).getPlayers().size());
        assertEquals(2, mainController.getGames().get(1).getNumConnectedPlayers());
    }



}
