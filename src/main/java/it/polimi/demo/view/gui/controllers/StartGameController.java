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

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

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
    @FXML public Text gameidLabel;
    @FXML private Button buttonReady;
    @FXML private Text nicknameLabel;



    @FXML
    public void CreateGame() {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("c");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void JoinGame() {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("js");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void RandomGame() {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("j");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    void insertGameId() {
        if(!GameId.getText().isEmpty()){
            getInputReaderGUI().add(GameId.getText());
        }
    }

    @FXML
    void insertNickname() {
        if(!myNickname.getText().isEmpty()) {
            getInputReaderGUI().add(myNickname.getText());
        }
    }

    @FXML void playerIsReady() {
        getInputReaderGUI().add("y");
    }

    public void setGameId(int id) {
        gameidLabel.setText("GameID: "+id);
    }

    public void setMyNicknameLabel(String nickname){
        nicknameLabel.setText(nickname);
    }

    public void setReadyButton(boolean visible){
        buttonReady.setVisible(visible);
    }

    @FXML
    public void twoPLayers() {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("2");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    @FXML
    public void threePLayers() {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("3");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }
    @FXML
    public void fourPLayers() {
        LinkedBlockingQueue<String> reader = getInputReaderGUI();
        if (reader != null) {
            reader.add("4");
        } else {
            System.out.println("L'oggetto inputReaderGUI è null.");
        }
    }

    private void showWaitingRoomSpecific(String nickname, int playerIndex) {
        switch (playerIndex) {
            case 0 -> {
                pane0.setVisible(true);
                ImageView pane0ImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/blueOwl.png")))); //blue
                pane0ImageView.setFitWidth(286);
                pane0ImageView.setFitHeight(418);
                nicknameLabel1.setText(nickname);
                pane0.getChildren().add(pane0ImageView);

            }
            case 1 -> {
                pane1.setVisible(true);
                ImageView pane1ImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/green.png"))));
                pane1ImageView.setFitWidth(286);
                pane1ImageView.setFitHeight(418);
                nicknameLabel2.setText(nickname);
                pane1.getChildren().add(pane1ImageView);

            }
            case 2 -> {
                pane2.setVisible(true);
                ImageView pane2ImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/red.png"))));
                pane2ImageView.setFitWidth(286);
                pane2ImageView.setFitHeight(418);
                nicknameLabel3.setText(nickname);
                pane2.getChildren().add(pane2ImageView);
            }
            case 3 -> {
                pane3.setVisible(true);
                ImageView pane3ImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/yellow.png"))));
                pane3ImageView.setFitWidth(286);
                pane3ImageView.setFitHeight(418);
                nicknameLabel4.setText(nickname);
                pane3.getChildren().add(pane3ImageView);
            }
            default -> throw new IllegalArgumentException("Invalid player index: " + playerIndex);
        }
    }

    public void showPlayerToWaitingRoom(ModelView model) {
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
}
