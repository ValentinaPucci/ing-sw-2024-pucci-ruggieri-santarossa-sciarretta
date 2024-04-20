# Preliminary Problems

Firstly, we need to decide whether to start with *GameModel* class or with *GameController* class. In fact, in order to implement GameController, we need GameModel. But what is GameModel? It is a class that summarizes all the functionalities of the model. The only way the model has to 'talk' with other parts of MVC pattern is through the class GameModel.

Some of the major functionalities GameModel has to implement are the following:

- Integration with the package *Listener*: the observer pattern is useful when dealing with the network, but it seems to be unnecessary at the moment.
- Reference to *CommonBoard* class: trivially, where does the client pick a card in order to play? Obviously from a deck, which is in CommonBoard class.
- Reference to a list of *Player*(s): why? because someone has to play the game (hence, we need a reference to the current playing player)! GameModel manages that list in the following way: adds/removes a player, reconnects/disconnects a player, checks if a player is ready to play, modifies the status of the game accordingly to the information contained in the list of players.
- Reference to *GameStatus* (enumeration {WAIT, RUNNING, LASTROUND, SECONDLASTROUND, ENDED}): note that it is the player which influences the status and not vice-versa. Moreover, if the status is changing (i.e. the controller is setting a new status), we notify this change to all the listeners. As an example, consider a situation where the current player place a card into its personal board and reaches 20 points. By doing so, the game enters in the status SECONDLASTROUND (which is set by the controller).
- Relevant methods of the whole model package: as an example, we clearly need the method drawCard (the client pick a card) from a certain deck (recall that all the decks are inside the CommonBoard) and placeCard in a certain PersonalBoard (recall that we have the reference to PersonalBoard through Player class). Furthermore, another relevant method is nextTurn, through which the current player reference is updated to the next player in the order (note that you have always to take care of the game status), notifies to all the listeners the beginning of a new round/turn. In addition to the previously mentioned methods, GameModel also declares the winner of the game (based on the points accumulated during the whole game). Note that it can be useful to have a data structure to mantain a leaderboard of the players in the game.

*Remark*: *GameModel* class does not only manage the methods concerning the game itself, but also all the methods which let a client (physical player) to participate in a game, to leave a game, to connect/disconnect from a game ecc.

What about the *GameController* class? Firstly we need a reference to *GameModel* class. In fact, a number of methods inside GameController simply call the respective methods inside GameModel (i.e. addPlayer/removePlayer). In particular we need the following:

- GameController manage the players exactly in the same way as GameModel does. If all the players are ready to start the game (and they are enough), the GameController initializes the CommonBoard using the initializer already present in *commonBoard* class. Moreover, GameController extract out of random the first player to play (this passage is crucial for our game logic, it is the black placeholder!).
- We also need a number of specific 'getters': the most important are getPlayerPosition, drawFromConcreteDeck, drawFromTable, getGameStatus...
- A crucial method to insert is placeCard. Recall the GameController takes care of the game status, eventually updating it accordingly to the game dynamic. In this case, after the placement of a card, we could end up entering in the LASTROUND. This imply that the game status must be updated directly by GameController. In fact, GameController does not have an attribute for game status. Indeed, this makes sense if we think of a Controller as acting (and thus modifying) its relative model.
- We need to implement the observer design pattern: GameController will contain methods like addListener and removeListener (calling the respective method in GameModel). However, we left this part aside for the moment.

Simultaneously, GameController implements GameControllerInterface, an interface belonging to the networking package, specifically inside *rmi* package. In this context, GameController can be seen as the implementation of GameControllerInterface needed by rmi. From this point of view, many of the previously mentioned methods are in fact ovveriding what is specified in GameControllerInterface.
Before going on, look at lines 298 (to 311). The RMI client is in fact our physical player, and thus he is able to draw a card from a deck as well as place a card on his personal board. Hence, we need those methods inside the interface rmi uses to communicate with the model, i.e. GameControllerInterface.
For other information, look directly at GameControllerInterface class. Remember the only methods an rmi client can use are inside this interface. For a more general view of what a client can do and cannot do you can inspect the interface called *CommonClientActions*.
So, in order to summarize what we have seen, we can say that a client (whose possible actions are described in CommonClientActions) uses an interface called GameControllerInterface, which is implemented by the GameController.

# The concept of reconnection

We would like to implement this advanced functionality on our program. Namely, we want that if for some reason a client loses the connection to the server, then it is able to reacquire it thanks to our implementation. An issue is the following: the *run* method inside *GameController* class performs some actions with respect to a map. This map has the following meaning: everyone who is currently playing is identified as a listener (the domain of the map), and we define a function from this domain to a set of heartbeats (the codomain). In other words, every player (listener) is associated to a specific heartbeat, which contains the information required from the server to determine whether a client is still connected (a 'fresh' heartbeat) or not. Now, the problem is that it is not so simple to find, inside the code of *Saccani et al.* where the update caller routine (which must be called 'frequently' the update of these heartbeats) is located. At a first glance, it seems that the code we are searching for is inside the *RMI Client* class, but it is commented by the authors. At this stage, this appears to me as an open problem!

# ToDo

- Change Listener
- Change Chat
- Multiple Games
- Disconnections
- Change all the methods related to connection resilience except from *reconnectPlayer*
- Define the winner based on the number of objective realized
