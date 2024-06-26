package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.model.enumerations.Orientation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;

/**
 * RunningController is the controller class for the running phase of the game.
 */
public class RunningController extends GuiInputReaderController {
    @FXML public Label myPoints;
    @FXML public Label playerLabel1;
    @FXML public Label playerLabel2;
    @FXML public Label playerLabel3;
    public ArrayList<Label> othersNicknames = new ArrayList<>();
    @FXML public GridPane otherPlayers;
    @FXML public ImageView StarterCardImage;
    @FXML public Pane starterCardPane;
    @FXML public Button FlipStarter;
    @FXML public Pane personalObjective0Pane;
    @FXML public Pane personalObjective1Pane;
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
    @FXML public ScrollPane sp1;
    @FXML public ScrollPane sp2;
    @FXML public ScrollPane sp0;
    @FXML public ScrollPane my_sp;
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
    @FXML private ComboBox<String> recipientComboBox;
    private Orientation cardHandOrientation;
    private Orientation starterCardOrientation;
    private ArrayList<Integer> cardHand = new ArrayList<>();
    private int starterCard = 0;
    private ArrayList<Player> players_list;
    private int my_index = 0;
    private List<Pane> cardPanes;
    private Mapper mapper = new Mapper();
    private InverseMapper inverseMapper = new InverseMapper();
    private int chosenCard = 0;
    double chosenX = 0;
    private double chosenY = 0;
    private int cardIndex = 0;
    int commonIndex = 0;
    private ArrayList<Player> players_without_me = new ArrayList<>();
    int players_without_me_size = 0;
    private ArrayList<Integer> personalObjectiveIds = new ArrayList<>();
    private int rounds = 0;
    private int firstPlayerIndex = 0;
    boolean cc1_ended = false;
    boolean cc2_ended = false;
    boolean cc3_ended = false;
    boolean cc4_ended = false;
    boolean cc5_ended = false;
    boolean cc6_ended = false;
    boolean cc7_ended = false;
    boolean cc8_ended = false;
    boolean cc9_ended = false;
    int offset_x = 280;
    int offset_y = 200;

    /**
     * Method that initializes every structure on the screen
     */
    public void initialize() {

        preloadImages();

        personalObjectivesBox = new HBox();

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

        cardPanes = Arrays.asList(starterCardPane, cardHandVBox, commonCardsVbox, personalObjective0Pane, personalObjective1Pane, handCard0, handCard1, handCard2);
        setComponentsDisable(cardPanes, true);

        personalBoardAnchorPane.setOnMouseClicked(this::handleMouseClick);

        double initialHValue = 600.0 / (1500 - my_sp.getViewportBounds().getWidth());
        double initialVValue = 400.0 / (1500 - my_sp.getViewportBounds().getHeight());
        my_sp.setHvalue(initialHValue);
        my_sp.setVvalue(initialVValue);
        personalBoardAnchorPane.setVisible(true);
        my_sp.setVisible(true);

        sp0.setHvalue(initialHValue);
        sp0.setVvalue(initialVValue);
        pb0.setVisible(false);
        sp0.setVisible(false);

        sp1.setHvalue(initialHValue);
        sp1.setVvalue(initialVValue);
        pb1.setVisible(false);
        sp1.setVisible(false);

        sp2.setHvalue(initialHValue);
        sp2.setVvalue(initialVValue);
        pb2.setVisible(false);
        sp2.setVisible(false);

        pb0.setDisable(false);
        pb1.setDisable(false);
        pb2.setDisable(false);
    }


    private static final Map<String, Image> preloadedImages = new HashMap<>();

