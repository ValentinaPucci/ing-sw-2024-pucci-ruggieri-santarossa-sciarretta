package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.model.interfaces.PlayerIC;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RunningControllerOLD extends GenericController{
    public Label youPoints;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    public GridPane CommonGridPane;
    @FXML
    public GridPane PersonalObjectiveGridPane;
    @FXML
    public GridPane CardHandPane;
    @FXML
    private Pane gameBoardPane;
    private List<ImageView> pieces;
    private List<Double[]> positions;
    @FXML
    private Label youNickname;
    @FXML
    private Label playerLabel1;
    @FXML
    private Label player1Points;
    @FXML
    private Label playerLabel2;
    @FXML
    private Label player2Points;
    @FXML
    private Label playerLabel3;
    @FXML
    private Label player3Points;
    @FXML
    private int points;
    private Orientation cardHandOrientation;

    @FXML
    private ListView<String> eventsListView;  //TODO: DA CAPIRE X RIUSCIRE A COLLEGARLA AL MODEL -> I METODI NON LI HO ANCORA CREATI QUA

    private ArrayList<Integer> cardHand;

    @FXML
    private void initialize() {
        // Carica l'immagine di sfondo del tabellone
        ImageView background = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Background.jpg"))));
        gameBoardPane.getChildren().add(background);

        // Esempio di posizioni (layoutX, layoutY)
        positions = new ArrayList<>();
        positions.add(new Double[]{412.0, 2940.0});  // Casella 0
        positions.add(new Double[]{788.0, 2940.0}); // Casella 1
        positions.add(new Double[]{1160.0, 2940.0}); // Casella 2
        positions.add(new Double[]{1340.0, 2605.0}); // Casella 3
        positions.add(new Double[]{972.0, 2605.0}); // Casella 4
        positions.add(new Double[]{600.0, 2605.0});  // Casella 5
        positions.add(new Double[]{228.0, 2605.0}); // Casella 6
        positions.add(new Double[]{228.0, 2270.0}); // Casella 7
        positions.add(new Double[]{600.0, 2270.0}); // Casella 8
        positions.add(new Double[]{972.0, 2270.0}); // Casella 9
        positions.add(new Double[]{1344.0, 2270.0});  // Casella 10
        positions.add(new Double[]{1344.0, 1935.0}); // Casella 11
        positions.add(new Double[]{972.0, 1935.0}); // Casella 12
        positions.add(new Double[]{600.0, 1935.0}); // Casella 13
        positions.add(new Double[]{228.0, 1935.0}); // Casella 14
        positions.add(new Double[]{228.0, 1560.0});  // Casella 15
        positions.add(new Double[]{600.0, 1560.0}); // Casella 16
        positions.add(new Double[]{972.0, 1560.0}); // Casella 17
        positions.add(new Double[]{1344.0, 1560.0}); // Casella 18
        positions.add(new Double[]{1344.0, 1224.0}); // Casella 19
        positions.add(new Double[]{788.0, 1072.0});  // Casella 20
        positions.add(new Double[]{228.0, 2940.0}); // Casella 21
        positions.add(new Double[]{228.0, 910.0}); // Casella 22
        positions.add(new Double[]{228.0, 576.0}); // Casella 23
        positions.add(new Double[]{444.0, 276.0}); // Casella 24
        positions.add(new Double[]{788.0, 220.0});  // Casella 25
        positions.add(new Double[]{1132.0, 276.0}); // Casella 26
        positions.add(new Double[]{1344.0, 576.0}); // Casella 27
        positions.add(new Double[]{1344.0, 910.0}); // Casella 28
        positions.add(new Double[]{788.0, 2270.0}); // Casella 29

        // Carica le pedine
        pieces = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            ImageView piece = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pieces/piece" + i + ".png"))));
            piece.setFitWidth(30);
            piece.setFitHeight(30);
            pieces.add(piece);
            gameBoardPane.getChildren().add(piece);
            movePieceToPosition(piece, 0);
        }

        points = 0;
        updatePoints();

        cardHandOrientation = Orientation.FRONT;

        cardHand = new ArrayList<>();
    }

    private void movePieceToPosition(ImageView piece, int positionIndex) {
        Double[] position = positions.get(positionIndex);
        piece.setLayoutX(position[0]);
        piece.setLayoutY(position[1]);
    }

    //METODO CHE VERRà CHIAMATO DAL GAME FLOW
    // PIECE INDEX corrisponde a PLAYER INDEX
    // STEPS sono i punti di cui mi devo spostare, cioè quelli che ho fatto nel turno (delta points)
    // PLAYER 1 -> BLU
    // PLAYER 2 -> VERDE
    // PLAYER 3 -> ROSSO
    // PLAYER 4 -> GIALLO
    public void movePiece(int pieceIndex, int steps) {
        ImageView piece = pieces.get(pieceIndex);
        int currentPosition = positions.indexOf(new Double[]{piece.getLayoutX(), piece.getLayoutY()});
        int newPosition = (currentPosition + steps) % positions.size();
        movePieceToPosition(piece, newPosition);
    }

    public void updatePoints() {
        youPoints.setText("Your points: " + points);
    }

    //METODO CHE VERRà CHIAMATO DA SOTTO
    // VALUE sono i punti che il giocatore ha fatto nella mano, che vanno aggiunti a quelli precedenti (delta points)
    public void addPoints(int value) {
        points += value;
        updatePoints();
    }

    //METODO CHE VERRà CHIAMATO DAL GAME FLOW
    // cardIds sono gli id (su 3 cifre) delle carte da mostrare nella common board
    // cardIds[0] -> ResourceDeck.peek() (voglio il BACK)
    // cardIds[1] -> ResourceCard (voglio il FRONT)
    // cardIds[2] -> GoldDeck.peek() (voglio il BACK)
    // cardIds[3] -> GoldCard (voglio il FRONT)
    // cardIds[4] -> ObjectiveCard (voglio il FRONT)
    // cardIds[5] -> ObjectiveCard (voglio il FRONT)

    public void setCommonCards(GameModelImmutable model) {
        Integer[] cardIds = model.getCommonBoard().getCommonCardsId();
        for (int i = 0; i < cardIds.length; i++) {
            int cardId = cardIds[i];
            ImageView imageView;
            Image image;
            imageView = new ImageView();
            imageView.setFitWidth(50);
            imageView.setFitHeight(70);

            if (i == 0 || i == 2 || i == 4 || i == 5) { //(sono in un deck -> voglio il BACK)
                image = new Image("cards_back/" + cardId + ".png"); // Assicurati di includere l'estensione del file (es. ".png")
            } else { //(sono in una carta -> voglio il FRONT)
                image = new Image("cards_front/" + cardId + ".png"); // Assicurati di includere l'estensione del file (es. ".png")
            }

            imageView.setImage(image);
            int column = i % 2;
            int row = i / 2;
            CommonGridPane.add(imageView, column, row);
        }
    }



    //METODO CHE VERRà CHIAMATO DAL GAME FLOW
    // cardIds sono gli id (su 3 cifre) delle carte obiettivo personale
    // metodo chiamato prima di scegliere la carta obiettivo definitiva

    public void setPersonalObjective(GameModelImmutable model, String nickname) {
        Integer[] cardIds = model.getPlayerEntity(nickname).getSecretObjectiveCardsIds();
        for (int i = 0; i < cardIds.length; i++) {
            int cardId = cardIds[i];
                Image image = new Image("cards_front/"+ cardId); // Aggiungi l'id della carta al percorso
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(50); // Imposta la larghezza desiderata
                imageView.setFitHeight(70); // Imposta l'altezza desiderata
                if (i == 0)
                    PersonalObjectiveGridPane.add(imageView, 0, 0); // Aggiungi l'immagine alla griglia nella posizione corretta
                else
                    PersonalObjectiveGridPane.add(imageView, 0, 1); // Aggiungi l'immagine alla griglia nella posizione corretta
        }
    }


    //METODO CHE VERRà CHIAMATO DAL GAME FLOW
    // cardIds sono gli id (su 3 cifre) delle carte obiettivo personale
    // metodo chiamato dopo aver scelto la carta obiettivo definitiva
    //questo metodo serve per girare l'altra carta
    // in ingresso c'è l'id della carta da girare e la posizione (i = 0 , i = 1)
    public void flipPersonalObjective(int cardId, int i) {
        Image image = new Image("cards_back/"+ cardId); // Aggiungi l'id della carta al percorso
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50); // Imposta la larghezza desiderata
        imageView.setFitHeight(70); // Imposta l'altezza desiderata
        if (i == 0)
            PersonalObjectiveGridPane.add(imageView, 0, 0); // Aggiungi l'immagine alla griglia nella posizione corretta
        else
            PersonalObjectiveGridPane.add(imageView, 0, 1); // Aggiungi l'immagine alla griglia nella posizione corretta
    }


    //METODO CHE VERRà CHIAMATO DAL GAME FLOW AL PRIMO TURNO e da qua con il flip
    public void setCardHandFront(ArrayList<Integer> cardIds) {
        for (int i = 0; i < cardIds.size(); i++) {
            cardHand.set(i, cardIds.get(i));
            Image image = new Image("cards_front/"+ cardIds.get(i)); // Aggiungi l'id della carta al percorso
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50); // Imposta la larghezza desiderata
            imageView.setFitHeight(70); // Imposta l'altezza desiderata
            CardHandPane.add(imageView, 0, i); // Aggiungi l'immagine alla griglia nella posizione corretta
           }
        cardHandOrientation = Orientation.FRONT;
    }


    //METODO CHE VERRà CHIAMATO DA SOTTO NEI TURNI SUCCESSIVI
    //CARD ID è L'ID DELLA CARTA DA AGGIUNGERE ALLA MANO DI CARTE
    // i è L'INDICE IN CUI INSERIRLA NELLA MANO
    //TODO: CONTROLLARE SE è LA STESSA LOGICA CHE è IMPLEMENTATA NEL MODEL
    public void modifyCardHandFront(int cardId, int i) {
        cardHand.set(i, cardId);
        Image image = new Image("cards_front/"+ cardId); // Aggiungi l'id della carta al percorso
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50); // Imposta la larghezza desiderata
        imageView.setFitHeight(70); // Imposta l'altezza desiderata
        CardHandPane.add(imageView, 0, i); // Aggiungi l'immagine alla griglia nella posizione corretta
        cardHandOrientation = Orientation.FRONT;
    }

    //METODO CHE !! NON !! VERRà CHIAMATO DAL GAME FLOW
    public void setCardHandBack(ArrayList<Integer> cardIds) {
        for (int i = 0; i < cardIds.size(); i++) {
            cardHand.set(i, cardIds.get(i));
            Image image = new Image("cards_back/"+ cardIds.get(i)); // Aggiungi l'id della carta al percorso
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50); // Imposta la larghezza desiderata
            imageView.setFitHeight(70); // Imposta l'altezza desiderata
            CardHandPane.add(imageView, 0, i); // Aggiungi l'immagine alla griglia nella posizione corretta
        }
        cardHandOrientation = Orientation.BACK;
    }

    //METODO CHE !! NON !! VERRà CHIAMATO DAL GAME FLOW MA VIENE ATTIVATO DAL BOTTONE flip
    public void flipCardHand() {
        if (cardHandOrientation == Orientation.BACK)
            setCardHandFront(cardHand);
        else
            setCardHandBack(cardHand);
    }


    //--------------------GESTION EVENTI NELLA LIST VIEW-------
    /**
     * This method set the important events
     * @param importantEvents the list of important events
     */

    //METODO CHE VERRà CHIAMATO DALLA GUI APPLICATION
    public void setImportantEvents(List<String> importantEvents) {
        eventsListView.getItems().clear();
        for (String s : importantEvents) {
            eventsListView.getItems().add(s);
        }
        eventsListView.scrollTo(eventsListView.getItems().size());
    }

    //TODO: capire se playersConnected è la giusta struttura dati da usare (riccardo)
    // voglio avere sempre lo stesso ordine di giocatori
    public void setScoreBoard(GameModelImmutable model) {
        for(int i = 0; i < model.getPlayersConnected().size(); i++){
            ImageView piece = pieces.get(i);
            movePieceToPosition(piece, (model.getPlayersConnected().get(i).getScoreBoardPosition()));
        }
    }

    public void setNicknamesAndPoints(GameModelImmutable model, String nickname) {
        youNickname.setTextFill(Color.WHITE);
        playerLabel1.setTextFill(Color.WHITE);
        playerLabel2.setTextFill(Color.WHITE);
        playerLabel3.setTextFill(Color.WHITE);
        Integer refToGui;
        Label labelNick = null, labelPoints = null;

        for (PlayerIC p : model.getPlayersConnected()) {
            refToGui = getReferringPlayerIndex(model, nickname, p.getNickname());
            switch (refToGui) {
                case 0 -> {
                    labelNick = youNickname;
                    labelPoints = (Label) mainAnchor.lookup("#youPoints");
                }
                case 1 -> {
                    labelNick = playerLabel1;
                    labelPoints = (Label) mainAnchor.lookup("#player1Points");
                }
                case 2 -> {
                    labelNick = playerLabel2;
                    labelPoints = (Label) mainAnchor.lookup("#player2Points");
                }
                case 3 -> {
                    labelNick = playerLabel3;
                    labelPoints = (Label) mainAnchor.lookup("#player3Points");
                }
                case null, default -> throw new IllegalStateException("Unexpected value: " + refToGui);
            }

            assert labelNick != null;
            labelNick.setText(p.getNickname());
            labelNick.setVisible(true);

            labelPoints.setText(String.valueOf(p.getScoreBoardPosition()));
            labelPoints.setVisible(true);

            if (model.getCurrentPlayerNickname().equals(p.getNickname())) {
                labelNick.setTextFill(Color.YELLOW);
            }
        }
    }



    /**
     * This method return the index of the player
     *
     * @param model            the model of the game {@link GameModelImmutable}
     * @param personalNickname the nickname of the player
     * @param nickToGetRef     the nickname of the player to get the index
     * @return the index of the player in the GUI
     */
    private Integer getReferringPlayerIndex(GameModelImmutable model, String personalNickname, String nickToGetRef) {
        if (personalNickname.equals(nickToGetRef))
            return 0;

        int offset = 1;
        int playerNum = model.getPlayersConnected().size();
        String otherNick;

        for (int i = 0; i < playerNum; i++) {
            otherNick = model.getPlayersConnected().get(i).getNickname();
            if (!otherNick.equals(personalNickname)) {
                if (otherNick.equals(nickToGetRef)) {
                    return offset;
                }
                offset++;
            }

        }
        return null;
    }

    public void setCardHand(GameModelImmutable model, String nickname) {
        setCardHandFront(model.getPlayerEntity(nickname).getCardHandIds());
    }

    public void setPlayerDrawnCard(GameModelImmutable model, String nickname) {
        setCommonCards(model);
        setCardHand(model, nickname);
    }

    /**
     * This method set the message in the correct format
     * @param msgs the list of messages
     * @param myNickname the nickname of the player
     */
    public void setMessage(List<Message> msgs, String myNickname) {
    }
}
