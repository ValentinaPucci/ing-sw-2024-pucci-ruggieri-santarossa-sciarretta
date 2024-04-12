package it.polimi.ingsw.model;

import it.polimi.ingsw.listener.GameListener;
import it.polimi.ingsw.listener.ListenersHandler;
import it.polimi.ingsw.model.board.CommonBoard;
import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.ingsw.model.cards.gameCards.ResourceCard;
import it.polimi.ingsw.model.cards.gameCards.StarterCard;
import it.polimi.ingsw.model.chat.Chat;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumerations.Coordinate;
import it.polimi.ingsw.model.enumerations.GameStatus;
import it.polimi.ingsw.model.exceptions.*;
import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.ingsw.networking.PrintAsync.printAsync;

public class GameModel {
    /**
     * It maps the players' indexes in the queue with their position on the score board
     * 2,1 means the second player came in first place
     */
    private Map<Integer, Integer> leaderBoard;
    private List<Player> players;
    private ConcreteDeck resource_deck;
    private ConcreteDeck gold_deck;
    private ConcreteDeck objective_deck;
    private ConcreteDeck starter_deck;
    private Integer gameId;
    private CommonBoard common_board;
    private Integer current_player;
    private Integer firstTurnIndex = -1;
    private Chat chat;
    private GameStatus status;
    private Integer first_finished_player = -1;
    private ListenersHandler listeners_handler;
    private Queue<Player> player_queue;
    private int num_players;
    //private boolean game_over;
    private boolean second_last_turn;
    private int[] final_scores;
    private Player[] winners;
    private Coordinate coordinate;
    private ResourceCard already_placed_card;
    private int from_where_draw;
    private int from_which_deckindex;
    private int col;
    private int row;


    public GameModel(CommonBoard common_board, List<Player> players) {

        Random random = new Random();
        gameId = random.nextInt(1000000);
        status = GameStatus.WAIT;
        chat = new Chat();
        listeners_handler = new ListenersHandler();

        this.player_queue = new LinkedList<>();
        this.players = players;
        this.num_players = players.size();
        //this.game_over = false;
        this.second_last_turn = false;
        this.final_scores = new int[num_players];
        this.common_board = common_board;
        this.resource_deck = common_board.getResourceConcreteDeck();
        this.gold_deck = common_board.getGoldConcreteDeck();
        this.starter_deck = common_board.getStarterConcreteDeck();
        this.objective_deck = common_board.getObjectiveConcreteDeck();
        this.current_player = -1;
        for (Player player : players) {
            this.player_queue.offer(player);
        }
        this.coordinate = Coordinate.NE;
        this.from_where_draw = 0;
        this.from_which_deckindex = 0;
        this.col = 0;
        this.row = 0;;
        this.winners = new Player[0];

    }


    public GameModel() {
        players = new ArrayList<>();
        Random random = new Random();
        gameId = random.nextInt(10000000);
        //common_board = new CommonBoard(); PROBLEMA CON IL PASSAGGIO PARAMETRI AL COSTRUTTORE
        current_player = -1;
        status = GameStatus.WAIT;
        chat = new Chat();

        listeners_handler = new ListenersHandler();


    }

//    public void gameFlow() {
//
//        initializeGame();
//
//        for (int i = 0; i < num_players; i++) {
//            Player currentPlayer = player_queue.poll(); // Get and remove the first player from the queue
//            assert currentPlayer != null;
//            currentPlayer.playStarterCard(); // Play the starter card for the current player
//            player_queue.offer(currentPlayer); // Add the current player back to the end of the queue
//        }
//
//        while (!isGameOver()) {
//            while (!isSecondLastTurn()) {
//                    Player current_player = player_queue.poll(); // Get and remove the first player from the queue
//                    assert current_player != null;
//                    int prec_points = current_player.getPersonalBoard().getPoints();
//                    placeCard(current_player.getChosenGameCard(),
//                            current_player.getPersonalBoard(),
//                            coordinate,
//                            already_placed_card);
//                    int current_points = current_player.getPersonalBoard().getPoints();
//                    int delta = current_points - prec_points;
//                    common_board.movePlayer(current_player.getId(), delta);
//                    current_player.addToHand(drawCard(from_where_draw));
//                    player_queue.offer(current_player); // Add the current player back to the end of the queue
//                    if (common_board.getPartialWinner() != -1)
//                        second_last_turn = true;
//                    if (common_board.getGoldConcreteDeck().isEmpty() &&
//                            common_board.getResourceConcreteDeck().isEmpty()){
//                        second_last_turn = true;
//                        common_board.setPartialWinner(current_player.getId());
//                    }
//            }
//            secondLastTurn();
//        }
//        calculateFinalScores();
//        setWinner();
//    }

