package it.polimi.demo.view.gui.controllers;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class SceneController {

    private LinkedBlockingQueue<String> GuiReader;

    /**
     * Method to get the input reader_queue GUI.
     * @return the input reader_queue GUI
     */
    public LinkedBlockingQueue<String> getInputReaderGUI() {
        return GuiReader;
    }

    /**
     * Method to set the input reader_queue GUI.
     * @param GuiReader the input reader_queue GUI
     */
    public void setInputReaderGUI(LinkedBlockingQueue<String> GuiReader) {
        //System.out.println("setInputReaderGUIGeneric: "+ inputReaderGUI);
        this.GuiReader = GuiReader;
    }

}
