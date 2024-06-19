package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.ModelView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

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
    @FXML public VBox cardHandVBox;
    @FXML public Pane handCard0;
    @FXML public Pane handCard1;
    @FXML public Pane handCard2;
    @FXML public Button FlipHand;
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
    @FXML public AnchorPane personalBoardAnchorPane;
    @FXML public AnchorPane pb0;
    @FXML public AnchorPane pb1;
    @FXML public AnchorPane pb2;
    @FXML public ImageView personalObjective0;
    @FXML public ImageView personalObjective1;
    private Orientation cardHandOrientation;
    private Orientation starterCardOrientation;
    private ArrayList<Integer> cardHand = new ArrayList<>();
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
    private int player_index;
    private List<Pane> cardPanes;
    private List<Button> buttons;
    private Mapper mapper = new Mapper();

    private InverseMapper inverseMapper = new InverseMapper();
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
        pb0.setVisible(true);
        pb1.setVisible(true);
        pb2.setVisible(true);
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

        ImageView pieceToMove = pieces.get(player_index);

        for (ImageView bnImageView : bnImageViews) {
            if (bnImageView.getImage() == pieceToMove.getImage()) {
                bnImageView.setImage(null);
                break;
            }
        }
        bnImageViews[indexToGo].setImage(pieceToMove.getImage());

    }


    public void setPlayersPointsAndNicknames(ModelView model, String nickname) {
        ArrayList<Player> players_list = model.getAllPlayers();
        this.players_list = players_list;
        this.player_index = getPlayerIndex(players_list, nickname);
        recipientComboBox.getItems().clear();
        recipientComboBox.getItems().add("Everyone");
        int j = 0;
        for (Player player : players_list) {
            if (!player.getNickname().equals(nickname)) {
                // Add player to recipient combo box
                recipientComboBox.getItems().add(player.getNickname());
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
            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream == null) {
                System.out.println("Image not found: " + imagePath);
                continue;
            }
            ImageView imageView = new ImageView(new Image(imageStream));
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
            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream == null) {
                System.out.println("Image not found: " + imagePath);
                continue;
            }
            ImageView imageView = new ImageView(new Image(imageStream));
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
        String imagePath = "/images/cards/cards_back/" + String.format("%03d", starterCard) + ".png";
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            ImageView imageView = new ImageView(new Image(imageStream));
            imageView.setFitWidth(90); // Imposta la larghezza desiderata
            imageView.setFitHeight(65); // Imposta l'altezza desiderata

            StarterCardImage.setImage(imageView.getImage());
            starterCardOrientation = Orientation.FRONT;

        }else{
            System.out.println("Image not found: " + imagePath);
        }
    }

    public void setStarterCardFront() {
        String imagePath = "/images/cards/cards_back/"+ String.format("%03d", starterCard) + ".png"; // Aggiungi l'id della carta al percorso
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            ImageView imageView = new ImageView(new Image(imageStream));
            imageView.setFitWidth(90); // Imposta la larghezza desiderata
            imageView.setFitHeight(65); // Imposta l'altezza desiderata

            StarterCardImage.setImage(imageView.getImage());
            starterCardOrientation = Orientation.FRONT;

        }else{
            System.out.println("Image not found: " + imagePath);
        }
    }

    public void setStarterCardBack() {
        String imagePath = "/images/cards/cards_front/" + String.format("%03d", starterCard) + ".png";
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            ImageView imageView = new ImageView(new Image(imageStream));
            imageView.setFitWidth(90); // Imposta la larghezza desiderata
            imageView.setFitHeight(65); // Imposta l'altezza desiderata

            StarterCardImage.setImage(imageView.getImage());
            starterCardOrientation = Orientation.BACK;

        }else{
            System.out.println("Image not found: " + imagePath);
        }

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
            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream == null) {
                System.out.println("Image not found: " + imagePath);
                continue;
            }

            ImageView imageView = new ImageView(new Image(imageStream));
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
            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream == null) {
                System.out.println("Image not found: " + imagePath);
                continue;
            }

            ImageView imageView = new ImageView(new Image(imageStream));
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

        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("2");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void onPersonalObjective0Clicked(MouseEvent event) {
        setMsgToShow("personalObjective1 clicked" , true);
        flipPersonalObjective(personalObjectiveIds.get(1), 1);
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("1");
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

        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            if(starterCardOrientation == Orientation.FRONT)
                reader.add("f");
            else if(starterCardOrientation == Orientation.BACK)
                reader.add("b");
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

        handCard0.setDisable(true);

        FlipHand.setDisable(true);

        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("1");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }

        if (reader != null) {
            if(cardHandOrientation == Orientation.FRONT)
                reader.add("f");
            else if(cardHandOrientation == Orientation.BACK)
                reader.add("b");
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

        handCard1.setDisable(true);

        FlipHand.setDisable(true);

        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("2");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }

        if (reader != null) {
            if(cardHandOrientation == Orientation.FRONT)
                reader.add("f");
            else if(cardHandOrientation == Orientation.BACK)
                reader.add("b");
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

        handCard2.setDisable(true);

        FlipHand.setDisable(true);

        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("3");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }

        if (reader != null) {
            if(cardHandOrientation == Orientation.FRONT)
                reader.add("f");
            else if(cardHandOrientation == Orientation.BACK)
                reader.add("b");
            else
                System.out.println("Orientation non valida.");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        placeCard(2);
    }

    //-------------------------------------------------------------------------------------------------------------------------
    public void setPersonalBoard(ModelView gameModel) {
        Player player = gameModel.getPlayersConnected().getFirst();
        int playerIndex = getPlayerIndex(gameModel.getAllPlayers(), player.getNickname());
        ArrayList<Integer> last_chosen_card = gameModel.getLastChosenCardAndPosition();
        Orientation last_chosen_orientation = gameModel.getLastChosenOrientation();

        placeOthersPlayersCard(playerIndex, last_chosen_card, last_chosen_orientation, gameModel.getLastCoordinate());
    }

    private void placeOthersPlayersCard(int playerIndex, ArrayList<Integer> lastChosenCard, Orientation lastChosenOrientation, Coordinate coord) {
        String imagePath;
        System.out.println("lastChosenCardId: " + lastChosenCard);
        String formattedCardId = String.format("%03d", lastChosenCard.getFirst());
        if(lastChosenOrientation == Orientation.FRONT){
            imagePath = "/images/cards/cards_front/" + formattedCardId + ".png";
        }else if(lastChosenOrientation == Orientation.BACK){
            imagePath = "/images/cards/cards_back/" + formattedCardId + ".png";
        } else {
            System.out.println("Orientation non valida.");
            return;
        }
        ImageView CardPic = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
        CardPic.setFitWidth(90); // Imposta la larghezza desiderata
        CardPic.setFitHeight(65); // Imposta l'altezza desiderata

        int[] result = {lastChosenCard.get(1) - 250, lastChosenCard.get(2) - 250};

        switch (coord) {
            case NE -> result[0] = result[0] - 1;
            case NW -> {
                result[0] = result[0] - 1;
                result[1] = result[1] - 1;
            }
            case SW -> result[1] = result[1] - 1;
            case SE -> {
                // it's correct
            }
        }

        String resultString = String.format("%d,%d", result[0], result[1]);

        int[] position = inverseMapper.getInverseMappedPosition(resultString);

        CardPic.setLayoutX((double)position[0]);
        CardPic.setLayoutY((double)position[1]);

        // Verifica e aggiorna le dimensioni dell'AnchorPane
        double newWidth = position[0] + CardPic.getFitWidth();
        double newHeight = position[1]  + CardPic.getFitHeight();

        System.out.println("add card for: " + players_list.get(playerIndex).getNickname());
        switch (playerIndex){
            case 0 -> {
                if (newWidth > pb0.getPrefWidth()) {
                    pb0.setPrefWidth(newWidth + 10);
                }

                if (newHeight > pb0.getPrefHeight()) {
                    pb0.setPrefHeight(newHeight + 10);
                }
                pb0.getChildren().add(CardPic);
            }
            case 1 -> {
                if (newWidth > pb1.getPrefWidth()) {
                    pb1.setPrefWidth(newWidth + 10);
                }

                if (newHeight > pb1.getPrefHeight()) {
                    pb1.setPrefHeight(newHeight + 10);
                }
                pb1.getChildren().add(CardPic);
            }
            case 2 -> {
                if (newWidth > pb2.getPrefWidth()) {
                    pb2.setPrefWidth(newWidth + 10);
                }

                if (newHeight > pb2.getPrefHeight()) {
                    pb2.setPrefHeight(newHeight + 10);
                }
                pb2.getChildren().add(CardPic);
            }
        }
        System.out.println("card added ");
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
        String resultString = String.format("%d,%d", 0, 0);
        int[] position = inverseMapper.getInverseMappedPosition(resultString);

        double xCenter = (double)position[0];
        double yCenter = (double)position[1];
        String imagePath;
        // Posiziona la carta iniziale al centro
        String formattedCardId = String.format("%03d", starterCard);
        if(starterCardOrientation == Orientation.FRONT){
            imagePath = "/images/cards/cards_back/" + formattedCardId + ".png";
        }else if(starterCardOrientation == Orientation.BACK){
            imagePath = "/images/cards/cards_front/" + formattedCardId + ".png";
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

    //-------------------------------------------------------------------------------------------------------------------------

    public void whichCardToPlace() {
        cardHandVBox.setDisable(false);
        cardHandVBox.setMouseTransparent(false);
        handCard0.setDisable(false);
        handCard1.setDisable(false);
        handCard2.setDisable(false);
        FlipHand.setDisable(false);
        setMsgToShow("Choose a card to place from your hand: ", true);
    }

    public void whichOrientationToPlace() {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            if(cardHandOrientation == Orientation.FRONT)
                reader.add("f");
            else if(cardHandOrientation == Orientation.BACK)
                reader.add("b");
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
            //System.out.println("coordinate selezionate: (" + result[0] + ", " + result[1] + ")");
            LinkedBlockingQueue<String> reader = getInputReaderGUI();
            if (reader != null) {
                //System.out.println("(x,y): " + result[0] + ", " + result[1]);
                reader.add(String.valueOf(result[0]));
                reader.add(String.valueOf(result[1]));
            } else {
                System.out.println("L'oggetto inputReaderGUI è null.");
            }
        } else {
            System.out.println("posizione non valida");
        }
    }


    private void reallyPlaceCard(double clickX, double clickY, Coordinate coord) {
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

        int[] result = mapper.getMappedPosition(clickX, clickY);

        switch (coord) {
            case NE -> result[0] = result[0] - 1;
            case NW -> {
                result[0] = result[0] - 1;
                result[1] = result[1] - 1;
            }
            case SW -> result[1] = result[1] - 1;
            case SE -> {
                // it's correct
            }
        }
        //System.out.println("coordinate selezionate result: (" + result[0] + ", " + result[1] + ")");

        String resultString = String.format("%d,%d", result[0], result[1]);
        //System.out.println(resultString);

        int[] position = inverseMapper.getInverseMappedPosition(resultString);
        //System.out.println("coordinate selezionate position: (" + position[0] + ", " + position[1] + ")");

        CardPic.setLayoutX((double)position[0]);
        CardPic.setLayoutY((double)position[1]);

        // Verifica e aggiorna le dimensioni dell'AnchorPane
        double newWidth = position[0] + CardPic.getFitWidth();
        double newHeight = position[1] + CardPic.getFitHeight();

        if (newWidth > personalBoardAnchorPane.getPrefWidth()) {
            personalBoardAnchorPane.setPrefWidth(newWidth + 10);
        }

        if (newHeight > personalBoardAnchorPane.getPrefHeight()) {
            personalBoardAnchorPane.setPrefHeight(newHeight + 10);
        }

        handCard0.setStyle(null);
        handCard1.setStyle(null);
        handCard2.setStyle(null);

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
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("1");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        commonCardsVbox.setDisable(true);
        commonIndex = 1;
    }
    @FXML
    public void commonCard2Clicked(MouseEvent mouseEvent) {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("2");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        commonCardsVbox.setDisable(true);
        commonIndex = 2;
    }
    @FXML
    public void commonCard3Clicked(MouseEvent mouseEvent) {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("3");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        commonCardsVbox.setDisable(true);
        commonIndex = 3;
    }
    @FXML
    public void commonCard4Clicked(MouseEvent mouseEvent) {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("4");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        commonCardsVbox.setDisable(true);
        commonIndex = 4;
    }
    @FXML
    public void commonCard5Clicked(MouseEvent mouseEvent) {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("5");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        commonCardsVbox.setDisable(true);
        commonIndex = 5;
    }
    @FXML
    public void commonCard6Clicked(MouseEvent mouseEvent) {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("6");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
        commonCardsVbox.setDisable(true);
        commonIndex = 6;
    }

    public void illegalMove() {
        setMsgToShow("Illegal move! Choose different coordinates or flip card! \n", false);
        cardHandVBox.setDisable(false);
        cardHandVBox.setMouseTransparent(false);
        handCard0.setDisable(false);
        handCard1.setDisable(false);
        handCard2.setDisable(false);
        FlipHand.setDisable(false);
        personalBoardAnchorPane.setDisable(false);
    }

    public void illegalMovePlace() {
        cardHandVBox.setDisable(false);
        cardHandVBox.setMouseTransparent(false);
        handCard0.setDisable(false);
        handCard1.setDisable(false);
        handCard2.setDisable(false);
        FlipHand.setDisable(false);
        personalBoardAnchorPane.setDisable(false);
    }

    public void illegalMoveBecauseOf(String message) {
        //setMsgToShow(message, false);
    }



    public void successfulMove(Coordinate coord) {
        reallyPlaceCard(mapper.getMinCorner(chosenX,chosenY)[0], (mapper.getMinCorner(chosenX,chosenY)[1]), coord);
        setMsgToShow("Valid position!", true);
        personalBoardAnchorPane.setDisable(true);
    }



    //-------------------------------------CHAT, EVENTI E MESSAGI------------------------------------


//    public void setImportantEvents(List<String> importantEvents) {
//        eventsListView.getItems().clear();
//        for (String s : importantEvents) {
//            eventsListView.getItems().add(s);
//        }
//        eventsListView.scrollTo(eventsListView.getItems().size());
//    }

    @FXML
    private void ActionSendMessage() {
        String message = chatInput.getText();
        String recipient = recipientComboBox.getValue();
        if (!message.trim().isEmpty()) {
            if ("Everyone".equals(recipient)) {
                getInputReaderGUI().add("/c " + chatInput.getText());
                chatArea.appendText("Me (to everyone): " + message + "\n");
            } else {
                chatArea.appendText("Me (to " + recipient + "): " + message + "\n");
                getInputReaderGUI().add("/cs " + recipientComboBox.getValue().toString() + " " + chatInput.getText());
            }
            chatInput.clear();
        }
    }


    public void updateChat(ModelView model, String sender) {
        Message message = model.getChat().getLastMessage();
        chatArea.appendText(sender + ": " + message.text() + "\n");
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

    public void exitGame(ActionEvent actionEvent) {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("/quit");
            reader.add("/leave");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }
}


