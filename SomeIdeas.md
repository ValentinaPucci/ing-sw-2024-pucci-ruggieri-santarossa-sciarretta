# Preliminary Problems

Firstly, we need to decide whether to start with *GameModel* class or with *GameController* class. In fact, in order to implement GameController, we need GameModel. But what is GameModel? It is a class that summarizes all the functionalities of the model. The only way the model has to 'talk' with other parts of MVC pattern is through the class GameModel.

Some of the major functionalities GameModel has to implement are the following:

- Integration with the package *Listener*: the listener pattern is useful when dealing with the network, but it seems to be unnecessary at the moment.
- Reference to *CommonBoard* class: trivially, where does the client pick a card in order to play? Obviously from a deck, which is in CommonBoard class.
- Reference to a list of *Player*(s): why? because someone has to play the game (hence, we need a reference to the current playing player)! GameModel manages that list in the following way: adds/removes a player, reconnects/disconnects a player, checks if a player is ready to play, modifies the status of the game accordingly to the information contained in the list of players.
- Reference to *GameStatus* (enumeration {WAIT, RUNNING, LASTROUND, SECONDLASTROUND, ENDED}): note that it is the player which influences the status and not vice-versa. Moreover, if the status is changing (i.e. the controller is setting a new status), we notify this change to all the listeners. As an example, consider a situation where the current player place a card into its personal board and reaches 20 points. By doing so, the game enters in the status SECONDLASTROUND (which is set by the controller).
- Relevant methods of the whole model package: as an example, we clearly need the method drawCard (the client pick a card) from a certain deck (recall that all the decks are inside the CommonBoard) and placeCard in a certain PersonalBoard (recall that we have the reference to PersonalBoard through Player class). Furthermore, another relevant method is nextTurn, through which the current player reference is updated to the next player in the order (note that you have always to take care of the game status), notifies to all the listeners the beginning of a new round/turn. In addition to the previously mentioned methods, GameModel also declares the winner of the game (based on the points accumulated during the whole game). Note that it can be useful to have a data structure to mantain a leaderboard of the players in the game.

*Remark*: *GameModel* class does not only manage the methods concerning the game itself, but also all the methods which let a client (physical player) to participate in a game, to leave a game, to connect/disconnect from a game ecc.

What about the *GameController* class? Firstly we need a reference to *GameModel* class. In fact, a number of methods inside GameController simply call the respective methods inside GameModel (i.e. addPlayer/removePlayer). In particular we need the following:

- GameController manage the players exactly in the same way as GameModel does. If all the players are ready to start the game (and they are enough), the GameController initializes the CommonBoard using the initializer already present in *commonBoard* class. Moreover, GameController extract out of random the first player to play (this passage is crucial for our game logic, it is the black placeholder!).
- We also need a number of specific 'getters': the most important are getPlayerPosition, drawFromConcreteDeck, drawFromTable, getGameStatus...
- A crucial method to insert is placeCard. Recall the GameController takes care of the game status, eventually updating it accordingly to the game dynamic. In this case, after the placement of a card, we could end up entering in the LASTROUND. This imply that the game status must be updated directly by GameController. In fact, GameController does not have an attribute for game status. Indeed, this makes sense if we think of a Controller as acting (and thus modifying) its relative model.
- We need to implement the listener design pattern: GameController will contain methods like addListener and removeListener (calling the respective method in GameModel). However, we left this part aside for the moment.

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


- CodeReview: MainControllerInterface 
- CodeReview: Listeners (listenerHandler) --> MARGHE
- Testing --> MARGHE
- Ricontrollare GameController e MainController --> VALE
- Inizio del gioco e creazione sessione --> VALE
- GameView di Tarantino et al + GameFlow --> RIK E LOLLO

# Some notes on the network (4 May 2024)
- Server implementation directly act on the main controller right? Well, where is that main_controller reference initialized? We take care of it by getting the controller instance in the create method of class ServerImpl
- As a convention, a listener is added to the model whenever a player create a new game. 
- We need to rethink interface: Client is an interface which is used by the ServerImpl class. It is an interface in the sense that from an exterior point of view (i.e. from the server), we only see Client (the interface), not its implementation. That is fine! Remark: viceversa still holds, i.e. from the client point of view, we only see the Server interface, not its implementation.

# ToDo (7 may 2024)
- Implement the reconnection feature including new listeners paradigm
- Create a method 'connectPlayer' in model and controller (to be called by the server, induced by the client)
- Someone has to take care of GameModelImmutable class. It is useful to have all the object we want to represent in the view ready to use. 
- Implement performTurn in controller, called by ServerImpl (Server interface) called by ClientImpl (It contains both placeCard and drawCard methods).
- See execute() method in the run of TextualGameUI (called whenever MyState == MY_TURN).

# ToDo (8 may 2024)
- setAsConnected vs setAsReadyToStart... what is the difference?
- Manage the GameStatus! Implement GameFlow method correctly in the controller.

# ToDo (11 may 2024)
- check the real condition of gameDynamics! For example, the game should enter in running status only when the cards are dealt to the players.

