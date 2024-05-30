package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.model.interfaces.PlayerIC;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RunningController extends GenericController{


    @FXML private AnchorPane mainAnchor;
    @FXML public ImageView personalObjective0;
    @FXML public ImageView personalObjective1;
    @FXML public GridPane commonCardsPane;
    @FXML public GridPane personalObjectivesPane;
    @FXML public Pane starterCardPane;
    @FXML public GridPane cardHandPane;
    @FXML private Pane scoreBoardPane;
    private Orientation cardHandOrientation;
    private Orientation starterCardOrientation;
    private ArrayList<Integer> cardHand;
    @FXML private ListView<String> eventsListView;  //TODO: DA CAPIRE X RIUSCIRE A COLLEGARLA AL MODEL -> I METODI NON LI HO ANCORA CREATI QUA
    private Pane[] bnPanes;
    @FXML private Pane bn0;
    @FXML private Pane bn1;
    @FXML private Pane bn2;
    @FXML private Pane bn3;
    @FXML private Pane bn4;
    @FXML private Pane bn5;
    @FXML private Pane bn6;
    @FXML private Pane bn7;
    @FXML private Pane bn8;
    @FXML private Pane bn9;
    @FXML private Pane bn10;
    @FXML private Pane bn11;
    @FXML private Pane bn12;
    @FXML private Pane bn13;
    @FXML private Pane bn14;
    @FXML private Pane bn15;
    @FXML private Pane bn16;
    @FXML private Pane bn17;
    @FXML private Pane bn18;
    @FXML private Pane bn19;
    @FXML private Pane bn20;
    @FXML private Pane bn21;
    @FXML private Pane bn22;
    @FXML private Pane bn23;
    @FXML private Pane bn24;
    @FXML private Pane bn25;
    @FXML private Pane bn26;
    @FXML private Pane bn27;
    @FXML private Pane bn28;
    @FXML private Pane bn29;
    private ImageView pieceBlackImageView;
    private ArrayList<ImageView> pieces;


    public void initialize() {
        pieces = new ArrayList<>();
        ImageView piece1ImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pieces/piece1.png"))));
        ImageView piece2ImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pieces/piece2.png"))));
        ImageView piece3ImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pieces/piece3.png"))));
        ImageView piece4ImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pieces/piece4.png"))));

        pieces.add(0, piece1ImageView);
        pieces.add(1, piece2ImageView);
        pieces.add(2, piece3ImageView);
        pieces.add(3, piece4ImageView);

        pieceBlackImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pieces/piece0.png"))));

        bnPanes[0] = bn0;
        bnPanes[1] = bn1;
        bnPanes[2] = bn2;
        bnPanes[3] = bn3;
        bnPanes[4] = bn4;
        bnPanes[5] = bn5;
        bnPanes[6] = bn6;
        bnPanes[7] = bn7;
        bnPanes[8] = bn8;
        bnPanes[9] = bn9;
        bnPanes[10] = bn10;
        bnPanes[11] = bn11;
        bnPanes[12] = bn12;
        bnPanes[13] = bn13;
        bnPanes[14] = bn14;
        bnPanes[15] = bn15;
        bnPanes[16] = bn16;
        bnPanes[17] = bn17;
        bnPanes[18] = bn18;
        bnPanes[19] = bn19;
        bnPanes[20] = bn20;
        bnPanes[21] = bn21;
        bnPanes[22] = bn22;
        bnPanes[23] = bn23;
        bnPanes[24] = bn24;
        bnPanes[25] = bn25;
        bnPanes[26] = bn26;
        bnPanes[27] = bn27;
        bnPanes[28] = bn28;
        bnPanes[29] = bn29;

        scoreBoardPane.getChildren().add(piece1ImageView);
        movePieceToPositionZero(piece1ImageView, bnPanes[0]);
        scoreBoardPane.getChildren().add(piece2ImageView);
        movePieceToPositionZero(piece2ImageView, bnPanes[0]);
        scoreBoardPane.getChildren().add(piece3ImageView);
        movePieceToPositionZero(piece3ImageView, bnPanes[0]);
        scoreBoardPane.getChildren().add(piece4ImageView);
        movePieceToPositionZero(piece4ImageView, bnPanes[0]);

        for (int i = 0; i < 6; i++) {
            ImageView imageView = new ImageView();
            imageView.setFitWidth(50);
            imageView.setFitHeight(70);
            imageView.setImage(null);
            int column = i % 2;
            int row = i / 2;
            commonCardsPane.add(imageView, column, row);
        }

        for (int i = 0; i < 2; i++) {
            ImageView imageView = new ImageView();
            imageView.setFitWidth(50);
            imageView.setFitHeight(70);
            imageView.setImage(null);
            personalObjectivesPane.add(imageView, i, 0);
        }

        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView();
            imageView.setFitWidth(50);
            imageView.setFitHeight(70);
            imageView.setImage(null);
            cardHandPane.add(imageView, i, 0);
        }

        ImageView starterCardView = new ImageView();
        starterCardView.setFitWidth(50);
        starterCardView.setFitHeight(70);
        starterCardView.setImage(null);
        starterCardPane.getChildren().add(starterCardView);

        cardHand.set(0, 0);
        cardHand.set(1, 0);
        cardHand.set(2, 0);

    }

    // Method to move a piece to a new Pane
    private void movePieceToPositionZero(ImageView piece, Pane targetPane) {
        Pane parent = (Pane) piece.getParent();
        if (parent != null) {
            parent.getChildren().remove(piece);
        }
        targetPane.getChildren().add(piece);
    }

    // Method to move a piece to a new Pane
    private void movePieceToPosition(GameModelImmutable model, String nickname, int indexToGo) {
        int player_index = -1 ;
        ArrayList<PlayerIC> players_list = model.getAllPlayers();
        for (int i = 0; i < players_list.size(); i++) {
            if (players_list.get(i).getNickname().equals(nickname)) {
                player_index = i;
            }
        }
        if(player_index == -1)
            System.out.println("Errore riga 181 gridPaneController");

        Pane targetPane = bnPanes[indexToGo];
        ImageView piece = pieces.get(player_index);
        Pane parent = (Pane) piece.getParent();
        if (parent != null) {
            parent.getChildren().remove(piece);
        }
        targetPane.getChildren().add(piece);

    }

    // Method to set an image to one of the common card ImageViews
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
            commonCardsPane.add(imageView, column, row);
        }
    }


    // Method to set an image to one of the common card ImageViews
    public void setPersonalObjectives(GameModelImmutable model, String nickname) {
        Integer[] cardIds = model.getPlayerEntity(nickname).getSecretObjectiveCardsIds();
        for (int i = 0; i < cardIds.length; i++) {
            int cardId = cardIds[i];
            Image image = new Image("cards_front/"+ cardId); // Aggiungi l'id della carta al percorso
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50); // Imposta la larghezza desiderata
            imageView.setFitHeight(70); // Imposta l'altezza desiderata
            if (i == 0)
                personalObjectivesPane.add(imageView, 0, 0); // Aggiungi l'immagine alla griglia nella posizione corretta
            else
                personalObjectivesPane.add(imageView, 0, 1); // Aggiungi l'immagine alla griglia nella posizione corretta
        }
    }

    public void flipPersonalObjective(int cardId, int i) {
        Image image = new Image("cards_back/"+ cardId); // Aggiungi l'id della carta al percorso
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50); // Imposta la larghezza desiderata
        imageView.setFitHeight(70); // Imposta l'altezza desiderata
        if (i == 0)
            personalObjectivesPane.add(imageView, 0, 0); // Aggiungi l'immagine alla griglia nella posizione corretta
        else
            personalObjectivesPane.add(imageView, 0, 1); // Aggiungi l'immagine alla griglia nella posizione corretta
    }

    public void setCardHand(GameModelImmutable model, String nickname) {
        setCardHandFront(model.getPlayerEntity(nickname).getCardHandIds());
    }

    public void setCardHandFront(ArrayList<Integer> cardIds) {
        for (int i = 0; i < cardIds.size(); i++) {
            cardHand.set(i, cardIds.get(i));
            Image image = new Image("cards_front/"+ cardIds.get(i)); // Aggiungi l'id della carta al percorso
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50); // Imposta la larghezza desiderata
            imageView.setFitHeight(70); // Imposta l'altezza desiderata
            cardHandPane.add(imageView, 0, i); // Aggiungi l'immagine alla griglia nella posizione corretta
        }
        cardHandOrientation = Orientation.FRONT;
    }

    public void setCardHandBack(ArrayList<Integer> cardIds) {
        for (int i = 0; i < cardIds.size(); i++) {
            cardHand.set(i, cardIds.get(i));
            Image image = new Image("cards_back/"+ cardIds.get(i)); // Aggiungi l'id della carta al percorso
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50); // Imposta la larghezza desiderata
            imageView.setFitHeight(70); // Imposta l'altezza desiderata
            cardHandPane.add(imageView, 0, i); // Aggiungi l'immagine alla griglia nella posizione corretta
        }
        cardHandOrientation = Orientation.BACK;
    }

    public void setStarterCardFront(int cardId) {
        Image starterCardView = new Image("cards_front/"+ cardId); // Aggiungi l'id della carta al percorso
        ImageView imageView = new ImageView(starterCardView);
        imageView.setFitWidth(50); // Imposta la larghezza desiderata
        imageView.setFitHeight(70); // Imposta l'altezza desiderata
        starterCardPane.getChildren().add(imageView);
        starterCardOrientation = Orientation.FRONT;
    }

    public void setStarterCardBack(int cardId) {
        Image starterCardView = new Image("cards_back/"+ cardId); // Aggiungi l'id della carta al percorso
        ImageView imageView = new ImageView(starterCardView);
        imageView.setFitWidth(50); // Imposta la larghezza desiderata
        imageView.setFitHeight(70); // Imposta l'altezza desiderata
        starterCardPane.getChildren().add(imageView);
        starterCardOrientation = Orientation.BACK;
    }

    public void flipCardHand() {
        if (cardHandOrientation == Orientation.BACK)
            setCardHandFront(cardHand);
        else
            setCardHandBack(cardHand);
    }


    //TODO: to adjust the entering parameter
    public void flipStarterCard(int id) {
        if (starterCardOrientation == Orientation.BACK)
            setStarterCardFront(id);
        else
            setStarterCardBack(id);
    }

    //TODO: quando faccio place devo togliere la carta dalla mano di carte
    //quando inserisco una carta in mano la inserisco in posizione 0
    public void modifyCardHandFront(int cardId) {
        cardHand.set(0, cardId);
        Image image = new Image("cards_front/"+ cardId); // Aggiungi l'id della carta al percorso
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50); // Imposta la larghezza desiderata
        imageView.setFitHeight(70); // Imposta l'altezza desiderata
        cardHandPane.add(imageView, 0, 0); // Aggiungi l'immagine alla griglia nella posizione corretta
        cardHandOrientation = Orientation.FRONT;
    }

    public void setPlayerDrawnCard(GameModelImmutable model, String nickname) {
        setCommonCards(model);
        setCardHand(model, nickname);
    }

    //mostra che la carta giocata non è più in mano
    public void setPlayerPlacedCard(GameModelImmutable model, String nickname) {
        setCardHand(model, nickname);
    }


    public void setImportantEvents(List<String> importantEvents) {
        eventsListView.getItems().clear();
        for (String s : importantEvents) {
            eventsListView.getItems().add(s);
        }
        eventsListView.scrollTo(eventsListView.getItems().size());
    }
}


