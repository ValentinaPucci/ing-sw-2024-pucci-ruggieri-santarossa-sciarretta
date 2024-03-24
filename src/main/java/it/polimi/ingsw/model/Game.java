package it.polimi.ingsw.model;

import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

import static it.polimi.ingsw.model.Coordinate.NE;

public class Game {
    private Queue<Player> player_queue;
    private Player[] players;
    private int num_players;
    private Player first_player;
    private boolean game_over;
    private boolean last_turn;
    private CommonBoard common_board;
    private ConcreteDeck resource_deck;
    private ConcreteDeck gold_deck;
    private ConcreteDeck objective_deck;
    private Coordinate coordinate;
    private ResourceCard already_placed_card;
    private StarterCard starter_card;
    private int from_where_draw;
    private int from_which_deckindex;
    private int col;
    private int row;




    public Game(CommonBoard common_board, Player[] players) {
        player_queue = new LinkedList<>();
        this.players = players;
        this.first_player = players[0];
        this.num_players = players.length-1;
        this.game_over = false;
        this.last_turn = false;
        this.common_board = common_board; //initializes the board
        this.resource_deck = common_board.getResourseConcreteDeck();
        this.gold_deck = common_board.getGoldConcreteDeck();
        this.objective_deck = common_board.getObjectiveConcreteDeck();
        this.coordinate = NE;
        this.from_where_draw = 0;
        this.from_which_deckindex = 0;
        this.col = 0;
        this.row = 0;;

    }

    public void gameFlow(){
        initializeGame();
        for(Player player: player_queue){
            //TODO: metodo per piazzare starter card
        }
        while (!isGameOver()) {
            while (!isLastTurn()) {
                // Loop through players and handle each player's turn
                for (Player player : player_queue) {
                    placeCard(player.getChosenGameCard(), player.getPersonalBoard(), coordinate, already_placed_card);
                    PersonalBoard current_board = player.getPersonalBoard();
                    int delta = current_board.getDeltaPoints();
                    common_board.movePlayer(player.getId(), delta);
                    drawCard(from_where_draw);
                    if(common_board.getPartialWinner() != -1)
                        last_turn = true;
                }
            }
            secondLastTurn();
        }
        calculateFinalScores();
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

            for (int i = 0; i < 2; i++) {
                if (!objective_deck.isEmpty()) {
                    ObjectiveCard card1 = (ObjectiveCard)objective_deck.pop();
                    ObjectiveCard card2 = (ObjectiveCard)objective_deck.pop();
                    player.setSecretObjectives(card1, card2);
                }
            }
        }
    }


    public void fillQueue(Queue<Player> player_queue, Player[] players){
        if ( players== null || player_queue == null) {
            throw new IllegalArgumentException("Array o coda nulli non sono ammessi.");
        }
        for (Player element : players) {
            player_queue.add(element);
        }
    }


    public void calculateFinalScores() {

    }

    public void placeCard(ResourceCard card_chosen, PersonalBoard personal_board, Coordinate coordinate, ResourceCard already_placed_card) {
        switch (coordinate){
            case NE:
                personal_board.placeCardAtNE(already_placed_card, card_chosen);
            case SE:
                personal_board.placeCardAtSE(already_placed_card, card_chosen);
            case SW:
                personal_board.placeCardAtSO(already_placed_card, card_chosen);
            case NW:
                personal_board.placeCardAtNO(already_placed_card, card_chosen);
        }

    }
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
    public void setAlreadyPlacedCard(ResourceCard card) {
        this.already_placed_card = card;
    }



    public void drawCard(int where){
        switch (where){
            case 0:
                common_board.drawFromConcreteDeck(from_which_deckindex);
            case 1:
                common_board.drawFromTable(row, col, from_which_deckindex);
        }
    }

    public void setFromWhereDraw(int where) {
        this.from_where_draw = where;
        //Convention to avoid creating another enum
        //0: from concrete deck
        //1: from table
    }

    public void setFromConcreteDeck(int index) {
        this.from_which_deckindex = index;
    }

    public void setFromTable(int row, int col, int index) {
        this.row = row;
        this.col = col;
        this.from_which_deckindex = index;}



    public boolean isLastTurn() {
        return last_turn;
    }

    public void lastTurn(){
        for (int i = 0; i < num_players; i++) {
            Player currentPlayer = player_queue.poll();
            //il giocatore gioca
            player_queue.offer(currentPlayer); // Rimetti il giocatore corrente in fondo alla coda
        }
        game_over = true;
    }

    public void secondLastTurn(){
        for (int i = common_board.getPartialWinner(); i < num_players; i++) {
            Player currentPlayer = player_queue.poll();
            //il giocatore gioca
            player_queue.offer(currentPlayer); // Rimetti il giocatore corrente in fondo alla coda
        }
        lastTurn();
    }

    public boolean isGameOver() {
        return game_over;
    }
}

