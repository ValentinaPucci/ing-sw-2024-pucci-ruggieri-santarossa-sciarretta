package it.polimi.ingsw.model;

import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class Game {
    private Queue<Player> playerQueue;
    private int currentPlayerIndex;
    private Player firstPlayer;
    private boolean gameOver;
    private CommonBoard common_board;
    private ConcreteDeck resource_deck;
    private ConcreteDeck gold_deck;


    public Game() {
        common_board = new CommonBoard(); //initializes the board
        dealCards();
        while (!isGameOver()) {
            // Loop through players and handle each player's turn
            for (Player player : playerQueue) {
                placeCard(chooseCardToPlayFromHand());
                updatePersonalScore(currentPlayerIndex);
                drawCard(); //TODO: pesco da terra o dal mazzo a seconda di cosa sceglie l'utente
            }
            gameOver = isGameOver();
        }
        lastTurn();
        calculateFinalScores();


        // Perform end game actions (e.g., display winner)
    }

    public void addPlayer(Player player) {
        playerQueue.add(player);
    }


    public void dealCards() {
        // Deal cards to players
        for (Player player : playerQueue) {
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
        return currentPlayerIndex;
    }


    public void calculateFinalScores() {
        // Calculate final scores
        //Riceve da commonBoard le posizioni dell'ultimo giro + da personalBoard i punteggi degli obiettivi, per ciascun giocatore
        //somma per ogni giocatore
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Card chooseCardToPlayFromHand() {

    }

    public void placeCard(Card card_chosen){

    }

    public void updatePersonalScore(int currentPlayerIndex){

    }

    public void drawCard(){
       //pattern dividere tra pesca da mazzo o dal banco (metodi già scritti in CommonBoard)
    }

    public void lastTurn(){
        //gestisce il settaggio del partial_winner + tiene conto del fatto che sto giocando l'ultimo turno
        //movePlayer (In commonBard notifica quando si arriva a 20, il game fa finire il turno corrente, ne fa fare un'altro completo
        //e poi arresta il gioco
    }


    // Additional methods and logic as needed
}