# ToDo (17 may 2024)
- we don't provide a list of possible placeable positions in the PersonalBoard, so we need a method that continuously asks the client a different set of coordinate on the PersonalBoard if the previous couple entered was an invalid position

# ToDo (03 june 2024) x GUI based on TUI dynamics
1. objective card to choose (1 / 2)
2. starter card orientation (f / b)
3. place starter card 

# IDEAS x disable buttons / cards
- In case of images -> I need to use the relative Pane

- If I want them to be clickable: starterCardPane.setDisable(false); / FlipStarter.setDisable(false);
- If I want to disable them: FlipStarter.setDisable(true); / starterCardPane.setDisable(true);
- If I want to make them invisible: setVisible(false)

  - If I want to disable all others cards in group:
  
        cardPanes = Arrays.asList(starterCardPane /*, otherPanes */);
        buttons = Arrays.asList(FlipStarter /*, otherButtons */);
  
        // Initially, only the starter card and button are enabled

        setComponentsDisable(cardPanes, true);
        setComponentsDisable(buttons, true);

        starterCardPane.setDisable(false);
        FlipStarter.setDisable(false);

        ...

        private void setComponentsDisable(List<? extends javafx.scene.Node> components, boolean disable) {
            for (javafx.scene.Node component : components) {
                component.setDisable(disable);
                }
            }       

# IDEAS FOR PERSONAL BOARD IN GUI (03 june 2024)
- AnchorPane + ScrollPane (AnchorPane utilizzato per contenere le ImageView, AnchorPane all'interno dello ScrollPane per gestire lo scorrimento.)
- Posizionamento delle carte (ImageView): evento di clic sull'AnchorPane -> Verifica se la posizione del clic è valida -> Se è valida, aggiungi una nuova ImageView alle coordinate specifiche.


da chat gpt 4:
    
    import javafx.application.Application;
    import javafx.scene.Scene;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.layout.Pane;
    import javafx.scene.control.ScrollPane;
    import javafx.stage.Stage;
    import java.util.ArrayList;
    import java.util.List;
    
    public class CardPlacementApp extends Application {
    private List<Coordinate> validCoordinates;
    
        @Override
        public void start(Stage primaryStage) {
            AnchorPane anchorPane = new AnchorPane();
            ScrollPane scrollPane = new ScrollPane(anchorPane);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
    
            // Definire le coordinate valide
            validCoordinates = new ArrayList<>();
            validCoordinates.add(new Coordinate(100, 100));
            validCoordinates.add(new Coordinate(200, 200));
            validCoordinates.add(new Coordinate(300, 300));
            // Aggiungi altre coordinate valide se necessario
    
            // Aggiungi evento di clic all'AnchorPane
            anchorPane.setOnMouseClicked(event -> {
                double x = event.getX();
                double y = event.getY();
    
                Coordinate clickedCoordinate = new Coordinate(x, y);
                if (isValidCoordinate(clickedCoordinate)) {
                    addImageView(anchorPane, clickedCoordinate);
                }
            });
    
            Scene scene = new Scene(scrollPane, 800, 600);
            primaryStage.setTitle("Card Placement App");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    
        private boolean isValidCoordinate(Coordinate coordinate) {
            for (Coordinate validCoordinate : validCoordinates) {
                if (coordinate.isCloseTo(validCoordinate)) {
                    return true;
                }
            }
            return false;
        }
    
        private void addImageView(Pane pane, Coordinate coordinate) {
            Image image = new Image("path/to/your/image.png");
            ImageView imageView = new ImageView(image);
            imageView.setX(coordinate.getX());
            imageView.setY(coordinate.getY());
            pane.getChildren().add(imageView);
        }
    
        public static void main(String[] args) {
            launch(args);
        }
    
        class Coordinate {
            private double x, y;
    
            public Coordinate(double x, double y) {
                this.x = x;
                this.y = y;
            }
    
            public double getX() {
                return x;
            }
    
            public double getY() {
                return y;
            }
    
            public boolean isCloseTo(Coordinate other) {
                double threshold = 10; // distanza massima per essere considerata "vicina"
                return Math.abs(this.x - other.x) < threshold && Math.abs(this.y - other.y) < threshold;
            }
        }
    }


Spiegazione del Codice:

Definizione dell'Interfaccia Grafica:
Creiamo un AnchorPane e un ScrollPane.
Configuriamo il ScrollPane per adattarsi alle dimensioni dell'AnchorPane.

Coordinate Valide:
Definiamo una lista di coordinate valide (validCoordinates).
Implementiamo un metodo isValidCoordinate per verificare se una coordinata cliccata è valida.

Aggiunta di ImageView:
Aggiungiamo un evento di clic all'AnchorPane.
Se la coordinata cliccata è valida, aggiungiamo una nuova ImageView alle coordinate specifiche.

Prossimi Passi:
a. Aggiungere test per verificare se le coordinate sono corrette.
b. Implementare la gestione delle immagini in modo più flessibile.
