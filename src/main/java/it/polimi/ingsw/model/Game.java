package it.polimi.ingsw.model;

import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class Game {
    private Queue<Player> player_queue;

    private Player[] players;
    private int current_player_index;
    private int num_players;
    private Player current_player;
    private Player first_player;
    private boolean game_over;
    private CommonBoard common_board;
    private ConcreteDeck resource_deck;
    private ConcreteDeck gold_deck;


    public Game(CommonBoard common_board, Player[] players) {
        player_queue = new LinkedList<>();
        this.players = players;
        this.current_player_index = 0;
        this.current_player = players[0];
        this.first_player = players[0];
        this.num_players = players.length-1;
        this.game_over = false;
        this.common_board = common_board; //initializes the board
        this.resource_deck = common_board.getResourseConcreteDeck();
        this.gold_deck = common_board.getGoldConcreteDeck();

    }

    public void gameFlow(){
        initializeGame();
        while (!isGameOver()) {
            // Loop through players and handle each player's turn
            for (Player player : player_queue) {
                //placeCard(chooseCardToPlayFromHand());
                updatePersonalScore(current_player_index);
                PersonalBoard current_board = current_player.getPersonalBoard();
                int delta = current_board.getDeltaPoints();
                common_board.movePlayer(current_player_index, delta);
                drawCard(); //TODO: pesco da terra o dal mazzo a seconda di cosa sceglie l'utente
            }
            game_over = isGameOver();
        }
        LastTurn();
        calculateFinalScores();
    }

    public void addPlayer(Player player) {
        player_queue.add(player);
    }

    public void initializeGame(){
        common_board.initializeBoard();
        dealCards();
        fillQueue(player_queue, players);


    }


    public void dealCards() {
        // Deal cards to players
        for (Player player : player_queue) {
            // Deal 2 cards from the resource deck
            for (int i = 0; i < 2; i++) {
                if (!resource_deck.isEmpty()) {
                    Card card = resource_deck.pop(); // Remove the top card from the resource deck
                    player.addToHand(card); // Add the card to the player's hand
                }
            }

            // Deal 1 card from the gold deck
            if (!gold_deck.isEmpty()) {
                Card card = gold_deck.pop(); // Remove the top card from the gold deck
                player.addToHand(card); // Add the card to the player's hand
            }

            //TODO: capire se vogliamo prendere 2 obiettivi dal deck obiettivi o scegliere 2 carte a caso
        }
    }


    public int getCurrentPlayer() {
        return current_player_index;
    }

    public void fillQueue(Queue<Player> player_queue, Player[] players){

        // Controlla se l'array o la coda sono nulli
        if ( players== null || player_queue == null) {
            throw new IllegalArgumentException("Array o coda nulli non sono ammessi.");
        }

            // Aggiunge gli elementi dell'array alla coda
        for (Player element : players) {
            player_queue.add(element);
        }

    }


    public void calculateFinalScores() {
        // Calculate final scores
        //Riceve da commonBoard le posizioni dell'ultimo giro + da personalBoard i punteggi degli obiettivi, per ciascun giocatore
        //somma per ogni giocatore
    }

    public boolean isGameOver() {
        return game_over;
    }

//    public Card chooseCardToPlayFromHand() {
//
//    }

    public void placeCard(Card card_chosen) {

    }

    public void updatePersonalScore(int currentPlayerIndex){

    }

    public void drawCard(){
       //pattern dividere tra pesca da mazzo o dal banco (metodi già scritti in CommonBoard)
    }
    public void LastTurn(){
        for (int i = 0; i < num_players; i++) {
            Player currentPlayer = player_queue.poll();
            //il giocatore gioca
            player_queue.offer(currentPlayer); // Rimetti il giocatore corrente in fondo alla coda
        }
    }


    public void secondLastTurn(){
        for (int i = current_player_index; i < num_players; i++) {
            Player currentPlayer = player_queue.poll();
            //il giocatore gioca
            player_queue.offer(currentPlayer); // Rimetti il giocatore corrente in fondo alla coda
        }
        LastTurn();
    }



    // Additional methods and logic as needed
}