    //-------------------------gestione dei players---------------------------------------------

    /**
     * @return the number of players
     */
    public int getNumOfPlayers() {
        return players.size();
    }

    /**
     * @return player's list
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @return the number of player's connected
     */
    public int getNumOfOnlinePlayers() {
        int connectedPlayersCount = 0;
        for (Player player : players) {
            if (player.isConnected()) {
                connectedPlayersCount++;
            }
        }
        return connectedPlayersCount;
    }

    public void initializeGame(){
        common_board.initializeBoard();
        dealCards();
    }

    /**
     * add a player to the game<br>
     *
     * @param p player to add
     * @throws PlayerAlreadyConnectedException if the player is already in the game
     * @throws MaxPlayersLimitException    if the game is full
     */
    public void addPlayer(Player p) throws PlayerAlreadyConnectedException, MaxPlayersLimitException {
        // First I check that the player is not already in the game
        // then I check if the game is already full
        if (!players.contains(p)) {
            if (players.size() < DefaultValues.MaxNumOfPlayer) {
                players.add(p);
                listeners_handler.notify_playerJoined(this);
            } else {
                listeners_handler.notify_JoinUnableGameFull(p, this);
                throw new MaxPlayersLimitException();
            }
        } else {
            listeners_handler.notify_JoinUnableNicknameAlreadyIn(p);
            throw new PlayerAlreadyConnectedException();
        }

    }

    /**
     * @param nick removes this player from the game
     */
    public void removePlayer(String nick) {
        players.remove(players.stream().filter(x -> x.getNickname().equals(nick)).toList().get(0));
        listeners_handler.notify_playerLeft(this, nick);

        if (this.status.equals(GameStatus.RUNNING) &&
                players.stream()
                        .filter(Player::isConnected)
                        .toList()
                        .size() <= 1) {
            // Not enough players to keep playing
            this.setStatus(GameStatus.ENDED);
        }
    }

    /**
     * @param p player is reconnected
     * @throws PlayerAlreadyConnectedException player is already in
     * @throws MaxPlayersLimitException there's already 4 players in game
     * @throws GameEndedException the game has ended
     */

    public boolean reconnectPlayer(Player p) throws PlayerAlreadyConnectedException, MaxPlayersLimitException, GameEndedException {

        Player pIn = players.stream()
                .filter(x -> x.equals(p))
                .toList()
                .get(0);

        if (!pIn.isConnected()) {
            pIn.setConnected(true);
            listeners_handler.notify_playerReconnected(this, p.getNickname());

            //if (!isTheCurrentPlayerOnline()) {
            //nextTurn();
            //}
            return true;

        } else {
            printAsync("ERROR: Trying to reconnect a player not offline!");
            return false;
        }
    }

    /**
     * @param p is set as ready, then everyone is notified
     */
    public void playerIsReadyToStart(Player p) {
        p.setReadyToStart();
        listeners_handler.notify_PlayerIsReadyToStart(this, p.getNickname());
    }

    /**
     * @return true if there are enough players to start, and if every one of them is ready
     */
    public boolean arePlayersReadyToStartAndEnough() {
        //If every player is ready, the game starts
        return players.stream().filter(Player::getReadyToStart)
                .count() == players.size() && players.size() >= DefaultValues.MinNumOfPlayer;
    }


    public Integer getCurrentPlayer() {
        return this.current_player;
    }

    /**
     * @param playerNick
     * @return player by nickname
     */
    public Player getPlayerEntity(String playerNick) {
        List<Player> ris = players.stream().filter(x -> x.getNickname().equals(playerNick)).toList();
        if(ris.size()>0){
            return ris.get(0);
        }
        return null;
    }


    /**
     * @param nick player to set as disconnected
     */
    public void setAsDisconnected(String nick) {
        getPlayerEntity(nick).setConnected(false);
        getPlayerEntity(nick).setNotReadyToStart();
        if (getNumOfOnlinePlayers() != 0) {
            listeners_handler.notify_playerDisconnected(this, nick);

//            if (getNumOfOnlinePlayers() != 1 && !isTheCurrentPlayerOnline()) {
//                try {
//                    nextTurn();
//                } catch (GameEndedException e) {
//
//                }
//            }
            if ((this.status.equals(GameStatus.RUNNING) || this.status.equals(GameStatus.LAST_TURN)) && getNumOfOnlinePlayers() == 1) {
                listeners_handler.notify_onlyOnePlayerConnected(this, DefaultValues.secondsToWaitReconnection);
            }
        }//else the game is empty
    }