    public void preloadImages() {
        for (int i = 1; i <= 102; i++) {
            String formattedCardId = String.format("%03d", i);
            for (Orientation orientation : Orientation.values()) {
                String imagePath = switch (orientation) {
                    case FRONT -> "/images/cards/cards_front/" + formattedCardId + ".png";
                    case BACK -> "/images/cards/cards_back/" + formattedCardId + ".png";
                };
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
                preloadedImages.put(formattedCardId + orientation, image);
            }
        }

        // Preload placeholder image
        String placeholderImagePath = "/images/placeholder.jpg";
        Image placeholderImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(placeholderImagePath)));
        preloadedImages.put("placeholder", placeholderImage);

        // Preload piece images
        for (int i = 1; i <= 4; i++) {
            String pieceImagePath = "/images/pieces/piece" + i + ".png";
            Image pieceImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(pieceImagePath)));
            preloadedImages.put("piece" + (i-1), pieceImage);
        }

        // Preload the black piece image
        String blackPieceImagePath = "/images/pieces/piece0.png";
        Image blackPieceImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(blackPieceImagePath)));
        preloadedImages.put("pieceBlack", blackPieceImage);
    }


    /**
    *    Method that is used to disable a group of components in a single call
    * */
    private void setComponentsDisable(List<? extends Node> components, boolean disable) {
        for (Node component : components) {
            component.setDisable(disable);
        }
    }

    /**
     * Method that get the player index in the given array, based on his nickname
     * */
    public int getPlayerIndex(ArrayList<Player> players_list, String nickname) {
        for (int i = 0; i < players_list.size(); i++) {
            if (players_list.get(i).getNickname().equals(nickname)) {
                return i;
            }
        }
        return -1;
    }

    //---------------------------------------SCOREBOARD----------------------------------------------------

    /**
     * Method that move pieces into the scoreboard according to the players' points in the model
     * */
    public void setScoreBoardPosition(ModelView model) {
        for (int i = 0; i < model.getAllPlayers().size(); i++) {
            int player_position = model.getCommonBoard().getPlayerPosition(i);
            movePieceToPosition(i, player_position);
        }
    }

    /**
     * Method that move a piece in a given position of the scoreboard
     * */
    private void movePieceToPosition(int player_index, int indexToGo) {

        // Retrieve the piece image from the preloadedImages map
        Image pieceToMoveImage = preloadedImages.get("piece" + player_index);

        // Find the ImageView with the matching Image and clear it
        for (ImageView bnImageView : bnImageViews) {
            if (bnImageView.getImage() == pieceToMoveImage) {
                bnImageView.setImage(null);
                break;
            }
        }

        // Set the piece image to the new position
        bnImageViews[indexToGo].setImage(pieceToMoveImage);
    }

    //-------------------------------OTHER PLAYERS--------------------------------------------

    /**
     * method that sets names and points of the others players, and creates the button GO to see their personaBoards
     * */
    public void setGridPaneAndChat(ModelView model, String nickname) {
        ArrayList<Player> players_list = model.getAllPlayers();

        this.players_list = players_list;
        this.firstPlayerIndex = model.getAllPlayers().indexOf(model.getFirstPlayer());

        recipientComboBox.getItems().clear();
        recipientComboBox.getItems().add("Everyone");

        // Clear existing rows except the header
        otherPlayers.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);
        while (otherPlayers.getRowConstraints().size() > 1) {
            otherPlayers.getRowConstraints().remove(1);
        }

        myPoints.setText("0");

        int j = 0;
        for (Player player : players_list) {
            if (!player.getNickname().equals(nickname)) {
                players_without_me.add(j, player);
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
                Label pointsLabel = new Label("0");
                pointsLabel.setTextFill(Color.web("#4d0202"));
                pointsLabel.setAlignment(javafx.geometry.Pos.CENTER);
                pointsLabel.setFont(javafx.scene.text.Font.font("Luminari", 19));
                GridPane.setRowIndex(pointsLabel, j + 1);
                GridPane.setColumnIndex(pointsLabel, 1);
                otherPlayers.getChildren().add(pointsLabel);

                // Create and offer button
                Button goButton = new Button("GO");
                goButton.setPrefHeight(26.0);
                goButton.setPrefWidth(188.0);
                goButton.setFont(javafx.scene.text.Font.font("Luminari", 11));
                goButton.setEffect(new javafx.scene.effect.ColorAdjust(0.17, 0.3, 0.0, 0.0));
                goButton.setOnAction((ActionEvent event) ->{
                    showOthersPersonalBoard(getPlayerIndex(players_list, player.getNickname()));});
                GridPane.setRowIndex(goButton, j + 1);
                GridPane.setColumnIndex(goButton, 2);
                otherPlayers.getChildren().add(goButton);

                // Add row constraints
                otherPlayers.getRowConstraints().add(new RowConstraints(30.0, 30.0, Double.MAX_VALUE));
                j++;
            }else{
                this.my_index = j;
            }
        }
        players_without_me_size = players_without_me.size();
    }

    /**
     * Method that binds the click on the GO button with the command to send to the model
     * */
    public void showOthersPersonalBoard(int index) {
        if (reader != null) {
            switch (index) {
                case 0:
                    reader.add("/pb0");
                    break;
                case 1:
                    reader.add("/pb1");
                    break;
                case 2:
                    reader.add("/pb2");
                    break;
                case 3:
                    reader.add("/pb3");
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("inputReaderGUI object is null");
        }
    }

    /**
     * Method that shows the personal board of the given player
     * */
    public void setOthersPersonalBoard(int player_index) {
        Player player = players_list.get(player_index);
        int playerIndex = getPlayerIndex(players_without_me, player.getNickname());
        personalBoardAnchorPane.setVisible(false);
        my_sp.setVisible(false);

        pb0.setVisible(playerIndex == 0);
        sp0.setVisible(playerIndex == 0);
        pb1.setVisible(playerIndex == 1);
        sp1.setVisible(playerIndex == 1);
        pb2.setVisible(playerIndex == 2);
        sp2.setVisible(playerIndex == 2);
    }

    /** Method that sets the points of the players in the grid pane
     * */
    public void setPoints(ModelView gameModel) {
        int j = 0;
        for(Player player : players_without_me){
            int playerIndex = getPlayerIndex(players_list, player.getNickname());
            removeNodeByRowColumnIndex(j + 1, 1, otherPlayers);
            Label pointsLabel = new Label(String.valueOf(gameModel.getCommonBoard().getPlayerPosition(playerIndex)));
            pointsLabel.setTextFill(Color.web("#4d0202"));
            pointsLabel.setAlignment(javafx.geometry.Pos.CENTER);
            pointsLabel.setFont(javafx.scene.text.Font.font("Luminari", 19));
            GridPane.setRowIndex(pointsLabel, j + 1);
            GridPane.setColumnIndex(pointsLabel, 1);
            otherPlayers.getChildren().add(pointsLabel);
            j++;
        }
        myPoints.setText(gameModel.getCommonBoard().getPlayerPosition(my_index) + "");
    }

    /** Method that helps to set the points of the players in the grid pane
     * */
    private void removeNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);

            if (rowIndex != null && rowIndex == row && colIndex != null && colIndex == column) {
                gridPane.getChildren().remove(node);
                break;
            }
        }
    }

    /**
     * Method that updates the personal boards of the other players
     * */
    public void setPersonalBoard(ModelView gameModel) {
        Player player = gameModel.getPlayersConnected().getFirst();
        int playerIndex = getPlayerIndex(gameModel.getAllPlayers(), player.getNickname());
        ArrayList<Integer> last_chosen_card = gameModel.getLastChosenCardAndPosition();
        Orientation last_chosen_orientation = gameModel.getLastChosenOrientation();

        placeOthersPlayersCard(gameModel.getAllPlayers(), playerIndex, last_chosen_card, last_chosen_orientation, gameModel.getLastCoordinate());
    }

    /**
     * Method that places the card of the other players in his personal board
     * */
    private void placeOthersPlayersCard(ArrayList<Player> all_players, int playerIndex, ArrayList<Integer> lastChosenCard, Orientation lastChosenOrientation, Coordinate coord) {
            Player player_without_me = players_list.get(playerIndex);
            int player_without_me_index = getPlayerIndex(players_without_me, player_without_me.getNickname());

            if (rounds < players_list.size()) {
                String formattedCardId = String.format("%03d", all_players.get(playerIndex).getStarterCard().getId());
                Orientation orientation = all_players.get(playerIndex).getStarterCard().getOrientation();
                Image starterImage = preloadedImages.get(formattedCardId + orientation);

                if (starterImage == null) {
                    System.out.println("Orientation non valida.");
                    return;
                }

                ImageView StarterCardPic = new ImageView(starterImage);
                StarterCardPic.setFitWidth(90);
                StarterCardPic.setFitHeight(65);

                String resultString = String.format("%d,%d", 0, 0);
                int[] position = inverseMapper.getInverseMappedPosition(resultString);

                StarterCardPic.setLayoutX(position[0] + offset_x);
                StarterCardPic.setLayoutY(position[1] + offset_y);

                switch (player_without_me_index) {
                    case 0 -> pb0.getChildren().add(StarterCardPic);
                    case 1 -> pb1.getChildren().add(StarterCardPic);
                    case 2 -> pb2.getChildren().add(StarterCardPic);
                }

                // Get the piece image
                Image pieceImage = preloadedImages.get("piece" + playerIndex);
                ImageView piecePic = new ImageView(pieceImage);
                piecePic.setFitWidth(20);
                piecePic.setFitHeight(20);
                piecePic.setLayoutX(position[0] + 20 + offset_x);
                piecePic.setLayoutY(position[1] + 22.5 + offset_y);

                switch (player_without_me_index) {
                    case 0 -> pb0.getChildren().add(piecePic);
                    case 1 -> pb1.getChildren().add(piecePic);
                    case 2 -> pb2.getChildren().add(piecePic);
                }

                if (playerIndex == firstPlayerIndex) {
                    Image blackPieceImage = preloadedImages.get("pieceBlack");
                    ImageView BlackPiecePic = new ImageView(blackPieceImage);
                    BlackPiecePic.setFitWidth(20);
                    BlackPiecePic.setFitHeight(20);
                    BlackPiecePic.setLayoutX(position[0] + 50 + offset_x);
                    BlackPiecePic.setLayoutY(position[1] + 22.5 + offset_y);

                    switch (player_without_me_index) {
                        case 0 -> pb0.getChildren().add(BlackPiecePic);
                        case 1 -> pb1.getChildren().add(BlackPiecePic);
                        case 2 -> pb2.getChildren().add(BlackPiecePic);
                    }
                }
                rounds++;
            }

            String formattedCardId = String.format("%03d", lastChosenCard.get(0));
            Image lastChosenImage = preloadedImages.get(formattedCardId + lastChosenOrientation);

            if (lastChosenImage == null) {
                System.out.println("Orientation non valida.");
                return;
            }

            ImageView CardPic = new ImageView(lastChosenImage);
            CardPic.setFitWidth(90);
            CardPic.setFitHeight(65);

            int[] result = {lastChosenCard.get(1) - 25, lastChosenCard.get(2) - 25};

            switch (coord) {
                case NE -> result[0]--;
                case NW -> {
                    result[0]--;
                    result[1]--;
                }
                case SW -> result[1]--;
            }

        String resultString = String.format("%d,%d", result[0], result[1]);

        int[] position = inverseMapper.getInverseMappedPosition(resultString);

        CardPic.setLayoutX((double)position[0] + offset_x);
        CardPic.setLayoutY((double)position[1] + offset_y);

        double newWidth = position[0] + CardPic.getFitWidth();
        double newHeight = position[1]  + CardPic.getFitHeight();

        switch (player_without_me_index){
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
    }

    /**
      Method that hides all the personal boards that are showed, and make your personal board visible
     */
    @FXML
    private void hideAllPersonalBoards() {
        pb0.setVisible(false);
        sp0.setVisible(false);
        pb1.setVisible(false);
        sp1.setVisible(false);
        pb2.setVisible(false);
        sp2.setVisible(false);
        personalBoardAnchorPane.setVisible(true);
        my_sp.setVisible(true);
    }

    //-------------------------------------------------COMMON CARDS--------------------------------------------

    /**
     * Method that sets the grid pane of common cards
     * */
    public void setCommonCards(ModelView model) {
        Integer[] cardIds = model.getCommonBoard().getCommonCardsId();
        for (int i = 0; i < cardIds.length; i++) {
            int cardId = cardIds[i];
            String formattedCardId = cardId != -1 ? String.format("%03d", cardId) : "placeholder";
            String key = (cardId != -1 ? formattedCardId + (i == 0 || i == 3 || i == 6 ? Orientation.BACK : Orientation.FRONT) : "placeholder");
            Image image = preloadedImages.get(key);

            if (image == null) {
                System.out.println("Image not found: " + key);
                continue;
            }

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(90);
            imageView.setFitHeight(65);

            switch (i) {
                case 0 -> {
                    commonCard1.getChildren().add(imageView);
                    if (cardId == -1) cc1_ended = true;
                }
                case 1 -> {
                    commonCard2.getChildren().add(imageView);
                    if (cardId == -1) cc2_ended = true;
                }
                case 2 -> {
                    commonCard3.getChildren().add(imageView);
                    if (cardId == -1) cc3_ended = true;
                }
                case 3 -> {
                    commonCard4.getChildren().add(imageView);
                    if (cardId == -1) cc4_ended = true;
                }
                case 4 -> {
                    commonCard5.getChildren().add(imageView);
                    if (cardId == -1) cc5_ended = true;
                }
                case 5 -> {
                    commonCard6.getChildren().add(imageView);
                    if (cardId == -1) cc6_ended = true;
                }
                case 6 -> {
                    commonCard7.getChildren().add(imageView);
                    if (cardId == -1) cc7_ended = true;
                }
                case 7 -> {
                    commonCard8.getChildren().add(imageView);
                    if (cardId == -1) cc8_ended = true;
                }
                case 8 -> {
                    commonCard9.getChildren().add(imageView);
                    if (cardId == -1) cc9_ended = true;
                }
            }
        }
    }

    /**
     * Method that ables the click on the common cards
     * */
    public void ableCommonCardsClick() {
        commonCardsVbox.setDisable(false);
        setMsgToShow("Choose a common card", true);
    }

    /**
     * Method that binds the click on the common cards with the command to send to the model
     * The user has chosen the resource deck
     * */
    @FXML
    public void commonCard1Clicked() {
        if(!cc1_ended){
            if (reader != null) {
                reader.add("1");
            } else {
                System.out.println("inputReaderGUI object is null");
            }
            commonCardsVbox.setDisable(true);
            commonIndex = 1;
        }
    }

    /**
     * Method that binds the click on the common cards with the command to send to the model
     * The user has chosen the resource card in the first position
     * */
    @FXML
    public void commonCard2Clicked() {
        if(!cc2_ended){
            if (reader != null) {
                reader.add("2");
            } else {
                System.out.println("inputReaderGUI object is null");
            }
            commonCardsVbox.setDisable(true);
            commonIndex = 2;
        }
    }

    /**
     * Method that binds the click on the common cards with the command to send to the model
     * The user has chosen the resource card in the second position
     * */
    @FXML
    public void commonCard3Clicked() {
        if(!cc3_ended){
            if (reader != null) {
                reader.add("3");
            } else {
                System.out.println("inputReaderGUI object is null");
            }
            commonCardsVbox.setDisable(true);
            commonIndex = 3;
        }
    }

    /**
     * Method that binds the click on the common cards with the command to send to the model
     * The user has chosen the gold deck
     * */
    @FXML
    public void commonCard4Clicked() {
        if(!cc4_ended){
            if (reader != null) {
                reader.add("4");
            } else {
                System.out.println("inputReaderGUI object is null");
            }
            commonCardsVbox.setDisable(true);
            commonIndex = 4;
        }
    }

    /**
     * Method that binds the click on the common cards with the command to send to the model
     * The user has chosen the gold card in the first position
     * */
    @FXML
    public void commonCard5Clicked() {
        if(!cc5_ended){
            if (reader != null) {
                reader.add("5");
            } else {
                System.out.println("inputReaderGUI object is null");
            }
            commonCardsVbox.setDisable(true);
            commonIndex = 5;
        }
    }

    /**
     * Method that binds the click on the common cards with the command to send to the model
     * The user has chosen the gold card in the second position
     * */
    @FXML
    public void commonCard6Clicked() {
        if(!cc6_ended){
            if (reader != null) {
                reader.add("6");
            } else {
                System.out.println("inputReaderGUI object is null");
            }
            commonCardsVbox.setDisable(true);
            commonIndex = 6;
        }
    }

    //-----------------------------------PERSONAL OBJECTIVES--------------------------------------------

    /**
     * Method that sets the personal objective cards of the player
     * */
    public void setPersonalObjectives(ModelView model, String nickname) {
        Integer[] cardIds = model.getPlayerEntity(nickname).getSecretObjectiveCardsIds();
        this.personalObjectiveIds = new ArrayList<>(Arrays.asList(cardIds));
        for (int i = 0; i < cardIds.length; i++) {
            int cardId = cardIds[i];
            String formattedCardId = String.format("%03d", cardId);
            String key = formattedCardId + Orientation.FRONT;
            Image image = preloadedImages.get(key);

            if (image == null) {
                System.out.println("Image not found: " + key);
                continue;
            }

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(90);
            imageView.setFitHeight(65);

            if (i == 0) {
                personalObjective0Pane.getChildren().add(imageView);
            } else {
                personalObjective1Pane.getChildren().add(imageView);
            }
        }
    }

    /**
     * Method that flips the personal objective that has not been chosen from the user
     * */
    public void flipPersonalObjective(int cardId, int i) {
        String formattedCardId = String.format("%03d", cardId);
        String key = formattedCardId + Orientation.BACK;
        Image image = preloadedImages.get(key);

        if (image == null) {
            System.out.println("Image not found: " + key);
            return;
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(90);
        imageView.setFitHeight(65);
        if (i == 0) {
            personalObjective0Pane.getChildren().add(imageView);
        } else {
            personalObjective1Pane.getChildren().add(imageView);
        }
        personalObjective1Pane.setDisable(true);
        personalObjective0Pane.setDisable(true);
    }


    /**
     * Method that ables the click on the personal objective cards
     * */
    public void ableObjectiveCardsClick() {
        personalObjective0Pane.setDisable(false);
        personalObjective1Pane.setDisable(false);
        personalObjective0Pane.setMouseTransparent(false);
        personalObjective1Pane.setMouseTransparent(false);
        setMsgToShow("Choose one objective card: " , true);
    }

    /**
     * Method that binds the click on the first personal objective card with the command to send to the model
     * The user has chosen the first personal objective card
     * */
    @FXML
    public void onPersonalObjective1Clicked() {
        flipPersonalObjective(personalObjectiveIds.getFirst(), 0);
        if (reader != null) {
            reader.add("2");
        } else {
            System.out.println("inputReaderGUI object is null");
        }
    }

    /**
     * Method that binds the click on the second personal objective card with the command to send to the model
     * The user has chosen the second personal objective card
     * */
    @FXML
    public void onPersonalObjective0Clicked() {
        flipPersonalObjective(personalObjectiveIds.get(1), 1);
        if (reader != null) {
            reader.add("1");
        } else {
            System.out.println("inputReaderGUI object is null");
        }
    }

    //-----------------------------------STARTER CARD--------------------------------------------

    /**
     * Method that sets the starter card on the front (it comes from GUI)
     * */
    public void setStarterCardFront(ModelView model, String nickname) {
        this.starterCard = model.getPlayerEntity(nickname).getStarterCardToChose().getFirst().getId();
        starterCardFrontLoading();
    }

    private void starterCardFrontLoading() {
        String formattedCardId = String.format("%03d", this.starterCard);
        String key = formattedCardId + Orientation.BACK;
        Image image = preloadedImages.get(key);

        if (image == null) {
            System.out.println("Image not found: " + key);
            return;
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(90);
        imageView.setFitHeight(65);

        StarterCardImage.setImage(imageView.getImage());
        starterCardOrientation = Orientation.FRONT;
    }

    /**
     * Method that sets the starter card on the front (it comes from the FLIP button)
     * */
    public void setStarterCardFront() {
        starterCardFrontLoading();
    }

    /**
     * Method that sets the starter card on the back (it comes from the FLIP button)
     * */
    public void setStarterCardBack() {
        String formattedCardId = String.format("%03d", this.starterCard);
        String key = formattedCardId + Orientation.FRONT;
        Image image = preloadedImages.get(key);

        if (image == null) {
            System.out.println("Image not found: " + key);
            return;
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(90);
        imageView.setFitHeight(65);

        StarterCardImage.setImage(imageView.getImage());
        starterCardOrientation = Orientation.BACK;
    }

    /**
     * Method that flips the starter card (it is the method of the FLIP button)
     * */
    public void flipStarterCard() {
        if (starterCardOrientation == Orientation.BACK)
            setStarterCardFront();
        else
            setStarterCardBack();
    }

    /**
     * Method that ables the click on the starter card
     * */
    public void ableStarterCardClick() {
        starterCardPane.setDisable(false);
        FlipStarter.setDisable(false);
        setMsgToShow("Choose the orientation of your starter card: ", true);
    }

    /**
     * Method that binds the click on the starter card with the command to send to the model
     * */
    @FXML
    private void onStarterCardClicked() {
        starterCardPane.setDisable(true);
        FlipStarter.setDisable(true);
        FlipStarter.setVisible(false);
        StarterCardImage.setVisible(false);

        if (reader != null) {
            if(starterCardOrientation == Orientation.FRONT)
                reader.add("f");
            else if(starterCardOrientation == Orientation.BACK)
                reader.add("b");
            else
                System.out.println("Orientation non valida.");
        } else {
            System.out.println("inputReaderGUI object is null");
        }
        placeStarterCard();
    }

    //-----------------------------------CARD HAND--------------------------------------------

    /**
     * Method that sets the card hand of the player (on the front), it comes from GUI
     * */
    public void setCardHand(ModelView model, String nickname) {
        setCardHandFront(model.getPlayerEntity(nickname).getCardHandIds());
    }

    /**
     * Method that sets the card hand of the player (on the back), it comes from the FLIP button
     * */
    public void setCardHandFront(ArrayList<Integer> cardIds) {
        for (int i = 0; i < cardIds.size(); i++) {
            cardHand.set(i, cardIds.get(i));
            String formattedCardId = String.format("%03d", cardIds.get(i));
            String key = formattedCardId + Orientation.FRONT;
            Image image = preloadedImages.get(key);

            if (image == null) {
                System.out.println("Image not found: " + key);
                continue;
            }

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(90);
            imageView.setFitHeight(65);

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

    /**
     * Method that sets the card hand of the player (on the back), it comes from the FLIP button
     * */
    public void setCardHandBack(ArrayList<Integer> cardIds) {
        for (int i = 0; i < cardIds.size(); i++) {
            cardHand.set(i, cardIds.get(i));
            String formattedCardId = String.format("%03d", cardIds.get(i));
            String key = formattedCardId + Orientation.BACK;
            Image image = preloadedImages.get(key);

            if (image == null) {
                System.out.println("Image not found: " + key);
                continue;
            }

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(90);
            imageView.setFitHeight(65);

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

    /**
     * Method that flips the card hand (it is the method of the FLIP button)
     * */
    public void flipCardHand() {
        if (cardHandOrientation == Orientation.BACK)
            setCardHandFront(cardHand);
        else
            setCardHandBack(cardHand);
    }

    /**
     * Method that binds the click on first card in the card hand with the command to send to the model
     * The user has chosen the first card in the hand
     * */
    public void onCardHand0Clicked() {
        cardHandVBox.setDisable(true);
        handCard1.setDisable(true);
        handCard2.setDisable(true);
        handCard0.setDisable(true);

        FlipHand.setDisable(true);

        if (reader != null) {
            reader.add("1");
        } else {
            System.out.println("inputReaderGUI object is null");
        }

        if (reader != null) {
            if(cardHandOrientation == Orientation.FRONT)
                reader.add("f");
            else if(cardHandOrientation == Orientation.BACK)
                reader.add("b");
            else
                System.out.println("Orientation non valida.");
        } else {
            System.out.println("inputReaderGUI object is null");
        }
        placeCard(0);
        FlipHand.setDisable(true);
    }

    /**
     * Method that binds the click on second card in the card hand with the command to send to the model
     * The user has chosen the second card in the hand
     * */
    @FXML
    public void onCardHand1Clicked() {
        cardHandVBox.setDisable(true);

        handCard1.setDisable(true);
        handCard2.setDisable(true);
        handCard0.setDisable(true);

        FlipHand.setDisable(true);

        if (reader != null) {
            reader.add("2");
        } else {
            System.out.println("inputReaderGUI object is null");
        }

        if (reader != null) {
            if(cardHandOrientation == Orientation.FRONT)
                reader.add("f");
            else if(cardHandOrientation == Orientation.BACK)
                reader.add("b");
            else
                System.out.println("Orientation non valida.");
        } else {
            System.out.println("inputReaderGUI object is null");
        }
        placeCard(1);
        FlipHand.setDisable(true);
    }

    /**
     * Method that binds the click on third card in the card hand with the command to send to the model
     * The user has chosen the third card in the hand
     * */
    @FXML
    public void onCardHand2Clicked() {
        cardHandVBox.setDisable(true);
        handCard1.setDisable(true);
        handCard2.setDisable(true);
        handCard0.setDisable(true);

        FlipHand.setDisable(true);

        if (reader != null) {
            reader.add("3");
        } else {
            System.out.println("inputReaderGUI object is null");
        }

        if (reader != null) {
            if(cardHandOrientation == Orientation.FRONT)
                reader.add("f");
            else if(cardHandOrientation == Orientation.BACK)
                reader.add("b");
            else
                System.out.println("Orientation non valida.");
        } else {
            System.out.println("inputReaderGUI object is null");
        }
        placeCard(2);
        FlipHand.setDisable(true);
    }

    /**
     * Method that removes from the hand the card played
     * */
    private void removeFromHand(int index) {
        cardHandVBox.setDisable(true);
        handCard2.setDisable(true);
        handCard1.setDisable(true);
        handCard0.setDisable(true);
        FlipHand.setDisable(true);

        if (index == 0) {
            handCard0.getChildren().clear();
        } else if (index == 1) {
            handCard1.getChildren().clear();
        } else {
            handCard2.getChildren().clear();
        }
    }

    /**
     * Method that ables the click on the card hand
     * */
    public void whichCardToPlace() {
        cardHandVBox.setDisable(false);
        cardHandVBox.setMouseTransparent(false);
        handCard0.setDisable(false);
        handCard1.setDisable(false);
        handCard2.setDisable(false);
        FlipHand.setDisable(false);
        personalBoardAnchorPane.setDisable(false);
        setMsgToShow("Choose a card to place from your hand: ", true);
        cardHandVBox.setDisable(false);
        cardHandVBox.setMouseTransparent(false);
        handCard0.setDisable(false);
        handCard1.setDisable(false);
        handCard2.setDisable(false);
        FlipHand.setDisable(false);
        personalBoardAnchorPane.setDisable(false);
    }

    /**
     * Method that binds the click on the chosen card with the orientation to send to the model
     * */
    public void whichOrientationToPlace() {
        if (reader != null) {
            if(cardHandOrientation == Orientation.FRONT)
                reader.add("f");
            else if(cardHandOrientation == Orientation.BACK)
                reader.add("b");
            else
                System.out.println("Orientation non valida.");
        } else {
            System.out.println("inputReaderGUI object is null");
        }
    }
    //-----------------------------------PLACE CARD ON MY PERSONAL BOARD--------------------------------------------

    String glowBorder = "-fx-effect: dropshadow(three-pass-box, blue, 10, 0, 0, 0);";

    /**
     * Method that places the card on my personal board
     * */
    private void placeCard(int index) {
        cardIndex = index;
        if(index ==  0){
            handCard0.setStyle(glowBorder);
            chosenCard = cardHand.getFirst();
        } else if (index ==  1) {
            handCard1.setStyle(glowBorder);
            chosenCard = cardHand.get(1);
        } else {
            handCard2.setStyle(glowBorder);
            chosenCard = cardHand.get(2);
        }
        handCard0.setDisable(true);
        handCard1.setDisable(true);
        handCard2.setDisable(true);
        setMsgToShow("Select where to put the chosen card", true);
        personalBoardAnchorPane.setDisable(false);
    }

    /**
     * Method that places the starter card on my personal board
     * */
    private void placeStarterCard() {
        String resultString = String.format("%d,%d", 0, 0);
        int[] position = inverseMapper.getInverseMappedPosition(resultString);

        double xCenter = (double)(position[0]+ offset_x);
        double yCenter = (double)(position[1]+ offset_y);

        String formattedCardId = String.format("%03d", starterCard);

        String key = formattedCardId + (starterCardOrientation == Orientation.FRONT ? Orientation.BACK : Orientation.FRONT);
        Image image = preloadedImages.get(key);

        if (image == null) {
            System.out.println("Image not found: " + key);
            return;
        }

        ImageView StarterCardPic = new ImageView(image);
        StarterCardPic.setFitWidth(90);
        StarterCardPic.setFitHeight(65);
        StarterCardPic.setLayoutX(xCenter);
        StarterCardPic.setLayoutY(yCenter);
        personalBoardAnchorPane.getChildren().add(StarterCardPic);

        // Retrieve the piece image from the preloadedImages map
        Image pieceImage = preloadedImages.get("piece" + my_index);
        ImageView piecePic = new ImageView(pieceImage);
        piecePic.setFitWidth(20);
        piecePic.setFitHeight(20);
        piecePic.setLayoutX(xCenter + 20);
        piecePic.setLayoutY(yCenter + 22.5);
        personalBoardAnchorPane.getChildren().add(piecePic);

        // Check if the current player is the first player
        if (my_index == firstPlayerIndex) {
            // Retrieve the black piece image from the preloadedImages map
            Image blackPieceImage = preloadedImages.get("pieceBlack");
            ImageView pieceBlackPic = new ImageView(blackPieceImage);
            pieceBlackPic.setFitWidth(20);
            pieceBlackPic.setFitHeight(20);
            pieceBlackPic.setLayoutX(xCenter + 50);
            pieceBlackPic.setLayoutY(yCenter + 22.5);
            personalBoardAnchorPane.getChildren().add(pieceBlackPic);
        }
        setMsgToShow("StarterCard placed", true);
    }

    /**
     * Method that handles the click on the scroll pane (personal board)
     * */
    @FXML
    private void handleMouseClick(MouseEvent event) {
        personalBoardAnchorPane.setDisable(true);

        double clickX = event.getX() - offset_x;
        double clickY = event.getY() - offset_y;
        chosenX = clickX;
        chosenY = clickY;

        int[] result = mapper.getMappedPosition(clickX, clickY);

        if (result != null) {
            if (reader != null) {
                reader.add(String.valueOf(result[0]));
                reader.add(String.valueOf(result[1]));
            } else {
                System.out.println("inputReaderGUI object is null");
            }
        } else {
            System.out.println("posizione non valida");
        }
    }

    /**
     * Method that places the card on my personal board
     * */
    private void reallyPlaceCard(double clickX, double clickY, Coordinate coord) {
        String formattedCardId = String.format("%03d", chosenCard);

        String key = formattedCardId + cardHandOrientation;
        Image image = preloadedImages.get(key);

        if (image == null) {
            System.out.println("Image not found: " + key);
            return;
        }

        ImageView CardPic = new ImageView(image);
        CardPic.setFitWidth(90);
        CardPic.setFitHeight(65);

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
        String resultString = String.format("%d,%d", result[0], result[1]);
        int[] position = inverseMapper.getInverseMappedPosition(resultString);
        CardPic.setLayoutX((double)position[0] + 2*offset_x );
        CardPic.setLayoutY((double)position[1] + 2*offset_y);

        double newWidth = position[0] + CardPic.getFitWidth() + 2*offset_x;
        double newHeight = position[1] + CardPic.getFitHeight() + 2*offset_y;

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


    /** Method that shows the successful attempt to place the card (it comes from the model), and places the card
     * */
    public void successfulMove(Coordinate coord) {
        reallyPlaceCard(mapper.getMinCorner(chosenX,chosenY)[0] - offset_x, (mapper.getMinCorner(chosenX,chosenY)[1]) - offset_y, coord);
        setMsgToShow("Valid position!", true);
        personalBoardAnchorPane.setDisable(true);
    }

    //-----------------------------------FEEDBACKS--------------------------------------------

    /** Method that shows the illegal attempt to place the card
     * */
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

    /** Method that disable all cards on the hand after an illegal move, the only move allowed is to flip the card
     * */
    public void disableAllOtherCardsInHand() {
        cardHandVBox.setDisable(true);
        handCard0.setDisable(true);
        handCard1.setDisable(true);
        handCard2.setDisable(true);
        FlipHand.setDisable(false);
        personalBoardAnchorPane.setDisable(false);
    }


    /**Methods that show messages on the screen
     * */
    private final Map<Boolean, Color> colorMap = Map.of(
            true, Color.GREEN,
            false, Color.RED
    );

    private final Function<Boolean, Color> getColor = success -> success == null
            ? Color.WHITE : colorMap.get(success);

    public void setMsgToShow(String msg, Boolean success) {
        labelMessage.setText(msg);
        labelMessage.setTextFill(getColor.apply(success));
    }

    /**
     * Method that shows who is the first to play
     * */
    public void changeTurn(ModelView model) {
        setMsgToShow("Next turn is up to: " + model.getCurrentPlayerNickname(), true);
    }

    /**
     * Method that shows the end of your turn
     * */
    public void myTurnIsFinished() {
        setMsgToShow("Your turn is finished. Wait until it is again your turn! ", true);
    }

    //-------------------------------------CHAT------------------------------------

    /**Method that binds the chat on the GUI with the command to send to the model
     * */
    @FXML
    private void ActionSendMessage() {
        String message = chatInput.getText();
        String recipient = recipientComboBox.getValue();
        if (!message.trim().isEmpty()) {
            if ("Everyone".equals(recipient)) {
                reader.add("/c " + chatInput.getText());
                chatArea.appendText("Me (to everyone): " + message + "\n");
            } else {
                chatArea.appendText("Me (to " + recipient + "): " + message + "\n");
                reader.add("/cs " + recipientComboBox.getValue().toString() + " " + chatInput.getText());
            }
            chatInput.clear();
        }
    }

    /**
     * Method that updates the chat, showing the last message that comes from the model
     * */
    public void updateChat(ModelView model, String nickname) {
        Message message = model.getChat().getLastMessage();
        if(nickname.equals("Everyone")){
            chatArea.appendText(message.sender().getNickname() + " (to everyone): " + message.text() + "\n");
        }else{
            chatArea.appendText(message.sender().getNickname() + " (to you): " + message.text() + "\n");
        }

    }

//--------------------------------------EXIT--------------------------------------------

    /** EXIT button that permits to exit the game
     * */
    public void exitGame() {
        if (reader != null) {
            reader.add("/quit");
            reader.add("/leave");
        } else {
            System.out.println("inputReaderGUI object is null");
        }
    }
}