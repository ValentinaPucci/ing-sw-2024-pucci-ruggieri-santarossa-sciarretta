package it.polimi.demo.view.gui.controllers;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class GuiInputReaderController {
    protected LinkedBlockingQueue<String> reader;
    public void setBuffer(LinkedBlockingQueue<String> GuiReader) {
        this.reader = GuiReader;
    }
}
