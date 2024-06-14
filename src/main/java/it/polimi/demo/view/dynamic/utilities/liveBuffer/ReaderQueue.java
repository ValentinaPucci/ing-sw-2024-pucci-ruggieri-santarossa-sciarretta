package it.polimi.demo.view.dynamic.utilities.liveBuffer;

import java.util.concurrent.LinkedBlockingQueue;

public class ReaderQueue {
    private final LinkedBlockingQueue<String> buffer = new LinkedBlockingQueue<>();
    public LinkedBlockingQueue<String> getBuffer() { return buffer; }
}
