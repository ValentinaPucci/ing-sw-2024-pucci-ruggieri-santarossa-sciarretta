package it.polimi.demo.view.gui.controllers;

import it.polimi.demo.view.dynamic.utilities.liveBuffer.ReaderQueue;

public abstract class GenericController {

    private ReaderQueue GuiReader;

    /**
     * Method to get the input reader GUI.
     * @return the input reader GUI
     */
    public ReaderQueue getInputReaderGUI() {
        return GuiReader;
    }

    /**
     * Method to set the input reader GUI.
     * @param GuiReader the input reader GUI
     */
    public void setInputReaderGUI(ReaderQueue GuiReader) {
        //System.out.println("setInputReaderGUIGeneric: "+ inputReaderGUI);
        this.GuiReader = GuiReader;
    }

}
