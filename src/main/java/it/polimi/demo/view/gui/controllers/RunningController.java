package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.interfaces.PlayerIC;
import it.polimi.demo.view.flow.utilities.GuiReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RunningController extends GenericController {
    @FXML public Label myPoints;
    @FXML public Text p2Points;
    @FXML public Text p1Points;
    @FXML public Text p3Points;
    @FXML public Label playerLabel1;
    @FXML public Label playerLabel2;
    @FXML public Label playerLabel3;
    public ArrayList<Text> othersPoints = new ArrayList<>();
    public ArrayList<Label> othersNicknames = new ArrayList<>();
    @FXML public GridPane otherPlayers;
    @FXML public ImageView StarterCardImage;
    @FXML public Pane starterCardPane;
    public Button FlipStarter;
    public Pane personalObjective0Pane;
    public Pane personalObjective1Pane;
    @FXML public HBox personalObjectivesBox;
    @FXML private AnchorPane mainAnchor;
    @FXML public ImageView personalObjective0;
    @FXML public ImageView personalObjective1;
    @FXML public GridPane commonCardsPane;
    @FXML public GridPane cardHandPane;
    @FXML private Pane scoreBoardPane;
    private Orientation cardHandOrientation;
    private Orientation starterCardOrientation;
    private ArrayList<Integer> cardHand = new ArrayList<>();
    @FXML private ListView<String> eventsListView;
    private ImageView pieceBlackImageView;
    private ArrayList<ImageView> pieces;
    @FXML private Label labelMessage;
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
    @FXML private TextArea chatArea;
    @FXML private TextField chatInput;
    @FXML
    private ComboBox<String> recipientComboBox;
    private int starterCard = 0;
    private ArrayList<PlayerIC> players_list;

    private List<Pane> cardPanes;
    private List<Button> buttons;

    ArrayList<Integer> personalObjectiveIds = new ArrayList<>();

    public void initialize() {

        personalObjectivesBox = new HBox();

        pieces = new ArrayList<>();
        ImageView piece1ImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pieces/piece1.png")))); //blue
        ImageView piece2ImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pieces/piece2.png")))); //green
        ImageView piece3ImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pieces/piece3.png")))); //red
        ImageView piece4ImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pieces/piece4.png")))); //yellow

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

        for (int i = 0; i < 6; i++) {
            ImageView imageView = new ImageView();
            imageView.setFitWidth(90);
            imageView.setFitHeight(65);
            imageView.setImage(null);
            int column = i % 2;
            int row = i / 2;
            commonCardsPane.add(imageView, column, row);
        }

        ImageView imageView1 = new ImageView();
        imageView1.setFitWidth(90);
        imageView1.setFitHeight(65);
        imageView1.setImage(null);
        personalObjective1Pane.getChildren().add(imageView1);

        ImageView imageView0 = new ImageView();
        imageView0.setFitWidth(90);
        imageView0.setFitHeight(65);
        imageView0.setImage(null);
        personalObjective1Pane.getChildren().add(imageView0);


        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView();
            imageView.setFitWidth(90);
            imageView.setFitHeight(65);
            imageView.setImage(null);
            cardHandPane.add(imageView, i, 0);
        }

        ImageView starterCardView = new ImageView();
        starterCardView.setFitWidth(90);
        starterCardView.setFitHeight(65);
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

        //disable all cards
        cardPanes = Arrays.asList(starterCardPane, cardHandPane, commonCardsPane, personalObjective0Pane, personalObjective1Pane);
        setComponentsDisable(cardPanes, true);

    }

    private void setComponentsDisable(List<? extends javafx.scene.Node> components, boolean disable) {
        for (javafx.scene.Node component : components) {
            component.setDisable(disable);
        }
    }

    public void setScoreBoardPosition(ModelView model) {
        for (int i = 0; i < model.getAllPlayers().size(); i++) {
            int player_position = model.getCommonBoard().getPlayerPosition(i);
            movePieceToPosition(i, player_position);
        }
    }

    // Method to move a piece to a new Pane
    private void movePieceToPosition(int player_index, int indexToGo) {
        ImageView targetPane = bnImageViews[indexToGo];
        ImageView piece = pieces.get(player_index);
        Pane parent = (Pane) piece.getParent();
        if (parent != null) {
            parent.getChildren().remove(piece);
        }
        targetPane.setImage(piece.getImage());
    }


    public void setPlayersPointsAndNicknames(ModelView model, String nickname) {
        ArrayList<PlayerIC> players_list = model.getAllPlayers();
        this.players_list = players_list;

        // Update recipient combo box
        recipientComboBox.getItems().clear();
        recipientComboBox.getItems().add("Everyone");

        // Clear existing rows except the header
        otherPlayers.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);
        // Remove existing RowConstraints except the first one
        while (otherPlayers.getRowConstraints().size() > 1) {
            otherPlayers.getRowConstraints().remove(1);
        }

        int j = 0;
        for (PlayerIC player : players_list) {
            if (!player.getNickname().equals(nickname)) {
                // Add player to recipient combo box
                recipientComboBox.getItems().add(player.getNickname());
                // Create and add player label
                Label playerLabel = new Label(player.getNickname());
                playerLabel.setTextFill(Color.web("#4d0202"));
                playerLabel.setAlignment(javafx.geometry.Pos.CENTER);
                playerLabel.setFont(javafx.scene.text.Font.font("Luminari", 19));
                GridPane.setRowIndex(playerLabel, j + 1);
                GridPane.setColumnIndex(playerLabel, 0);
                otherPlayers.getChildren().add(playerLabel);

                // Create and add points label
                Text pointsText = new Text(String.valueOf(player.getScoreBoardPosition()));
                pointsText.setFill(Color.web("#4d0202"));
                pointsText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
                pointsText.setFont(javafx.scene.text.Font.font("Luminari", 19));
                GridPane.setRowIndex(pointsText, j + 1);
                GridPane.setColumnIndex(pointsText, 1);
                otherPlayers.getChildren().add(pointsText);

                // Create and add button
                Button goButton = new Button("GO");
                goButton.setPrefHeight(26.0);
                goButton.setPrefWidth(188.0);
                goButton.setEffect(new javafx.scene.effect.ColorAdjust(0.17, 0.3, 0.0, 0.0));
                goButton.setOnAction((ActionEvent event) -> {
                    showOthersPersonalBoard(player);
                });
                GridPane.setRowIndex(goButton, j + 1);
                GridPane.setColumnIndex(goButton, 2);
                otherPlayers.getChildren().add(goButton);

                // Add row constraints
                otherPlayers.getRowConstraints().add(new RowConstraints(30.0, 30.0, Double.MAX_VALUE));
                j++;
            }
        }
    }

    private void showOthersPersonalBoard(PlayerIC player) {
        // TODO: Logica per mostrare la board personale degli altri giocatori
        System.out.println("Mostra board per " + player.getNickname());
    }

    // Method to set an image to one of the common card ImageViews
    public void setCommonCards(ModelView model) {
        Integer[] cardIds = model.getCommonBoard().getCommonCardsId();
        for (int i = 0; i < cardIds.length; i++) {
            int cardId = cardIds[i];
            String imagePath;
            if (i == 0 || i == 3 || i == 6) { // sono in un deck -> voglio il BACK
                imagePath = "/images/cards/cards_back/" + String.format("%03d", cardId) + ".png";
            } else { // sono in una carta -> voglio il FRONT
                imagePath = "/images/cards/cards_front/" + String.format("%03d", cardId) + ".png";
            }
            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
            imageView.setFitWidth(90); // Imposta la larghezza desiderata
            imageView.setFitHeight(65); // Imposta l'altezza desiderata
            int column = i % 3;
            int row = i / 3;
            commonCardsPane.add(imageView, column, row);
        }
    }


    // Method to set an image to one of the common card ImageViews
    public void setPersonalObjectives(ModelView model, String nickname) {
        Integer[] cardIds = model.getPlayerEntity(nickname).getSecretObjectiveCardsIds();
        this.personalObjectiveIds = new ArrayList<>(Arrays.asList(cardIds));
        for (int i = 0; i < cardIds.length; i++) {
            int cardId = cardIds[i];
            String imagePath = "/images/cards/cards_front/" + String.format("%03d", cardId) + ".png";
            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
            imageView.setFitWidth(90); // Imposta la larghezza desiderata
            imageView.setFitHeight(65); // Imposta l'altezza desiderata
            if (i == 0) {
                personalObjective0Pane.getChildren().add(imageView); // Add the image to the VBox
            } else {
                personalObjective1Pane.getChildren().add(imageView); // Add the image to the VBox
            }
        }
    }

    public void setStarterCardFront(ModelView model, String nickname) {
        this.starterCard = model.getPlayerEntity(nickname).getStarterCardToChose().getFirst().getId();
        String imagePath = "/images/cards/cards_front/" + String.format("%03d", starterCard) + ".png";
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
        imageView.setFitWidth(90); // Imposta la larghezza desiderata
        imageView.setFitHeight(65); // Imposta l'altezza desiderata
        StarterCardImage.setImage(imageView.getImage());
        starterCardOrientation = Orientation.FRONT;
    }

    public void setStarterCardFront() {
        String imagePath = "/images/cards/cards_front/"+ String.format("%03d", starterCard) + ".png"; // Aggiungi l'id della carta al percorso
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
        imageView.setFitWidth(90); // Imposta la larghezza desiderata
        imageView.setFitHeight(65);; // Imposta l'altezza desiderata
        StarterCardImage.setImage(imageView.getImage());
        starterCardOrientation = Orientation.FRONT;
    }

    public void setStarterCardBack() {
        String imagePath = "/images/cards/cards_back/" + String.format("%03d", starterCard) + ".png";
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
        imageView.setFitWidth(90); // Imposta la larghezza desiderata
        imageView.setFitHeight(65); // Imposta l'altezza desiderata
        StarterCardImage.setImage(imageView.getImage());
        starterCardOrientation = Orientation.BACK;
    }

    public void flipStarterCard() {
        if (starterCardOrientation == Orientation.BACK)
            setStarterCardFront();
        else
            setStarterCardBack();
    }

    public void flipPersonalObjective(int cardId, int i) {
        String imagePath = "/images/cards/cards_back/"+String.format("%03d", cardId) + ".png";
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
        imageView.setFitWidth(90); // Imposta la larghezza desiderata
        imageView.setFitHeight(65); // Imposta l'altezza desiderata
        if (i == 0) {
            personalObjective0Pane.getChildren().add(imageView); // Add the image to the VBox
        } else {
            personalObjective1Pane.getChildren().add(imageView); // Add the image to the VBox
        }
        personalObjective1Pane.setDisable(true);
        personalObjective0Pane.setDisable(true);
    }

    public void setCardHand(ModelView model, String nickname) {
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

    public void flipCardHand() {
        if (cardHandOrientation == Orientation.BACK)
            setCardHandFront(cardHand);
        else
            setCardHandBack(cardHand);
    }


    //--------------------------------------DYNAMIC GAME-------------------------------------------

    public void ableStarterCardClick() {
        starterCardPane.setDisable(false);
        FlipStarter.setDisable(false);
        setMsgToShow("Choose the orientation of your starter card: " , true);
    }


    public void ableObjectiveCardsClick() {
        personalObjective0Pane.setDisable(false);
        personalObjective1Pane.setDisable(false);
        personalObjective0Pane.setMouseTransparent(false);
        personalObjective1Pane.setMouseTransparent(false);
        setMsgToShow("Choose one objective card: " , true);
    }

    @FXML
    public void onPersonalObjective1Clicked(MouseEvent event) {
        setMsgToShow("personalObjective0 clicked" , true);
        flipPersonalObjective(personalObjectiveIds.getFirst(), 0);

        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("2");
        } else {
            System.out.println("L'oggetto GuiReader è null.");
        }
    }

    @FXML
    public void onPersonalObjective0Clicked(MouseEvent event) {
        setMsgToShow("personalObjective1 clicked" , true);
        flipPersonalObjective(personalObjectiveIds.get(1), 1);
        it.polimi.demo.view.flow.utilities.GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("1");
        } else {
            System.out.println("L'oggetto GuiReader è null.");
        }
    }


    @FXML
    private void onStarterCardClicked(MouseEvent event) {
        if (starterCardOrientation == Orientation.FRONT) {
            setMsgToShow("Starter card clicked with orientation: FRONT", true);
        } else {
            setMsgToShow("Starter card clicked with orientation: BACK", true);
        }
        starterCardPane.setDisable(true);
        FlipStarter.setDisable(true);
        FlipStarter.setVisible(false);
        StarterCardImage.setVisible(false);

        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            if(starterCardOrientation == Orientation.FRONT)
                reader.addTxt("f");
            else if(starterCardOrientation == Orientation.BACK)
                reader.addTxt("b");
            else
                System.out.println("Orientation non valida.");
        } else {
            System.out.println("L'oggetto GuiReader è null.");
        }
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

    public void setPlayerDrawnCard(ModelView model, String nickname) {
        setCommonCards(model);
        setCardHand(model, nickname);
    }

    //mostra che la carta giocata non è più in mano
    public void setPlayerPlacedCard(ModelView model, String nickname) {
        setCardHand(model, nickname);
    }


    public void changeTurn(ModelView model, String nickname) {
        setMsgToShow("Next turn is up to: " + model.getCurrentPlayerNickname(), true);
    }


    //-------------------------------------CHAT, EVENTI E MESSAGI------------------------------------


    public void setImportantEvents(List<String> importantEvents) {
        eventsListView.getItems().clear();
        for (String s : importantEvents) {
            eventsListView.getItems().add(s);
        }
        eventsListView.scrollTo(eventsListView.getItems().size());
    }

    @FXML
    private void sendMessage() {
        String message = chatInput.getText();
        String recipient = recipientComboBox.getValue();
        if (!message.trim().isEmpty()) {
            if ("Everyone".equals(recipient)) {
                chatArea.appendText("Me (to everyone): " + message + "\n");
            } else {
                chatArea.appendText("Me (a " + recipient + "): " + message + "\n");
                // Logica per inviare il messaggio privato al destinatario specifico
                for (PlayerIC player : players_list) {
                    if (player.getNickname().equals(recipient)) {
                        sendPrivateMessage(player, message);
                        break;
                    }
                }
            }
            chatInput.clear();
        }
    }

//    public void actionSendMessage(MouseEvent e) {
//        if (!messageText.getText().isEmpty()) {
//            Sound.playSound("sendmsg.wav");
//
//            if (comboBoxMessage.getValue().toString().isEmpty()) {
//                getInputReaderGUI().addTxt("/c " + messageText.getText());
//            } else {
//                //Player wants to send a private message
//                getInputReaderGUI().addTxt("/cs " + comboBoxMessage.getValue().toString() + " " + messageText.getText());
//                comboBoxMessage.getSelectionModel().selectFirst();
//            }
//            messageText.setText("");
//
//
//        }
//    }

    private void sendPrivateMessage(PlayerIC player, String message) {
        // Logica per inviare un messaggio privato al giocatore
        // Questo potrebbe includere aggiornare la GUI del giocatore o inviare il messaggio attraverso la rete
        System.out.println("Messaggio privato a " + player.getNickname() + ": " + message);
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


