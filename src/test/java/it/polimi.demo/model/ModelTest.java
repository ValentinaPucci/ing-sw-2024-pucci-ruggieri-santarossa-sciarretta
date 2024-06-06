package it.polimi.demo.model;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.polimi.demo.model.enumerations.GameStatus;
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
    public void testGetBeginnerPlayer() {
        assertThrows(NoSuchElementException.class, ()-> model.getBeginnerPlayer());
        addPlayersToGameModel(DefaultValues.minNumOfPlayer);
        assertNotNull(model.getBeginnerPlayer());
    }

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
    public void testGetPlayerEntity() {
        assertNull(model.getPlayerEntity("Player1"));
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        model.addPlayer(player1);
        model.addPlayer(player2);
        assertEquals(model.getAllPlayers().getFirst(), model.getPlayerEntity("Player1"));
        assertEquals(model.getAllPlayers().get(1), model.getPlayerEntity("Player2"));
        assertNull(model.getPlayerEntity("UnknownPlayer"));
    }

    @Test
    public void testAddPlayer() {
        assertEquals(0, model.getAllPlayers().size());
        // Add a player
        Player player = new Player("Player1");
        model.addPlayer(player);
        assertEquals(1, model.getAllPlayers().size());
        assertTrue(model.getAllPlayers().contains(player));
        // Attempt to add the same player again
        assertThrows(PlayerAlreadyConnectedException.class, () -> model.addPlayer(player));
        //Add other players
        Player player2 = new Player("Player2");
        model.addPlayer(player2);
        assertEquals(2, model.getAllPlayers().size());
        assertTrue(model.getAllPlayers().contains(player2));

        Player player3 = new Player("Player3");
        model.addPlayer(player3);
        assertEquals(3, model.getAllPlayers().size());
        assertTrue(model.getAllPlayers().contains(player3));
        assertThrows(PlayerAlreadyConnectedException.class, () -> model.addPlayer(player3));

        System.out.println(model.getNumPlayersToPlay());

        Player player4 = new Player("Player4");
        assertThrows(MaxPlayersLimitException.class,()-> model.addPlayer(player4));

        assertEquals(3, model.getAllPlayers().size());
        System.out.println(model.getAllPlayers().getFirst().getNickname());
        System.out.println(model.getAllPlayers().get(1).getNickname());
        System.out.println(model.getAllPlayers().get(2).getNickname());


    }

    @Test
    public void testRemovePlayer() {
        addPlayersToGameModel(DefaultValues.minNumOfPlayer);
        Player playerToRemove = model.getAllPlayers().getFirst();
        assertEquals(DefaultValues.minNumOfPlayer, model.getAllPlayers().size());
        model.removePlayer(playerToRemove);
        assertFalse(model.getAllPlayers().contains(playerToRemove));
        assertEquals(DefaultValues.minNumOfPlayer - 1, model.getAllPlayers().size());
    }

    // Utility method to add a specific number of players to the game model
    private void addPlayersToGameModel(int numPlayers) {
        for (int i = 0; i < numPlayers; i++) {
            model.addPlayer(new Player("Player" + i));
        }
    }
//    @Test
//   // This method is useless because a player is set as connected in addPlayer method
//    public void testSetPlayerAsConnected() {
//
//        Player player1 = new Player("Player1");
//        Player player2 = new Player("Player2");
//        model.addPlayer(player1);
//        model.addPlayer(player2);
//        assertEquals(2, model.getAllPlayers().size());
//
//        //assertThrows(IllegalArgumentException.class, () -> model.setPlayerAsConnected(model.getAllPlayers().getFirst()));
//        //assertThrows(IllegalArgumentException.class, () -> model.setPlayerAsConnected(model.getAllPlayers().get(1)));
//
//        model.getAllPlayers().getFirst().setAsConnected();
//        model.setPlayerAsConnected(model.getAllPlayers().getFirst());
//        System.out.println(model.getAllPlayers().getFirst().getIsConnected());
//
//        assertEquals(1, model.getPlayersConnected().size());
//        assertNotEquals(2, model.getPlayersConnected().size());
//
//        assertTrue(model.getAllPlayers().getFirst().getIsConnected());
//        assertFalse(model.getAllPlayers().get(1).getIsConnected());
//        model.getAllPlayers().get(1).setAsConnected();
//        model.setPlayerAsConnected(model.getAllPlayers().get(1));
//        assertEquals(2, model.getPlayersConnected().size());
//    }

