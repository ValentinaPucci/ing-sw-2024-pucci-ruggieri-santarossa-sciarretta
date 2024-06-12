package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.model.Model;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.ModelView;
import it.polimi.demo.view.dynamic.utilities.GuiReader;
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
    @FXML public Label playerLabel1;
    @FXML public Label playerLabel2;
    @FXML public Label playerLabel3;
    public ArrayList<Label> othersNicknames = new ArrayList<>();
    @FXML public GridPane otherPlayers;
    @FXML public ImageView StarterCardImage;
    @FXML public Pane starterCardPane;
    public Button FlipStarter;
    public Pane personalObjective0Pane;
    public Pane personalObjective1Pane;
    @FXML public HBox personalObjectivesBox;
    @FXML public AnchorPane personalBoardAnchorPane;
    @FXML public VBox cardHandVBox;
    @FXML public Pane handCard0;
    @FXML public Pane handCard1;
    @FXML public Pane handCard2;
    @FXML public VBox commonCardsVbox;
    @FXML public Pane commonCard1;
    @FXML public Pane commonCard2;
    @FXML public Pane commonCard3;
    @FXML public Pane commonCard4;
    @FXML public Pane commonCard5;
    @FXML public Pane commonCard6;
    @FXML public Pane commonCard7;
    @FXML public Pane commonCard8;
    @FXML public Pane commonCard9;
    @FXML public AnchorPane personalBoardPlayer1;
    @FXML public AnchorPane personalBoardPlayer2;
    @FXML public AnchorPane personalBoardPlayer3;
    @FXML public AnchorPane personalBoardPlayer4;

    @FXML private AnchorPane mainAnchor;
    @FXML public ImageView personalObjective0;
    @FXML public ImageView personalObjective1;

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
    private ArrayList<Player> players_list;
    private List<Pane> cardPanes;
    private List<Button> buttons;
    private Mapper mapper = new Mapper();
    private int chosenCard = 0;
    double chosenX = 0;
    double chosenY = 0;
    int cardIndex = 0;
    int commonIndex = 0;
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
            if(i==0) {
                commonCard1.getChildren().add(imageView);
            } else if (i==1) {
                commonCard2.getChildren().add(imageView);
            } else if (i==2) {
                commonCard3.getChildren().add(imageView);
            } else if (i==3) {
                commonCard4.getChildren().add(imageView);
            } else if (i==4) {
                commonCard5.getChildren().add(imageView);
            } else {
                commonCard6.getChildren().add(imageView);
            }
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


        cardHandVBox = new VBox();
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView();
            imageView.setFitWidth(90);
            imageView.setFitHeight(65);
            imageView.setImage(null);
            if(i==0) {
                handCard0.getChildren().add(imageView);
            } else if (i==1) {
                handCard1.getChildren().add(imageView);
            } else {
                handCard2.getChildren().add(imageView);
            }
        }

        ImageView starterCardView = new ImageView();
        starterCardView.setFitWidth(90);
        starterCardView.setFitHeight(65);
        starterCardView.setImage(null);
        starterCardPane.getChildren().add(starterCardView);

        cardHand.add(0);
        cardHand.add(0);
        cardHand.add(0);

        othersNicknames.add(playerLabel1);
        othersNicknames.add(playerLabel2);
        othersNicknames.add(playerLabel3);

        //disable all cards
        cardPanes = Arrays.asList(starterCardPane, cardHandVBox, commonCardsVbox, personalObjective0Pane, personalObjective1Pane);
        setComponentsDisable(cardPanes, true);

        personalBoardAnchorPane.setOnMouseClicked(this::handleMouseClick);

        personalBoardAnchorPane.setVisible(true);
        personalBoardPlayer1.setVisible(false);
        personalBoardPlayer2.setVisible(false);
        personalBoardPlayer3.setVisible(false);
        personalBoardPlayer4.setVisible(false);

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
        ArrayList<Player> players_list = model.getAllPlayers();
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
        for (Player player : players_list) {
            if (!player.getNickname().equals(nickname)) {
                // Add player to recipient combo box
                recipientComboBox.getItems().add(player.getNickname());
                // Create and offer player label
                Label playerLabel = new Label(player.getNickname());
                playerLabel.setTextFill(Color.web("#4d0202"));
                playerLabel.setAlignment(javafx.geometry.Pos.CENTER);
                playerLabel.setFont(javafx.scene.text.Font.font("Luminari", 19));
                GridPane.setRowIndex(playerLabel, j + 1);
                GridPane.setColumnIndex(playerLabel, 0);
                otherPlayers.getChildren().add(playerLabel);

                // Create and offer button
                Button goButton = new Button("GO");
                goButton.setPrefHeight(26.0);
                goButton.setPrefWidth(188.0);
                goButton.setEffect(new javafx.scene.effect.ColorAdjust(0.17, 0.3, 0.0, 0.0));
                goButton.setOnAction((ActionEvent event) -> {
                    showOthersPersonalBoard(getPlayerIndex(players_list, player.getNickname()));
                });
                GridPane.setRowIndex(goButton, j + 1);
                GridPane.setColumnIndex(goButton, 1);
                otherPlayers.getChildren().add(goButton);

                // Add row constraints
                otherPlayers.getRowConstraints().add(new RowConstraints(30.0, 30.0, Double.MAX_VALUE));
                j++;
            }
        }
    }

    public int getPlayerIndex(ArrayList<Player> players_list, String nickname) {
        for (int i = 0; i < players_list.size(); i++) {
            if (players_list.get(i).getNickname().equals(nickname)) {
                return i;
            }
        }
        return -1; // Restituisce -1 se il player non è trovato
    }

    public void setOthersPersonalBoard(PersonalBoard personalBoard, int player_index) {
        // Hide all boards first
        personalBoardPlayer1.setVisible(false);
        personalBoardPlayer2.setVisible(false);
        personalBoardPlayer3.setVisible(false);
        personalBoardPlayer4.setVisible(false);
        personalBoardAnchorPane.setVisible(false);

        // Show the correct board based on the player's index
        switch (player_index) {
            case 0:
                personalBoardAnchorPane.setVisible(false);
                personalBoardPlayer1.setVisible(true);
                break;
            case 1:
                personalBoardAnchorPane.setVisible(false);
                personalBoardPlayer2.setVisible(true);
                break;
            case 2:
                personalBoardAnchorPane.setVisible(false);
                personalBoardPlayer3.setVisible(true);
                break;
            case 3:
                personalBoardAnchorPane.setVisible(false);
                personalBoardPlayer4.setVisible(true);
                break;
            default:
                break;
        }
    }

    public void showOthersPersonalBoard(int index) {
        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            // Show the correct board based on the player's index or name
            switch (index) {
                case 0:
                    reader.addTxt("/pb0");
                    break;
                case 1:
                    reader.addTxt("/pb1");
                    break;
                case 2:
                    reader.addTxt("/pb2");
                    break;
                case 3:
                    reader.addTxt("/pb3");
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }

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
            if (i == 0) {
                commonCard1.getChildren().add(imageView); // Add the image to the VBox
            } else if (i == 1) {
                commonCard2.getChildren().add(imageView); // Add the image to the VBox
            } else if (i == 2) {
                commonCard3.getChildren().add(imageView); // Add the image to the VBox
            } else if (i == 3) {
                commonCard4.getChildren().add(imageView); // Add the image to the VBox
            } else if (i == 4) {
                commonCard5.getChildren().add(imageView); // Add the image to the VBox
            } else if (i == 5) {
                commonCard6.getChildren().add(imageView); // Add the image to the VBox
            } else if (i == 6) {
                commonCard7.getChildren().add(imageView); // Add the image to the VBox
            } else if (i == 7) {
                commonCard8.getChildren().add(imageView); // Add the image to the VBox
            } else {
                commonCard9.getChildren().add(imageView); // Add the image to the VBox
            }
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
            if(i==0) {
                handCard0.getChildren().add(imageView);
            } else if (i==1) {
                handCard1.getChildren().add(imageView);
            } else {
                handCard2.getChildren().add(imageView);
            }
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
            if(i==0) {
                handCard0.getChildren().add(imageView);
            } else if (i==1) {
                handCard1.getChildren().add(imageView);
            } else {
                handCard2.getChildren().add(imageView);
            }
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
        setMsgToShow("Choose the orientation of your starter card: ", true);
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
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void onPersonalObjective0Clicked(MouseEvent event) {
        setMsgToShow("personalObjective1 clicked" , true);
        flipPersonalObjective(personalObjectiveIds.get(1), 1);
        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("1");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
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
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        placeStarterCard();
    }

    public void onCardHand0Clicked(MouseEvent mouseEvent) {
        if (cardHandOrientation == Orientation.FRONT) {
            setMsgToShow("Card 1 from hand clicked with orientation: FRONT", true);
        } else {
            setMsgToShow("Card 1 from hand clicked with orientation: BACK", true);
        }
        cardHandVBox.setDisable(true);

        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("1");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }

        if (reader != null) {
            if(cardHandOrientation == Orientation.FRONT)
                reader.addTxt("f");
            else if(cardHandOrientation == Orientation.BACK)
                reader.addTxt("b");
            else
                System.out.println("Orientation non valida.");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        placeCard(0);
    }

    @FXML
    public void onCardHand1Clicked(MouseEvent mouseEvent) {
        if (cardHandOrientation == Orientation.FRONT) {
            setMsgToShow("Card 2 from hand clicked with orientation: FRONT", true);
        } else {
            setMsgToShow("Card 2 from hand clicked with orientation: BACK", true);
        }
        cardHandVBox.setDisable(true);

        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("2");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }

        if (reader != null) {
            if(cardHandOrientation == Orientation.FRONT)
                reader.addTxt("f");
            else if(cardHandOrientation == Orientation.BACK)
                reader.addTxt("b");
            else
                System.out.println("Orientation non valida.");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        placeCard(1);
    }

    @FXML
    public void onCardHand2Clicked(MouseEvent mouseEvent) {
        if (cardHandOrientation == Orientation.FRONT) {
            setMsgToShow("Card 3 from hand clicked with orientation: FRONT", true);
        } else {
            setMsgToShow("Card 3 from hand clicked with orientation: BACK", true);
        }
        cardHandVBox.setDisable(true);

        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("3");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }

        if (reader != null) {
            if(cardHandOrientation == Orientation.FRONT)
                reader.addTxt("f");
            else if(cardHandOrientation == Orientation.BACK)
                reader.addTxt("b");
            else
                System.out.println("Orientation non valida.");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        placeCard(2);
    }

    private void placeCard(int index) {
        cardIndex = index;
        if(index ==  0){
            handCard0.setStyle("-fx-border-color: blue; -fx-border-width: 3;");
            chosenCard = cardHand.getFirst();
        } else if (index ==  1) {
            handCard1.setStyle("-fx-border-color: blue; -fx-border-width: 3;");
            chosenCard = cardHand.get(1);
        } else {
            handCard2.setStyle("-fx-border-color: blue; -fx-border-width: 3;");
            chosenCard = cardHand.get(2);
        }
        handCard0.setDisable(true);
        handCard1.setDisable(true);
        handCard2.setDisable(true);
        setMsgToShow("Select where to put the chosen card", true);
        personalBoardAnchorPane.setDisable(false);
    }
    private void placeStarterCard() {
        double xCenter = personalBoardAnchorPane.getWidth() / 2;
        double yCenter = personalBoardAnchorPane.getHeight() / 2;
        String imagePath;
        // Posiziona la carta iniziale al centro
        String formattedCardId = String.format("%03d", starterCard);
        if(starterCardOrientation == Orientation.FRONT){
            imagePath = "/images/cards/cards_front/" + formattedCardId + ".png";
        }else if(starterCardOrientation == Orientation.BACK){
            imagePath = "/images/cards/cards_back/" + formattedCardId + ".png";
        } else {
            System.out.println("Orientation non valida.");
            return;
        }
        ImageView StarterCardPic = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
        StarterCardPic.setFitWidth(90); // Imposta la larghezza desiderata
        StarterCardPic.setFitHeight(65); // Imposta l'altezza desiderata
        StarterCardPic.setLayoutX(xCenter);
        StarterCardPic.setLayoutY(yCenter);
        personalBoardAnchorPane.getChildren().add(StarterCardPic);
        setMsgToShow("StarterCard placed", true);
    }
    public void whichCardToPlace() {
        cardHandVBox.setDisable(false);
        cardHandVBox.setMouseTransparent(false);
        cardPanes.forEach(pane -> pane.setDisable(false));
        setMsgToShow("Choose a card to place from your hand: ", true);
    }

    public void whichOrientationToPlace() {
        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            if(cardHandOrientation == Orientation.FRONT)
                reader.addTxt("f");
            else if(cardHandOrientation == Orientation.BACK)
                reader.addTxt("b");
            else
                System.out.println("Orientation non valida.");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    public void setPlayerDrawnCard(ModelView model, String nickname) {
        setCommonCards(model);
        setCardHand(model, nickname);
    }


    public void changeTurn(ModelView model, String nickname) {
        setMsgToShow("Next turn is up to: " + model.getCurrentPlayerNickname(), true);
    }


    public void myTurnIsFinished() {
        setMsgToShow("Your turn is finished. Now, wait until it is again your turn! ", true);
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        personalBoardAnchorPane.setDisable(true);

        double clickX = event.getX();
        double clickY = event.getY();
        chosenX = clickX;
        chosenY = clickY;

        int[] result = mapper.getMappedPosition(clickX, clickY);

        if (result != null) {
            System.out.println("coordinate selezionate: (" + result[0] + ", " + result[1] + ")");
            GuiReader reader = getInputReaderGUI();
            if (reader != null) {
                //System.out.println("(x,y): " + result[0] + ", " + result[1]);
                reader.addTxt(String.valueOf(result[0]));
                reader.addTxt(String.valueOf(result[1]));
            } else {
                System.out.println("L'oggetto inputReaderGUI è null.");
            }
        } else {
            System.out.println("posizione non valida");
        }
    }


    private void reallyPlaceCard(double clickX, double clickY) {
        String imagePath;
        String formattedCardId = String.format("%03d", chosenCard);
        if(cardHandOrientation == Orientation.FRONT){
            imagePath = "/images/cards/cards_front/" + formattedCardId + ".png";
        }else if(cardHandOrientation == Orientation.BACK){
            imagePath = "/images/cards/cards_back/" + formattedCardId + ".png";
        } else {
            System.out.println("Orientation non valida.");
            return;
        }
        ImageView CardPic = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
        CardPic.setFitWidth(90); // Imposta la larghezza desiderata
        CardPic.setFitHeight(65); // Imposta l'altezza desiderata
        CardPic.setLayoutX(clickX);
        CardPic.setLayoutY(clickY);
        personalBoardAnchorPane.getChildren().add(CardPic);
        setMsgToShow("Card placed", true);
        removeFromHand(cardIndex);
    }

    private void removeFromHand(int index) {
        if (index == 0) {
            handCard0.getChildren().clear();
        } else if (index == 1) {
            handCard1.getChildren().clear();
        } else {
            handCard2.getChildren().clear();
        }
    }

    public void ableCommonCardsClick() {
        commonCardsVbox.setDisable(false);
        setMsgToShow("Choose a common card ", true);
    }

    @FXML
    public void commonCard1Clicked(MouseEvent mouseEvent) {
        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("2");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        commonCardsVbox.setDisable(true);
        commonIndex = 1;
    }
    @FXML
    public void commonCard2Clicked(MouseEvent mouseEvent) {
        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("2");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        commonCardsVbox.setDisable(true);
        commonIndex = 2;
    }
    @FXML
    public void commonCard3Clicked(MouseEvent mouseEvent) {
        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("3");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        commonCardsVbox.setDisable(true);
        commonIndex = 3;
    }
    @FXML
    public void commonCard4Clicked(MouseEvent mouseEvent) {
        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("4");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        commonCardsVbox.setDisable(true);
        commonIndex = 4;
    }
    @FXML
    public void commonCard5Clicked(MouseEvent mouseEvent) {
        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("5");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        commonCardsVbox.setDisable(true);
        commonIndex = 5;
    }
    @FXML
    public void commonCard6Clicked(MouseEvent mouseEvent) {
        GuiReader reader = getInputReaderGUI();
        if (reader != null) {
            reader.addTxt("6");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        commonCardsVbox.setDisable(true);
        commonIndex = 6;
    }

    public void illegalMove() {
        setMsgToShow("Card can't be placed there, retry", false);
        personalBoardAnchorPane.setDisable(false);
    }


    public void successfulMove() {
        reallyPlaceCard(mapper.getMinCorner(chosenX,chosenY)[0], (mapper.getMinCorner(chosenX,chosenY)[1]));
        setMsgToShow("Posizione valida!", true);
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
    private void ActionSendMessage() {
        String message = chatInput.getText();
        String recipient = recipientComboBox.getValue();
        if (!message.trim().isEmpty()) {
            if ("Everyone".equals(recipient)) {
                getInputReaderGUI().addTxt("/c " + chatInput.getText());
                chatArea.appendText("Me (to everyone): " + message + "\n");
            } else {
                chatArea.appendText("Me (to " + recipient + "): " + message + "\n");
                getInputReaderGUI().addTxt("/cs " + recipientComboBox.getValue().toString() + " " + chatInput.getText());
            }
            chatInput.clear();
        }
    }


    public void updateChat(ModelView model, String sender) {
        Message message = model.getChat().getLastMessage();
        chatArea.appendText(sender + ": " + message.getText() + "\n");
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

    @FXML
    private void hideAllPersonalBoards() {
        personalBoardPlayer1.setVisible(false);
        personalBoardPlayer2.setVisible(false);
        personalBoardPlayer3.setVisible(false);
        personalBoardPlayer3.setVisible(false);
        personalBoardAnchorPane.setVisible(true);
    }


}


