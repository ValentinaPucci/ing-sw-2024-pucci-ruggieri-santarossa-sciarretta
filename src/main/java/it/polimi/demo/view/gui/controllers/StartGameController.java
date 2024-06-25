package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Class that manages all the actions that happen before the main running scene
 * */
public class StartGameController extends GuiInputReaderController{

    @FXML public Pane pane0;
    @FXML public Pane pane1;
    @FXML public Pane pane2;
    @FXML public Pane pane3;
    @FXML public Text nicknameLabel1;
    @FXML public Text nicknameLabel2;
    @FXML public Text nicknameLabel3;
    @FXML public Text nicknameLabel4;
    @FXML private TextField myNickname;
    @FXML private TextField GameId;
    @FXML public Text gameID;
    @FXML private Button buttonReady;
    @FXML private Text myNick;

    private Map<String, Image> preloadedImages = new HashMap<>();


    //------------------------------------MENU SCENE-----------------------------------------------------------
    /** Method that binds the create game button of the menu to the action of creating a new game.
     * */
    @FXML
    public void CreateGame() {
        if (reader != null) {
            reader.add("c");
        } else {
            System.out.println("inputReaderGUI object is null");
        }
    }

    /** Method that binds the join specific game button of the menu to the action of joining a specific game.
     * */
    @FXML
    public void JoinGame() {
        if (reader != null) {
            reader.add("js");
        } else {
            System.out.println("inputReaderGUI object is null");
        }
    }

    /** Method that binds the join random game button of the menu to the action of joining a random game.
     * */
    @FXML
    public void RandomGame() {
        if (reader != null) {
            reader.add("j");
        } else {
            System.out.println("inputReaderGUI object is null");
        }
    }

    //------------------------------------INSERT GAME ID SCENE----------------------------------------------------------
    /** Method that binds the insert game id button of the insert game id scene to the action of inserting the game id.
     * */
    @FXML
    void insertGameId() {
        if(!GameId.getText().isEmpty()){
            reader.add(GameId.getText());
        }
    }

    //------------------------------------INSERT NICKNAME SCENE----------------------------------------------------------
    /** Method that binds the insert nickname button of the insert nickname scene to the action of inserting the nickname.
     * */
    @FXML
    void insertNickname() {
        if(!myNickname.getText().isEmpty()) {
            reader.add(myNickname.getText());
        }
    }

    //------------------------------------INSERT NUMBER OF PLAYERS----------------------------------------------------------
    /** Method that binds the two players button of the insert number of players scene to the action of selecting two players.
     * */
    @FXML
    public void twoPLayers() {
        if (reader != null) {
            reader.add("2");
        } else {
            System.out.println("inputReaderGUI object is null");
        }
    }

    /** Method that binds the three players button of the insert number of players scene to the action of selecting three players.
     * */
    @FXML
    public void threePLayers() {
        if (reader != null) {
            reader.add("3");
        } else {
            System.out.println("inputReaderGUI object is null");
        }
    }

    /** Method that binds the four players button of the insert number of players scene to the action of selecting four players.
     * */
    @FXML
    public void fourPLayers() {
        if (reader != null) {
            reader.add("4");
        } else {
            System.out.println("inputReaderGUI object is null");
        }
    }

    //------------------------------------WAITING ROOM SCENE----------------------------------------------------------


    /** Method that binds the ready button of the waiting room scene to the action of setting the player as ready.
     * */
    @FXML void onPress() {
        reader.add("y");
    }

    /** Method that inserts players in the waiting room scene
     * */
    public void showPlayerToWaitingRoom(ModelView model) {
        preloadImages();
        pane0.setVisible(false);
        pane1.setVisible(false);
        pane2.setVisible(false);
        pane3.setVisible(false);
        int i = 0;
        for (Player p : model.getPlayersConnected()) {
            showWaitingRoomSpecific(p.getNickname(), i);
            i++;
        }
    }
    /** Method that sets the game id in the waiting room scene
     * */
    public void setGameId(int id) {
        gameID.setText("GameID: "+id);
    }

    public void preloadImages() {
        String[] colors = {"blue", "green", "red", "yellow"};
        for (String color : colors) {
            String imagePath = "/" + color + ".png";
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            preloadedImages.put(color, image);
        }
    }

    /** Method that sets the players in waiting room scene
     * */
    private void showWaitingRoomSpecific(String nickname, int playerIndex) {
        switch (playerIndex) {
            case 0 -> {
                pane0.setVisible(true);
                ImageView pane0ImageView = new ImageView(preloadedImages.get("blue"));
                pane0ImageView.setFitWidth(286);
                pane0ImageView.setFitHeight(418);
                nicknameLabel1.setText(nickname);
                pane0.getChildren().add(pane0ImageView);
            }
            case 1 -> {
                pane1.setVisible(true);
                ImageView pane1ImageView = new ImageView(preloadedImages.get("green"));
                pane1ImageView.setFitWidth(286);
                pane1ImageView.setFitHeight(418);
                nicknameLabel2.setText(nickname);
                pane1.getChildren().add(pane1ImageView);
            }
            case 2 -> {
                pane2.setVisible(true);
                ImageView pane2ImageView = new ImageView(preloadedImages.get("red"));
                pane2ImageView.setFitWidth(286);
                pane2ImageView.setFitHeight(418);
                nicknameLabel3.setText(nickname);
                pane2.getChildren().add(pane2ImageView);
            }
            case 3 -> {
                pane3.setVisible(true);
                ImageView pane3ImageView = new ImageView(preloadedImages.get("yellow"));
                pane3ImageView.setFitWidth(286);
                pane3ImageView.setFitHeight(418);
                nicknameLabel4.setText(nickname);
                pane3.getChildren().add(pane3ImageView);
            }
            default -> throw new IllegalArgumentException("Invalid player index: " + playerIndex);
        }
    }


    /** Method that sets the nickname of the player in the waiting room scene
     * */
    public void setMyNickname(String nick){
        myNick.setText(nick);
    }


    /** Method that sets the ready button visible in the waiting room scene
     * */
    public void ReadyButtonVisibility(boolean visible){
        buttonReady.setVisible(visible);
    }

}
