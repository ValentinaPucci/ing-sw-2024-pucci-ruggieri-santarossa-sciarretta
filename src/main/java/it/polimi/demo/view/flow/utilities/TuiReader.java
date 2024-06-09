package it.polimi.demo.view.flow.utilities;

import it.polimi.demo.Constants;

import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

import static it.polimi.demo.view.text.PrintAsync.printAsync;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * InputReaderTUI class
 * InputReaderTUI is the class that reads the input and add it to the buffer
 */
public class TuiReader extends Thread implements AbstractReader {
    private final LinkedBlockingQueue<String> buffer = new LinkedBlockingQueue<>();

    public TuiReader(){
        this.start();
    }

    @Override
    public void run(){
        Scanner scanner = new Scanner(System.in);
        while (!this.isInterrupted()) {
            String s = scanner.nextLine();
            buffer.add(s);
            printAsync(ansi().cursorUpLine().a(" ".repeat(s.length())));
            printAsync(ansi().cursor(Constants.row_input + 1, 0));
        }
    }

    public LinkedBlockingQueue<String> getBuffer(){
        return buffer;
    }
}
