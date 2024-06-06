package it.polimi.demo.view.flow.utilities;

import java.util.concurrent.LinkedBlockingQueue;

public interface AbstractReader {
    LinkedBlockingQueue<String> getBuffer();
}