    /**
     * Finds the players with most points, adds to thew leaderboard and then returns it
     */
    private List<Player> findWinner() {
        int max = Integer.MIN_VALUE;
        List<Player> winners = new ArrayList<>();
        leaderBoard = new HashMap<>();
        Map<Integer, Integer> temp = new HashMap<>();

        // Cicla attraverso ogni punteggio dei giocatori e trova il punteggio massimo
        for (int i = 0; i < getNumOfPlayers(); i++) {
            Player currentPlayer = getPlayers().get(i);
            int point = currentPlayer.getFinalScore();
            if (point > max) {
                max = point;
                winners.clear(); // Svuota la lista dei vincitori precedenti
                winners.add(currentPlayer); // Aggiungi il giocatore corrente come vincitore
            } else if (point == max) {
                // Se il giocatore ha lo stesso punteggio massimo, aggiungilo alla lista dei vincitori
                winners.add(currentPlayer);
            }
            temp.put(i, point);
        }

        // Ordina temp e mette i vincitori nella prima posizione del leaderboard
        leaderBoard = temp.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return winners;
    }

    /**
     * @return true if the player in turn is online
     */
    private boolean isTheCurrentPlayerOnline() {
        return players.get(current_player).isConnected();
    }

    /**
     * Sets the current playing player to the param
     *
     * @param current_player active playing player
     */
    public void setCurrentPlayer(Integer current_player) {
        this.current_player = current_player;
    }

    //-------------------------gestione listeners---------------------------------------------

    /**
     * @param obj adds the listener to the list
     */
    public void addListener(GameListener obj) {
        listeners_handler.addListener(obj);
    }

    /**
     * @param lis removes listener from list
     */
    public void removeListener(GameListener lis) {
        listeners_handler.removeListener(lis);
    }

    /**
     * @return the list of listeners
     */
    public List<GameListener> getListeners() {
        return listeners_handler.getListeners();
    }



    //-------------------------gestione chat e messaggi---------------------------------------------


    public Chat getChat() {
        return this.chat;
    }

    /**
     * Sends a message
     *
     * @param m message sent
     */
    public void sentMessage(Message m) {
        if (players.stream().filter(x -> x.equals(m.getSender())).count() == 1) {
            chat.addMsg(m);
            listeners_handler.notify_SentMessage(this, chat.getLastMessage());
        } else {
            throw new ActionPerformedByAPlayerNotPlayingException();
        }

    }
    //-------------------------gestione status---------------------------------------------



    public GameStatus getStatus() {
        return this.status;
    }

    /**
     * Sets the game status
     *
     * @param status
     */
    public void setStatus(GameStatus status) {
        //If I want to set the gameStatus to "RUNNING", there needs to be at least
        // DefaultValue.minNumberOfPlayers -> (2) in lobby
        if (status.equals(GameStatus.RUNNING) &&
                ((players.size() < DefaultValues.MinNumOfPlayer)
                        || current_player == -1)) {
            throw new NotReadyToRunException();
        } else {
            this.status = status;

            if (status == GameStatus.RUNNING) {
                listeners_handler.notify_GameStarted(this);
                listeners_handler.notify_nextTurn(this);
            } else if (status == GameStatus.ENDED) {
                //findWinner(); //Find winner
                listeners_handler.notify_GameEnded(this);
            } else if (status == GameStatus.LAST_TURN) {
                listeners_handler.notify_LastTurn(this);
            }
        }
    }

    //-------------------------gestione della logica di gioco---------------------------------------------

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

