package it.polimi.demo.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class numPlayersController extends GenericController {

    @FXML
    private Button numPlayers2;

    @FXML
    private Button numPlayers3;

    @FXML
    private Button numPlayers4;

    @FXML
    void handlePlayerSelection(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        String buttonText = sourceButton.getText();

        int numberOfPlayers = 0;

        switch (buttonText) {
            case "2 Giocatori":
                numberOfPlayers = 2;
                break;
            case "3 Giocatori":
                numberOfPlayers = 3;
                break;
            case "4 Giocatori":
                numberOfPlayers = 4;
                break;

        }

    }

    }


