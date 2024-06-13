package it.polimi.demo.view.dynamic.utilities.parser;

import it.polimi.demo.view.text.PrintAsync;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class TuiReader extends Thread implements AbstractReader {

    private final LinkedBlockingQueue<String> buffer = new LinkedBlockingQueue<>();

    public LinkedBlockingQueue<String> getBuffer(){
        return buffer;
    }

    public TuiReader() {
        this.start();
    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (!Thread.currentThread().isInterrupted()) {
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    buffer.add(input);
                    PrintAsync.printAsync("\033[1A\033[2K");
                }
            }
        } catch (Exception e) {
            // Handle other unexpected exceptions if necessary
            e.printStackTrace();
        }
    }

}