    public void placeCard(ResourceCard card_chosen,
                          PersonalBoard personal_board,
                          Coordinate coordinate,
                          ResourceCard already_placed_card) {
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
    public void setDrawCardFromConcreteDeck(int index) {
        this.from_which_deckindex = index;
    }

    public void setDrawCardFromTable(int row, int col, int index) {
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


     public void lastTurn() {
     for (int i = 0; i < num_players; i++) {
         Player current_player = player_queue.poll();
         assert current_player != null;
         int prec_points = current_player.getPersonalBoard().getPoints();

         placeCard(current_player.getChosenGameCard(), current_player.getPersonalBoard(), coordinate, already_placed_card);
         int current_points = current_player.getPersonalBoard().getPoints();
         int delta = current_points - prec_points;
         common_board.movePlayer(current_player.getId(), delta);
         player_queue.offer(current_player);
     }
         status = GameStatus.ENDED;
 }

 public void secondLastTurn() {
     for (int i = common_board.getPartialWinner(); i < num_players; i++)  {
         Player current_player = player_queue.poll();
         assert current_player != null;
         int prec_points = current_player.getPersonalBoard().getPoints();
         placeCard(current_player.getChosenGameCard(), current_player.getPersonalBoard(), coordinate, already_placed_card);
         int current_points = current_player.getPersonalBoard().getPoints();
         int delta = current_points - prec_points;
         common_board.movePlayer(current_player.getId(), delta);
         if(!common_board.getResourceConcreteDeck().isEmpty() && !common_board.getGoldConcreteDeck().isEmpty())
             current_player.addToHand(drawCard(from_where_draw));
         player_queue.offer(current_player);
     }
     lastTurn();
 }

    public void calculateFinalScores(){
        for(int i = 0; i < num_players; i++){
            this.final_scores[i] = players.get(i).getPersonalBoard().getPoints() +
                    players.get(i).getChosenObjectiveCard().calculateScore(players.get(i).getPersonalBoard()) +
                    common_board.getCommonObjectives()[0].calculateScore(players.get(i).getPersonalBoard()) +
                    common_board.getCommonObjectives()[1].calculateScore(players.get(i).getPersonalBoard());
        }
    }

    public int getFinalScore(int player_index){
        return final_scores[player_index];
    }

    public Integer getGameId() {
        return this.gameId;
    }

    /**
     * Sets the game id
     *
     * @param gameId new game id
     */
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }




//TODO: da adattare al nostro next turn
    /**
     * Calls the next turn
     *
     * @throws GameEndedException
     */
    public void nextTurn() throws GameEndedException {
        if (status.equals(GameStatus.WAIT)) {
            int current = firstTurnIndex;
            players.get(current).playStarterCard();
        }
        if (getNumOfOnlinePlayers() != 1) {
            //I skip the disconnected players and I let play only the connected ones
            do {
                current_player = (current_player+ 1) % players.size();
            } while (!players.get(current_player).isConnected());
        } else {
            //Only one player connected, I set the nextTurn to the next player of the one online
            //when someone will reconnect, the nextTurn will be corrected
            current_player = (current_player + 1) % players.size();
        }

        if (status.equals(GameStatus.RUNNING)) {
            if (isTheCurrentPlayerOnline()) {
                players.get(current_player).getChosenGameCard();
                //players.get(current_player).placeCard();

                int oldCurrent = current_player;
                if (getNumOfOnlinePlayers() != 1) {
                    //I skip the disconnected players and I let play only the connected ones
                    do {
                        current_player = (current_player + 1) % players.size();
                    } while (!players.get(current_player).isConnected());
                } else {
                    //Only one player connected, I set the nextTurn to the next player of the one online
                    //when someone will reconnect, the nextTurn will be corrected
                    current_player = (current_player + 1) % players.size();
                }

                if (first_finished_player != -1 && oldCurrent == firstTurnIndex) {
                    throw new GameEndedException();
                } else {
                    listeners_handler.notify_nextTurn(this);
                }

            } else if (status.equals(GameStatus.LAST_TURN)) {
                throw new GameEndedException();
            } else if (status.equals(GameStatus.ENDED)) {
                throw new GameEndedException();
            } else {
                throw new GameNotStartedException();
            }
        }

    }


    /**
     * Set the index of the player playing the first turn
     *
     * @param index of the player
     */
    public void setFirstTurnIndex(int index) {
        this.firstTurnIndex = index;
    }

    /**
     * @return the index of the first player playing
     */
    public Integer getFirstTurnIndex() {
        return this.firstTurnIndex;
    }


    public Integer getFirstFinishingPlayer() {
        return this.first_finished_player;
    }

    public ListenersHandler getListenersHandler() {
        return this.listeners_handler;
    }

    public Queue<Player> getPlayerQueue() {
        return this.player_queue;
    }

    public CommonBoard getCommonBoard() {
        return this.common_board;
    }

    public ConcreteDeck getResourceDeck() {
        return this.resource_deck;
    }

    public ConcreteDeck getGoldDeck() {
        return this.gold_deck;
    }

    public ConcreteDeck getObjectiveDeck() {
        return this.objective_deck;
    }

    public ConcreteDeck getStarterDeck() {
        return this.starter_deck;
    }

    public int[] getFinalScores() {
        return this.final_scores;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public ResourceCard getAlreadyPlacedCard() {
        return this.already_placed_card;
    }

    public int getFromWhereDraw() {
        return this.from_where_draw;
    }

    public int getFromWhichDeckIndex() {
        return this.from_which_deckindex;
    }

    public boolean isSecondLastTurn() {
        return this.second_last_turn;
    }

    public Player[] getWinners(){
        return this.winners;
    }

    public int getCol() {
        return this.col;
    }

    public int getRow() {
        return this.row;
    }
}