//    @Test
//    public void testSetPlayerAsDisconnected() {
//
//
//        addPlayersToGameModel(3);
//
//        System.out.println((model.getAllPlayers().getFirst().getIsConnected()));
//
//
//        // Verify that the player1 is connected
//        assertTrue(model.getAllPlayers().getFirst().getIsConnected());
//        // Disconnect player1
//        model.setPlayerAsDisconnected(model.getAllPlayers().getFirst());
//        //Verify that the player1 is disconnect and not ready to start
//        assertFalse(model.getAllPlayers().getFirst().getIsConnected());
//        assertFalse(model.getAllPlayers().getFirst().getReadyToStart());
//        //Verify that the player1 is not in players_connected list
//
//        assertFalse(model.getPlayersConnected().contains(model.getAllPlayers().getFirst()));
//
//        // Verifica che il metodo notify_playerDisconnected sia stato chiamato con i parametri corretti
//        //verify(listener_handler).notify_playerDisconnected(model, player1.getNickname());
//
//        // Add and connect player2
//        //model.addPlayer("Player2");
//        model.getAllPlayers().get(1).setAsConnected();
//        model.setPlayerAsConnected(model.getAllPlayers().get(1));
//
//
//        // Disconnect player2
//        model.setPlayerAsDisconnected(model.getAllPlayers().get(1));
//        assertFalse(model.getAllPlayers().get(1).getIsConnected());
//
//    }


