package it.polimi.demo.view.gui;

import it.polimi.demo.view.flow.ConnectionSelection;
import it.polimi.demo.view.flow.GameFlow;
import it.polimi.demo.view.gui.controllers.GenericController;
import it.polimi.demo.view.gui.scene.SceneType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ApplicationGUI extends Application {
    private GameFlow gameFlow;
    private Stage primaryStage, popUpStage;
    private StackPane root;
    private ArrayList<SceneInfo> scenes;
    @Override
    public void start(Stage stage) throws Exception {
        gameFlow = new GameFlow(this, ConnectionSelection.valueOf(getParameters().getUnnamed().get(0)));
        loadScenes();

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Codex Naturalis");

        root = new StackPane();
    }

    /**
     * This method use the FXMLLoader to load the scene and the controller of the scene.
     */
    private void loadScenes() {
        // Carica tutte le scene disponibili per essere mostrate durante il gioco

        // Inizializza una lista vuota per contenere le informazioni sulle scene
        scenes = new ArrayList<>();

        // Dichiarazione delle variabili locali per il caricamento delle scene
        FXMLLoader loader;
        Parent root;
        GenericController gc;

        // Ciclo attraverso tutti i valori dell'enum SceneType
        for (int i = 0; i < SceneType.values().length; i++) {
            // Crea un FXMLLoader per ogni scena usando il percorso specificato dal valore dell'enum
            loader = new FXMLLoader(getClass().getResource(SceneType.values()[i].value()));

            try {
                // Carica la radice della scena (il layout) dal file FXML
                root = loader.load();

                // Ottiene il controller associato alla scena caricata
                gc = loader.getController();
            } catch (IOException e) {
                // In caso di errore durante il caricamento del file FXML, lancia una RuntimeException
                throw new RuntimeException(e);
            }

            // Aggiunge le informazioni sulla scena appena caricata alla lista delle scene
            scenes.add(new SceneInfo(new Scene(root), SceneType.values()[i], gc));
        }
    }

}
