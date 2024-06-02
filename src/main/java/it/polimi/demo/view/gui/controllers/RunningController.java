package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.model.cards.gameCards.StarterCard;
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
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RunningController extends GenericController{
    @FXML public Label myPoints;
    @FXML public Text p2Points;
    @FXML public Text p1Points;
    @FXML public Text p3Points;
    @FXML public Label playerLabel1;
    @FXML public Label playerLabel2;
    @FXML public Label playerLabel3;
    public ArrayList<Text> othersPoints = new ArrayList<>();
    public ArrayList<Label> othersNicknames = new ArrayList<>();

    @FXML public ImageView StarterCardImage;

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
    private ArrayList<Integer> cardHand = new ArrayList<>();
     @FXML
     private ListView<String> eventsListView;
    private ImageView pieceBlackImageView;
    private ArrayList<ImageView> pieces;
    @FXML
    private Label labelMessage;
    private final ImageView[] bnImageViews = new ImageView[30];
    @FXML private ImageView bn0;
    @FXML private ImageView bn1;
    @FXML private ImageView bn2;
    @FXML private ImageView bn3;
    @FXML private ImageView bn4;
    @FXML private ImageView bn5;
    @FXML private ImageView bn6;
    @FXML private ImageView bn7;
    @FXML private ImageView bn8;
    @FXML private ImageView bn9;
    @FXML private ImageView bn10;
    @FXML private ImageView bn11;
    @FXML private ImageView bn12;
    @FXML private ImageView bn13;
    @FXML private ImageView bn14;
    @FXML private ImageView bn15;
    @FXML private ImageView bn16;
    @FXML private ImageView bn17;
    @FXML private ImageView bn18;
    @FXML private ImageView bn19;
    @FXML private ImageView bn20;
    @FXML private ImageView bn21;
    @FXML private ImageView bn22;
    @FXML private ImageView bn23;
    @FXML private ImageView bn24;
    @FXML private ImageView bn25;
    @FXML private ImageView bn26;
    @FXML private ImageView bn27;
    @FXML private ImageView bn28;
    @FXML private ImageView bn29;
    private int starterCard;


    public void initialize() {
        pieces = new ArrayList<>();
        ImageView piece1ImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pieces/piece1.png"))));
        ImageView piece2ImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pieces/piece2.png"))));
        ImageView piece3ImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pieces/piece3.png"))));
        ImageView piece4ImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pieces/piece4.png"))));

        pieces.add(0, piece1ImageView);
        pieces.add(1, piece2ImageView);
        pieces.add(2, piece3ImageView);
        pieces.add(3, piece4ImageView);

        pieceBlackImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pieces/piece0.png"))));

        bnImageViews[0] = bn0;
        bnImageViews[1] = bn1;
        bnImageViews[2] = bn2;
        bnImageViews[3] = bn3;
        bnImageViews[4] = bn4;
        bnImageViews[5] = bn5;
        bnImageViews[6] = bn6;
        bnImageViews[7] = bn7;
        bnImageViews[8] = bn8;
        bnImageViews[9] = bn9;
        bnImageViews[10] = bn10;
        bnImageViews[11] = bn11;
        bnImageViews[12] = bn12;
        bnImageViews[13] = bn13;
        bnImageViews[14] = bn14;
        bnImageViews[15] = bn15;
        bnImageViews[16] = bn16;
        bnImageViews[17] = bn17;
        bnImageViews[18] = bn18;
        bnImageViews[19] = bn19;
        bnImageViews[20] = bn20;
        bnImageViews[21] = bn21;
        bnImageViews[22] = bn22;
        bnImageViews[23] = bn23;
        bnImageViews[24] = bn24;
        bnImageViews[25] = bn25;
        bnImageViews[26] = bn26;
        bnImageViews[27] = bn27;
        bnImageViews[28] = bn28;
        bnImageViews[29] = bn29;

        bnImageViews[0].setImage(pieces.get(0).getImage());
        bn0.setImage(pieces.get(0).getImage());
        bn0.setImage(pieces.get(1).getImage());
        bn0.setImage(pieces.get(2).getImage());
        bn0.setImage(pieces.get(3).getImage());

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

        cardHand.add(0);
        cardHand.add(0);
        cardHand.add(0);

        othersPoints.add(p1Points);
        othersPoints.add(p2Points);
        othersPoints.add(p3Points);

        othersNicknames.add(playerLabel1);
        othersNicknames.add(playerLabel2);
        othersNicknames.add(playerLabel3);

    }

    public void setScoreBoardPosition(GameModelImmutable model){
        for(int i = 0; i < model.getAllPlayers().size(); i++){
            int player_position = model.getCommonBoard().getPlayerPosition(i);
            movePieceToPosition(model, i, player_position);
        }
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
    private void movePieceToPosition(GameModelImmutable model, int player_index, int indexToGo) {

        ImageView targetPane = bnImageViews[indexToGo];
        ImageView piece = pieces.get(player_index);
        Pane parent = (Pane) piece.getParent();
        if (parent != null) {
            parent.getChildren().remove(piece);
        }
        targetPane.setImage(piece.getImage());
    }

    public void setPlayersPointsAndNicknames(GameModelImmutable model, String nickname) {
        ArrayList<PlayerIC> allPlayers = model.getAllPlayers();
        int j = 0;
        for(int i = 0; i < allPlayers.size(); i++){
            if(allPlayers.get(i).getNickname().equals(nickname)){
                myPoints.setText(String.valueOf(model.getAllPlayers().get(i).getScoreBoardPosition()));
            }else{
                othersNicknames.get(j).setText(String.valueOf(allPlayers.get(i).getScoreBoardPosition()));
                othersNicknames.get(j).setText(allPlayers.get(i).getNickname());
                j++;
            }
        }
    }

    // Method to set an image to one of the common card ImageViews
    public void setCommonCards(GameModelImmutable model) {
        Integer[] cardIds = model.getCommonBoard().getCommonCardsId();
        for (int i = 0; i < cardIds.length; i++) {
            int cardId = cardIds[i];
            String imagePath;
            if (i == 0 || i == 2) { // sono in un deck -> voglio il BACK
                imagePath = "/images/cards/cards_back/" + String.format("%03d", cardId) + ".png";
            } else { // sono in una carta -> voglio il FRONT
                imagePath = "/images/cards/cards_front/" + String.format("%03d", cardId) + ".png";
            }
            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
            imageView.setFitWidth(90); // Imposta la larghezza desiderata
            imageView.setFitHeight(65); // Imposta l'altezza desiderata
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
            String imagePath = "/images/cards/cards_front/" + String.format("%03d", cardId) + ".png";
            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
            imageView.setFitWidth(90); // Imposta la larghezza desiderata
            imageView.setFitHeight(65); // Imposta l'altezza desiderata
            if (i == 0) {
                personalObjectivesPane.add(imageView, 0, 0); // Aggiungi l'immagine alla griglia nella posizione corretta
            } else {
                personalObjectivesPane.add(imageView, 1, 0); // Aggiungi l'immagine alla griglia nella posizione corretta
            }
        }
    }

//    public void setStarterCardFront(GameModelImmutable model, String nickname) {
//        this.starterCard = model.getPlayerEntity(nickname).getStarterCard().getId();
//        int cardId = model.getPlayerEntity(nickname).getStarterCard().getId();
//        String imagePath = "/images/cards/cards_front/" + String.format("%03d", cardId) + ".png";
//        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
//        imageView.setFitWidth(90); // Imposta la larghezza desiderata
//        imageView.setFitHeight(65); // Imposta l'altezza desiderata
//        StarterCardImage.setImage(imageView.getImage());
//    }

//    public void setStarterCardBack(GameModelImmutable model, String nickname) {
//        int cardId = model.getPlayerEntity(nickname).getStarterCard().getId();
//        String imagePath = "/images/cards/cards_back/" + String.format("%03d", cardId) + ".png";
//        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
//        imageView.setFitWidth(90); // Imposta la larghezza desiderata
//        imageView.setFitHeight(65); // Imposta l'altezza desiderata
//        StarterCardImage.setImage(imageView.getImage());
//    }

//    public void flipStarterCard() {
//        if (starterCardOrientation == Orientation.BACK)
//            setStarterCardFront(starterCard);
//        else
//            setStarterCardBack(starterCard);
//    }

    public void flipPersonalObjective(int cardId, int i) {
        Image image = new Image("/images/cards/cards_back/"+String.format("%03d", cardId) + ".png"); // Aggiungi l'id della carta al percorso
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(90); // Imposta la larghezza desiderata
        imageView.setFitHeight(65); // Imposta l'altezza desiderata
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
            String formattedCardId = String.format("%03d", cardIds.get(i));
            String imagePath = "/images/cards/cards_front/" + formattedCardId + ".png";
            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
            imageView.setFitWidth(90); // Imposta la larghezza desiderata
            imageView.setFitHeight(65); // Imposta l'altezza desiderata
            cardHandPane.add(imageView, i, 0); // Aggiungi l'immagine alla griglia nella posizione corretta
        }
        cardHandOrientation = Orientation.FRONT;
    }

    public void setCardHandBack(ArrayList<Integer> cardIds) {
        for (int i = 0; i < cardIds.size(); i++) {
            cardHand.set(i, cardIds.get(i));
            String formattedCardId = String.format("%03d", cardIds.get(i));
            String imagePath = "/images/cards/cards_back/" + formattedCardId + ".png";
            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
            imageView.setFitWidth(90); // Imposta la larghezza desiderata
            imageView.setFitHeight(65); // Imposta l'altezza desiderata
            cardHandPane.add(imageView, i, 0); // Aggiungi l'immagine alla griglia nella posizione corretta
        }
        cardHandOrientation = Orientation.BACK;
    }

    public void setStarterCardFront(int cardId) {
        Image starterCardView = new Image("/images/cards/cards_front/"+ String.format("%03d", cardId) + ".png"); // Aggiungi l'id della carta al percorso
        ImageView imageView = new ImageView(starterCardView);
        imageView.setFitWidth(50); // Imposta la larghezza desiderata
        imageView.setFitHeight(70); // Imposta l'altezza desiderata
        starterCardPane.getChildren().add(imageView);
        starterCardOrientation = Orientation.FRONT;
    }

    public void setStarterCardBack(int cardId) {
        Image starterCardView = new Image("/images/cards/cards_back/"+ String.format("%03d", cardId) + ".png"); // Aggiungi l'id della carta al percorso
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
        Image image = new Image("/images/cards/cards_front/"+ String.format("%03d", cardId) + ".png"); // Aggiungi l'id della carta al percorso
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

    /**
     * This method manage the change of turn
     * @param model
     * @param nickname
     */
    public void changeTurn(GameModelImmutable model, String nickname) {
        setMsgToShow("Next turn is up to: " + model.getCurrentPlayerNickname(), true);
    }

    public void setMsgToShow(String msg, Boolean success) {
        labelMessage.setText(msg);
        if (success == null) {
            labelMessage.setTextFill(Color.WHITE);
        } else if (success) {
            labelMessage.setTextFill(Color.GREEN);
        } else {
            labelMessage.setTextFill(Color.RED);
        }
    }
}


