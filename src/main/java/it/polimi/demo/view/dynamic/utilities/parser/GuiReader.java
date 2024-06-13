package it.polimi.demo.view.dynamic.utilities.parser;

import java.util.concurrent.LinkedBlockingQueue;

public class GuiReader implements AbstractReader {

    private final LinkedBlockingQueue<String> buffer = new LinkedBlockingQueue<>();

    public LinkedBlockingQueue<String> getBuffer(){
        return buffer;
    }
}
