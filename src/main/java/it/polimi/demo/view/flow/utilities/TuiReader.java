package it.polimi.demo.view.flow.utilities;

import it.polimi.demo.DefaultValues;

import java.util.Scanner;

import static it.polimi.demo.view.text.PrintAsync.printAsync;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * InputReaderTUI class
 * InputReaderTUI is the class that reads the input and add it to the buffer
 */
public class TuiReader extends Thread implements AbstractReader {

    private final BufferData buffer = new BufferData();

    /**
     * Init
     */
    public TuiReader(){
        this.start();
    }

    /**
     * Reads player's inputs
     */
    @Override
    public void run(){
        Scanner sc = new Scanner(System.in);
        while (!this.isInterrupted()) {
            // Reads the input and add what It reads to the buffer sync
            String temp = sc.nextLine();
            buffer.addData(temp);
            printAsync(ansi().cursorUpLine().a(" ".repeat(temp.length())));
            printAsync(ansi().cursor(DefaultValues.row_input + 1, 0));
        }
    }

    /**
     * @return the buffer
     */
    public BufferData getBuffer(){
        return buffer;
    }
}
