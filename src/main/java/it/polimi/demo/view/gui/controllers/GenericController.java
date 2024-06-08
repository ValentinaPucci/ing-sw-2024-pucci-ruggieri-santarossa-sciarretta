package it.polimi.demo.view.gui.controllers;
import it.polimi.demo.view.flow.utilities.GuiReader;

public abstract class GenericController {

    private GuiReader GuiReader;

    /**
     * Method to get the input reader GUI.
     * @return the input reader GUI
     */
    public GuiReader getInputReaderGUI() {
        return GuiReader;
    }

    /**
     * Method to set the input reader GUI.
     * @param GuiReader the input reader GUI
     */
    public void setInputReaderGUI(GuiReader GuiReader) {
        //System.out.println("setInputReaderGUIGeneric: "+ inputReaderGUI);
        this.GuiReader = GuiReader;
    }

}
