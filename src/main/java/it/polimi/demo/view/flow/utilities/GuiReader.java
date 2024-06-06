package it.polimi.demo.view.flow.utilities;

import java.util.concurrent.LinkedBlockingQueue;

public class GuiReader implements AbstractReader {

    private final LinkedBlockingQueue<String> buffer;

    public GuiReader(){
        buffer = new LinkedBlockingQueue<>();
    }

    public LinkedBlockingQueue<String> getBuffer(){
        return buffer;
    }

    public synchronized void addTxt(String txt){
        buffer.add(txt);
    }

}
