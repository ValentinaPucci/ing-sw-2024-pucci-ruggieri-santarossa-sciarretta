package it.polimi.demo.view.gui.controllers;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class GuiInputReaderController {
    private LinkedBlockingQueue<String> GuiReader;
    public LinkedBlockingQueue<String> getInputReaderGUI() {
        return GuiReader;
    }
    public void setInputReaderGUI(LinkedBlockingQueue<String> GuiReader) {
        this.GuiReader = GuiReader;
    }
}
