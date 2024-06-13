package it.polimi.demo.view.dynamic.utilities.parser;

import java.util.concurrent.LinkedBlockingQueue;

public interface AbstractReader {
    LinkedBlockingQueue<String> getBuffer();
}
