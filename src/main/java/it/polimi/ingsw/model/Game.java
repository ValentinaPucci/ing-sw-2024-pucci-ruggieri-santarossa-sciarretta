package it.polimi.ingsw.model;

import java.util.*;



public class Game {
    private Queue<Player> player_queue;
    private Player[] players;
    private int num_players;
    private boolean game_over;
    private boolean last_turn;
    private CommonBoard common_board;
    private ConcreteDeck resource_deck;
    private ConcreteDeck gold_deck;
    private ConcreteDeck objective_deck;
    private ConcreteDeck starter_deck;
    private int[] final_scores;
    private Player winner;
    private Player temp_winner;
    private Coordinate coordinate;
    private ResourceCard already_placed_card;
    private int from_where_draw;
    private int from_which_deckindex;
    private int col;
    private int row;




    public Game(CommonBoard common_board, Player[] players) {
        this.player_queue = new LinkedList<>();
        this.players = players;
        this.num_players = players.length;
        this.game_over = false;
        this.last_turn = false;
        this.final_scores = new int[num_players];
        this.common_board = common_board;
        this.resource_deck = common_board.getResourceConcreteDeck();
        this.gold_deck = common_board.getGoldConcreteDeck();
        this.starter_deck = common_board.getStarterConcreteDeck();
        this.objective_deck = common_board.getObjectiveConcreteDeck();
        // Aggiunta dei giocatori alla coda dei giocatori
        for (Player player : players) {
            this.player_queue.offer(player);
        }
        this.coordinate = Coordinate.NE;
        this.from_where_draw = 0;
        this.from_which_deckindex = 0;
        this.col = 0;
        this.row = 0;;

    }

    public void gameFlow(){
        initializeGame();
        for(Player player: player_queue){
            player.playStarterCard();
        }
        while (!isGameOver()) {
            while (!isLastTurn()) {
                // Loop through players and handle each player's turn
                for (Player player : player_queue) {
                    placeCard(player.getChosenGameCard(), player.getPersonalBoard(), coordinate, already_placed_card);
                    PersonalBoard current_board = player.getPersonalBoard();
                    int delta = current_board.getDeltaPoints();
                    common_board.movePlayer(player.getId(), delta);
                    player.addToHand(drawCard(from_where_draw));
                    if(common_board.getPartialWinner() != -1)
                        last_turn = true;
                }
            }
            secondLastTurn();
        }
        calculateFinalScores();
        setWinner();
    }

    public void initializeGame(){
        common_board.initializeBoard();
        dealCards();
    }


    public void dealCards() {
        // Deal cards to players
        for (Player player : player_queue) {
            if (!starter_deck.isEmpty()) {
                StarterCard card = (StarterCard) starter_deck.pop(); // Remove the top card from the gold deck
                //player.setStarterCard(card); // Add the card to the player's hand
            }
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


    public void calculateFinalScores() {
        for(int i= 0; i<players.length - 1; i++)
            final_scores[i] = common_board.getPlayerPosition(i)+ players[i].getPersonalBoard().getPoints();
    }

    public void placeCard(ResourceCard card_chosen, PersonalBoard personal_board, Coordinate coordinate, ResourceCard already_placed_card) {
        switch (coordinate){
            case NE:
                personal_board.placeCardAtNE(already_placed_card, card_chosen);
            case SE:
                personal_board.placeCardAtSE(already_placed_card, card_chosen);
            case SW:
                personal_board.placeCardAtSW(already_placed_card, card_chosen);
            case NW:
                personal_board.placeCardAtNW(already_placed_card, card_chosen);
        }

    }
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
    public void setAlreadyPlacedCard(ResourceCard card) {
        this.already_placed_card = card;
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

    public Card drawCard(int where){
        switch (where){
            case 0:
                return common_board.drawFromConcreteDeck(from_which_deckindex);
            case 1:
                return common_board.drawFromTable(row, col, from_which_deckindex);
        }
        return null;
    }

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


    public Player getWinner(){
        return winner;
    }

    public void setWinner(){
        int temp_score = 0;
        temp_winner = players[0];

        for(int i= 0; i<players.length - 1; i++)
            if (final_scores[i] > temp_score) {
                temp_score = final_scores[i];
                temp_winner = players[i];
            }
        this.winner = temp_winner;
    }

}

