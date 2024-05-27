package it.polimi.demo.view.gui.controllers;
import it.polimi.demo.view.flow.utilities.inputReaderGUI;

public abstract class GenericController {

    private inputReaderGUI inputReaderGUI;

    /**
     * Method to get the input reader GUI.
     * @return the input reader GUI
     */
    public inputReaderGUI getInputReaderGUI() {
        return inputReaderGUI;
    }

    /**
     * Method to set the input reader GUI.
     * @param inputReaderGUI the input reader GUI
     */
    public void setInputReaderGUI(it.polimi.demo.view.flow.utilities.inputReaderGUI inputReaderGUI) {
        this.inputReaderGUI = inputReaderGUI;
    }

}