//    @Test
//    public void testReconnectPlayer() {
//        Player player1 = new Player("Player1");
//        model.addPlayer(player1);
//        model.getBeginnerPlayer().setAsConnected();
//        model.setPlayerAsConnected(model.getBeginnerPlayer());
//
//        // Verify that a player can't reconnect if he is already connected
//        assertThrows(PlayerAlreadyConnectedException.class, () -> model.reconnectPlayer(model.getBeginnerPlayer()));
//
//        // Disconnect player1
//        model.setPlayerAsDisconnected(model.getBeginnerPlayer());
//
//        // Verify that the player can reconnect after being disconnected
//        assertDoesNotThrow(() -> model.reconnectPlayer(model.getBeginnerPlayer()));
//        assertTrue(model.getBeginnerPlayer().getIsConnected());
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
//        model.addPlayer(player1);
//        model.addPlayer(player2);
//        model.addPlayer(player3);
//
//        model.getAllPlayers().getFirst().setAsConnected();
//        model.setPlayerAsConnected(model.getAllPlayers().getFirst());
//        model.getAllPlayers().get(1).setAsConnected();
//        model.setPlayerAsConnected(model.getAllPlayers().get(1));
//        model.getAllPlayers().get(2).setAsConnected();
//        model.setPlayerAsConnected(model.getAllPlayers().get(2));
//
//        assertEquals(model.getAllPlayers().get(1), model.getPlayersConnected().get(1));
//        assertEquals(model.getAllPlayers().get(2), model.getPlayersConnected().get(2));
//        assertEquals(model.getAllPlayers().get(0), model.getPlayersConnected().getFirst());
//
//        assertTrue(model.getAllPlayers().get(1).getIsConnected());
//        assertTrue(model.getPlayersConnected().contains(model.getAllPlayers().get(1)));
//
//        //disconnect a player in the middle of players_connected
//        model.setPlayerAsDisconnected(model.getAllPlayers().get(1));
//        assertFalse(model.getPlayersConnected().contains(model.getAllPlayers().get(1)));
//
//
//        assertThrows(IllegalArgumentException.class, () -> model.connectPlayerInOrder(player4));
//        assertEquals(0, model.getAllPlayers().indexOf(model.getAllPlayers().get(1)) - 1);
//        assertEquals(0, model.getAllPlayers().indexOf(model.getAllPlayers().get(0)));
//
//        assertEquals(model.getAllPlayers().get(2), model.getPlayersConnected().get(1));
//        assertEquals(model.getAllPlayers().get(0), model.getPlayersConnected().getFirst());
//        model.connectPlayerInOrder(model.getAllPlayers().get(1));
//
//
//        // Verify that players were added correctly to players_connected list
//        assertTrue(model.getPlayersConnected().contains(model.getAllPlayers().get(0)));
//        assertTrue(model.getPlayersConnected().contains(model.getAllPlayers().get(1)));
//        assertTrue(model.getPlayersConnected().contains(model.getAllPlayers().get(2)));
//
//        // Verify that the players were added in correct order
//        assertEquals(model.getAllPlayers().get(1), model.getPlayersConnected().get(1));
//        assertEquals(model.getAllPlayers().get(0), model.getPlayersConnected().get(0));
//        assertEquals(model.getAllPlayers().get(2), model.getPlayersConnected().get(2));
//
//        //disconnect the firstPlayer
//        model.setPlayerAsDisconnected(model.getAllPlayers().get(0));
//        assertFalse(model.getPlayersConnected().contains(model.getAllPlayers().get(0)));
//        model.connectPlayerInOrder(model.getAllPlayers().get(0));
//
//        assertEquals(model.getAllPlayers().get(1), model.getPlayersConnected().get(0));
//        assertEquals(model.getAllPlayers().get(2), model.getPlayersConnected().get(1));
//        assertEquals(model.getAllPlayers().get(0), model.getPlayersConnected().get(2));
//    }
//
//    //------------------------game logic management------------------------
    @Test
    public void testDealCards() {
        addPlayersToGameModel(3);

        model.getCommonBoard().setPlayerCount(model.getAllPlayers().size());
        assertEquals(3, model.getAllPlayers().size());
        model.getCommonBoard().initializeBoard();


        //verify that common_board is initialized correctly
        assertTrue(model.getCommonBoard().getBoardNodes()[0].isPlayerPresent(0));
        assertNotNull(model.getCommonBoard().getDecks().getFirst());
        assertNotNull(model.getCommonBoard().getDecks().get(1));
        assertNotNull(model.getCommonBoard().getDecks().get(2));



        //assertNotNull(model.getCommonBoard().drawFromConcreteDeck(0));
        assertEquals(14, model.getCommonBoard().getObjectiveConcreteDeck().size());

        model.dealCards();


        // Verify that all players has received the correct cards

        for (int i = 0; i < model.getAllPlayers().size(); i++) {
            Player player = model.getAllPlayers().get(i);
            {
                assertNull(player.getStarterCard());
                assertEquals(3, player.getCardHand().size());
                assertEquals(2, player.getSecretObjectiveCards().size());
            }
        }


        //Verify that the common decks have been correctly reduced of cards
        assertEquals(32, model.getCommonBoard().getResourceConcreteDeck().size());
        assertEquals(35, model.getCommonBoard().getGoldConcreteDeck().size());

        assertEquals(8, model.getCommonBoard().getObjectiveConcreteDeck().size());

        assertEquals(6, model.getCommonBoard().getStarterConcreteDeck().size());

        assertEquals(model.getAllPlayers().getFirst().getStarterCardToChose().get(0).getId(), model.getAllPlayers().getFirst().getStarterCardToChose().get(0).getId());
        assertEquals(model.getAllPlayers().get(1).getStarterCardToChose().get(0).getId(), model.getAllPlayers().get(1).getStarterCardToChose().get(0).getId());
        assertEquals(model.getAllPlayers().get(2).getStarterCardToChose().get(0).getId(), model.getAllPlayers().get(2).getStarterCardToChose().get(0).getId());

        GoldCard gold = model.getCommonBoard().getGoldConcreteDeck().popGoldCard();
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

        GoldCard gold1 = model.getCommonBoard().getGoldConcreteDeck().popGoldCard();
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


        ResourceCard res = model.getCommonBoard().getResourceConcreteDeck().popResourceCard();
        System.out.println(res.getId());
        System.out.println(res.getPoints());
        System.out.println(res.getCornerAtSW());
        System.out.println(res.getCornerAtNE());
        System.out.println(res.getCornerAtSE());
        System.out.println(res.getCornerAtNW());

        ResourceCard res1 = model.getCommonBoard().getResourceConcreteDeck().popResourceCard();
        System.out.println(res1.getId());
        System.out.println(res1.getPoints());
        System.out.println(res1.getCornerAtSW().resource);
        System.out.println(res1.getCornerAtNE());
        System.out.println(res1.getCornerAtSE());
        System.out.println(res1.getCornerAtNW());

    }

    @Test
    public void testPlaceResourceCard() throws GameEndedException {
        //initialize game
        addPlayersToGameModel(3);
        model.getCommonBoard().setPlayerCount(model.getAllPlayers().size());
        //model.getCommonBoard().initializeBoard();

        //*************************Initialize board without shuffle****************************
        model.getCommonBoard().setInitialPosition();

        // Populate the table with cards from the decks
        for (int i = 0; i < 2; i++) {
            // Populate the first array with two cards from the Resource ConcreteDeck
            try {
                Card cardFromConcreteDeck1 = model.getCommonBoard().getResourceConcreteDeck().pop();
                model.getCommonBoard().getTableCards()[0][i] = cardFromConcreteDeck1;
            } catch (EmptyStackException e) {
                System.err.println("Empty deck: " + e.getMessage());
            }

            // Populate the second array with two cards from the Gold ConcreteDeck
            try {
                Card cardFromConcreteDeck2 = model.getCommonBoard().getGoldConcreteDeck().pop();
                model.getCommonBoard().getTableCards()[1][i] = cardFromConcreteDeck2;
            } catch (EmptyStackException e) {
                System.err.println("Empty deck: " + e.getMessage());
            }

            // Populate the second array with two cards from the Objective ConcreteDeck
            try {
                Card cardFromConcreteDeck3 = model.getCommonBoard().getObjectiveConcreteDeck().pop();
                model.getCommonBoard().getTableCards()[2][i] = cardFromConcreteDeck3;
            } catch (EmptyStackException e) {
                System.err.println("Empty deck: " + e.getMessage());
            }
        }
        model.getCommonBoard().getDecks().add(0, this.model.getCommonBoard().getResourceConcreteDeck());
        model.getCommonBoard().getDecks().add(1, this.model.getCommonBoard().getGoldConcreteDeck());
        model.getCommonBoard().getDecks().add(2, this.model.getCommonBoard().getObjectiveConcreteDeck());
        //**************************************************************************************

        model.dealCards();
        //set the starter card for each player
        model.getAllPlayers().getFirst().setStarterCard(model.getAllPlayers().getFirst().getStarterCardToChose().get(0));
        model.getAllPlayers().get(1).setStarterCard(model.getAllPlayers().get(1).getStarterCardToChose().get(0));
        model.getAllPlayers().get(2).setStarterCard(model.getAllPlayers().get(2).getStarterCardToChose().get(0));

        //System.out.println(model.getAllPlayers().getFirst().getStarterCardToChose()[0]);
        //System.out.println(model.getAllPlayers().getFirst().getStarterCardToChose()[1]);

        //set the objective card for each player
        model.getAllPlayers().getFirst().setChosenObjectiveCard(model.getAllPlayers().getFirst().getSecretObjectiveCards().get(0));
        model.getAllPlayers().get(1).setChosenObjectiveCard(model.getAllPlayers().get(1).getSecretObjectiveCards().get(0));
        model.getAllPlayers().get(2).setChosenObjectiveCard(model.getAllPlayers().get(2).getSecretObjectiveCards().get(0));

        System.out.println(model.getAllPlayers().getFirst().getSecretObjectiveCards().get(0));

        for (Player player : model.getAllPlayers()) {
            player.playStarterCard();
            //player.getPersonalBoard().bruteForcePlaceCardSE(model.getAllPlayers().getFirst().getHand().getFirst(), 500,500);
            //System.out.println(model.getAllPlayers().getFirst().getPersonalBoard().;
        }

        //Check resource counter for points ERROR
        //assertEquals(1,model.getAllPlayers().getFirst().getPersonalBoard().getNum_wolves());//-1
        //assertEquals(1,model.getAllPlayers().getFirst().getPersonalBoard().getNum_mushrooms());
        //assertEquals(1,model.getAllPlayers().getFirst().getPersonalBoard().getNum_leaves());




         //Test for placement of resources cards
        assertEquals(1, model.getAllPlayers().getFirst().getPersonalBoard().getBoard()[251][251].getLevel());
        System.out.println(model.getAllPlayers().getFirst().getHand().get(0));
        System.out.println(model.getAllPlayers().getFirst().getHand().get(0).points);
        System.out.println(model.getAllPlayers().getFirst().getHand().get(1));

        System.out.println(model.getAllPlayers().getFirst().getHand().get(1).getCornerAtNW()); //butterfly
        System.out.println(model.getAllPlayers().getFirst().getHand().get(1).getCornerAtNW().item);//Optional.empty
        System.out.println(model.getAllPlayers().getFirst().getHand().get(1).getCornerAtNE()); //leaf
        //System.out.println(model.getAllPlayers().getFirst().getHand().get(1).getCornerAtSW()); //problem
        System.out.println(model.getAllPlayers().getFirst().getHand().get(1).getCornerAtSE().item); //problem


        System.out.println(model.getAllPlayers().getFirst().getHand().get(0).getCornerAtNE().is_visible);
        System.out.println(model.getAllPlayers().getFirst().getHand().get(0).getCornerAtNE().resource); //null (not present)
        System.out.println(model.getAllPlayers().getFirst().getHand().getFirst().getCornerAtNW()); //Butterfly
        System.out.println(model.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSW().resource); //Optional.empty
        System.out.println(model.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSW().item); //Optional.empty
        System.out.println(model.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSW().is_visible);//true

        System.out.println(model.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSE().resource); //Optional.empty
        System.out.println(model.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSE().item); //Optional.empty
        System.out.println(model.getAllPlayers().getFirst().getHand().getFirst().getCornerAtSE().is_visible);//true
        System.out.println(model.getAllPlayers().getFirst().getHand().get(1).getCornerAtNE().getCoordinate());


        model.placeCard(model.getAllPlayers().getFirst().getHand().get(1), model.getAllPlayers().getFirst(), 251, 251);
        //assertEquals(2,model.getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][501].getLevel()); //problem
        System.out.println(model.getAllPlayers().getFirst().getHand().size());


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



        System.out.println(model.getAllPlayers().getFirst().getStarterCard());
        System.out.println(model.getAllPlayers().getFirst().getStarterCard().getCornerAtNW().is_visible); //true
        assertEquals(1, model.getAllPlayers().getFirst().getPersonalBoard().getBoard()[250][250].getLevel());
        System.out.println(model.getAllPlayers().getFirst().getPersonalBoard().getBoard()[250][250].getCornerFromCell().is_visible); //true
        System.out.println(model.getAllPlayers().getFirst().getPersonalBoard().getBoard()[250][250].getCornerFromCell().getBoard_coordinate().getX());
        assertNotNull(model.getAllPlayers().getFirst().getPersonalBoard().getBoard()[250][250].getCornerFromCell());
        System.out.println(model.getAllPlayers().getFirst().getHand().get(1));

        System.out.println(model.getAllPlayers().getFirst().getStarterCard().getCornerAtNW().resource);
        System.out.println(model.getAllPlayers().getFirst().getStarterCard().getCornerAtNE().resource);
        System.out.println(model.getAllPlayers().getFirst().getStarterCard().getCornerAtSW().resource);
        System.out.println(model.getAllPlayers().getFirst().getStarterCard().getCornerAtSE().resource);

        assertEquals(1, model.getAllPlayers().getFirst().getPersonalBoard().getNum_butterflies());

        //Resource Card on starterCard
        model.placeCard(model.getAllPlayers().getFirst().getHand().get(1), model.getAllPlayers().getFirst(), 251, 251);
        assertEquals(2, model.getAllPlayers().getFirst().getPersonalBoard().getBoard()[251][251].getLevel());
        assertEquals(1, model.getAllPlayers().getFirst().getPersonalBoard().getBoard()[251][252].getLevel());

        System.out.println(model.getAllPlayers().getFirst().getHand().get(1).getCornerAtNW().resource);
        assertEquals(0, model.getAllPlayers().getFirst().getPersonalBoard().getPoints());



        //assertEquals(1, model.getAllPlayers().getFirst().getPersonalBoard().getNum_butterflies()); //PROBLEM
        //System.out.println(model.getAllPlayers().getFirst().getPersonalBoard().getBoard()[500][500].getCornerFromCell().resource);

        assertEquals(2, model.getAllPlayers().getFirst().getPersonalBoard().getNum_leaves());
        assertEquals(1, model.getAllPlayers().getFirst().getPersonalBoard().getNum_potions());
        assertEquals(1, model.getAllPlayers().getFirst().getPersonalBoard().getNum_wolves());
        assertEquals(1, model.getAllPlayers().getFirst().getPersonalBoard().getNum_butterflies());
        assertEquals(1, model.getAllPlayers().getFirst().getPersonalBoard().getNum_mushrooms());


        System.out.println(model.getAllPlayers().getFirst().getHand().get(0));
        System.out.println(model.getAllPlayers().getFirst().getHand().get(0).getCornerAtNE().getCoordinate());
        System.out.println(model.getAllPlayers().getFirst().getHand().get(0).getCornerAtSE().getCoordinate());
        System.out.println(model.getAllPlayers().getFirst().getHand().get(1).getCornerAtNE());
        System.out.println(model.getAllPlayers().getFirst().getPersonalBoard().getBoard()[251][252].getCornerFromCell().getCoordinate());
        System.out.println(model.getAllPlayers().getFirst().getHand().get(1).getCornerAtNE().getCoordinate());
        System.out.println(model.getAllPlayers().getFirst().getHand().size());
        model.getAllPlayers().getFirst().removeFromHand(model.getAllPlayers().getFirst().getHand().get(1));
        assertEquals(2, model.getAllPlayers().getFirst().getHand().size());

        model.setStatus(GameStatus.RUNNING);

        model.drawCard(model.getAllPlayers().getFirst(), 6);
        assertEquals(3, model.getAllPlayers().getFirst().getHand().size());

        System.out.println(model.getAllPlayers().getFirst().getHand().get(0));

        model.placeCard(model.getAllPlayers().getFirst().getHand().get(0), model.getAllPlayers().getFirst(), 251, 252);
        assertEquals(2, model.getAllPlayers().getFirst().getPersonalBoard().getBoard()[251][252].getLevel());
        assertEquals(1, model.getAllPlayers().getFirst().getPersonalBoard().getBoard()[252][252].getLevel());
        assertEquals(1, model.getAllPlayers().getFirst().getPersonalBoard().getPoints());
        model.getAllPlayers().getFirst().removeFromHand(model.getAllPlayers().getFirst().getHand().get(0));
        assertEquals(2, model.getAllPlayers().getFirst().getHand().size());

        System.out.println(model.getAllPlayers().getFirst().getHand().get(0));
        assertEquals(2, model.getAllPlayers().getFirst().getPersonalBoard().getNum_butterflies());

        model.getAllPlayers().getFirst().removeFromHand(model.getAllPlayers().getFirst().getHand().get(0));
        assertEquals(1, model.getAllPlayers().getFirst().getHand().size());

        model.drawCard(model.getAllPlayers().getFirst(), 6);
        assertEquals(2, model.getAllPlayers().getFirst().getHand().size());

        System.out.println(model.getAllPlayers().getFirst().getHand().get(0));
        System.out.println(model.getAllPlayers().getFirst().getHand().get(1));

        //GoldCard on ResourceCard
        //model.placeCard((GoldCard) model.getAllPlayers().getFirst().getHand().get(1), model.getAllPlayers().getFirst(), 501, 503);
        //assertEquals(2,model.getAllPlayers().getFirst().getPersonalBoard().getBoard()[501][503].getLevel());

        //GoldCard on GoldCard
        model.drawCard(model.getAllPlayers().getFirst(), 4);
        System.out.println(model.getAllPlayers().getFirst().getHand().get(2));

        //GoldCard on StarterCard
        model.placeCard((GoldCard) model.getAllPlayers().getFirst().getHand().get(2), model.getAllPlayers().getFirst(), 250, 250);
        //assertThrows(IllegalMoveException.class, ()-> model.placeCard((GoldCard) model.getAllPlayers().getFirst().getHand().get(2), model.getAllPlayers().getFirst(), 500, 500));
        assertEquals(2, model.getAllPlayers().getFirst().getPersonalBoard().getBoard()[250][250].getLevel());
        System.out.println("*********");
        //assertEquals(3, model.getAllPlayers().getFirst().getPersonalBoard().getPoints());
        System.out.println(((GoldCard) model.getAllPlayers().getFirst().getHand().get(2)).getIsPotionRequired());

        assertEquals(1, model.getAllPlayers().getFirst().getPersonalBoard().getNum_leaves());
        assertEquals(2, model.getAllPlayers().getFirst().getPersonalBoard().getNum_potions());
        assertEquals(1, model.getAllPlayers().getFirst().getPersonalBoard().getNum_wolves());
        assertEquals(2, model.getAllPlayers().getFirst().getPersonalBoard().getNum_butterflies());
        assertEquals(0, model.getAllPlayers().getFirst().getPersonalBoard().getNum_mushrooms());
        assertEquals(0, model.getAllPlayers().getFirst().getPersonalBoard().getNum_parchments());

        System.out.println(model.getCommonBoard().getCommonObjectives());

        //model.calculateFinalScores();
        model.declareWinners();
        System.out.println(model.getAllPlayers().getFirst().getFinalScore());
        System.out.println(model.getWinners().getFirst().getNickname());
//        assertEquals(3,model.getAllPlayers().getFirst().getPersonalBoard().getPoints());
        assertEquals(0, model.getCommonBoard().getCommonObjectives().getFirst().calculateScore(model.getAllPlayers().getFirst().getPersonalBoard()));
        assertEquals(2, model.getCommonBoard().getCommonObjectives().get(1).calculateScore(model.getAllPlayers().getFirst().getPersonalBoard()));
        assertEquals(0, model.getAllPlayers().getFirst().getChosenObjectiveCard().calculateScore(model.getAllPlayers().getFirst().getPersonalBoard()));

//
//
//
//        //Test for drawCard
//        model.drawCard(model.getAllPlayers().getFirst(), 1);
//        assertEquals(4,model.getAllPlayers().getFirst().getHand().size());
//        model.drawCard(model.getAllPlayers().getFirst(), 2);
//        assertEquals(5,model.getAllPlayers().getFirst().getHand().size());
//        model.drawCard(model.getAllPlayers().getFirst(), 3);
//        assertEquals(6,model.getAllPlayers().getFirst().getHand().size());
//        model.drawCard(model.getAllPlayers().getFirst(), 4);
//        assertEquals(7,model.getAllPlayers().getFirst().getHand().size());
//        model.drawCard(model.getAllPlayers().getFirst(), 5);
//        assertEquals(8,model.getAllPlayers().getFirst().getHand().size());
//
//        //test for nextTurn
//        model.getAllPlayers().getFirst().setAsConnected();
//        model.setPlayerAsConnected(model.getAllPlayers().getFirst());
//        model.getAllPlayers().get(1).setAsConnected();
//        model.setPlayerAsConnected(model.getAllPlayers().get(1));
//        model.getAllPlayers().get(2).setAsConnected();
//        model.setPlayerAsConnected(model.getAllPlayers().get(2));
//
//
//        System.out.println(model.getPlayersConnected().getFirst().getNickname());
//        System.out.println(model.getPlayersConnected().get(1).getNickname());
//        System.out.println(model.getPlayersConnected().get(2).getNickname());
//
//        System.out.println(model.getStatus());
//
//        model.setStatus(GameStatus.RUNNING);
//
//
//        model.nextTurn();
//        System.out.println(model.getPlayersConnected().getFirst().getNickname());
//        System.out.println(model.getPlayersConnected().get(1).getNickname());
//        System.out.println(model.getPlayersConnected().get(2).getNickname());
//
//        model.setStatus(GameStatus.WAIT);
//        assertThrows(GameNotStartedException.class, ()-> model.nextTurn());
//
//        model.setStatus(GameStatus.ENDED);
//        assertThrows(GameEndedException.class, ()-> model.nextTurn());
//
//
//
//        System.out.println("*********");
//        //Test for declareWinners
//        System.out.println(model.getWinners().getFirst().getNickname());



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
//        //model.addPlayer("Player1");
//        //model.addPlayer("Player2");
//        //model.addPlayer("Player3");
//        model.getAllPlayers().getFirst().setAsConnected();
//        model.setPlayerAsConnected(model.getAllPlayers().getFirst());
//        model.getAllPlayers().get(1).setAsConnected();
//        model.setPlayerAsConnected(model.getAllPlayers().get(1));
//        model.getAllPlayers().get(2).setAsConnected();
//        model.setPlayerAsConnected(model.getAllPlayers().get(2));
//
//        model.setStatus(GameStatus.RUNNING);
//        GameStatus expectedStatus = GameStatus.RUNNING;
//        assertEquals(expectedStatus ,model.getStatus());
//
//    }



}
