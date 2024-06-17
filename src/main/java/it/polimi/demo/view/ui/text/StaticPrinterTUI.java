package it.polimi.demo.view.ui.text;

import org.fusesource.jansi.Ansi;
import it.polimi.demo.Constants;

import static org.fusesource.jansi.Ansi.ansi;

public class StaticPrinterTUI {

    public static void print(Ansi msg) {
        runAsync(() -> {
            System.out.println(msg);
            resetCursor();
        });
    }

    public static void print(String msg) {
        runAsync(() -> {
            System.out.println(msg);
            resetCursor();
        });
    }

    public static void print(StringBuilder msg) {
        runAsync(() -> {
            System.out.println(msg);
            resetCursor();
        });
    }

    public static void printNoNewLine(String msg) {
        runAsync(() -> System.out.print(msg));
    }

    public static void printNoNewLine(Ansi msg) {
        runAsync(() -> System.out.print(msg));
    }

    public static void printNoNewLine(StringBuilder msg) {
        runAsync(() -> System.out.print(msg));
    }

    private static void runAsync(Runnable task) {
        new Thread(task).start();
    }

    private static void resetCursor() {
        System.out.println(ansi().cursor(Constants.row_input, 0));
    }
}

