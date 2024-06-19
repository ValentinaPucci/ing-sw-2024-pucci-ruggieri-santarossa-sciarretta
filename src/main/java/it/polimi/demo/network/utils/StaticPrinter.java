package it.polimi.demo.network.utils;

import it.polimi.demo.Constants;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * This class is used to print messages asynchronously. It is used to print messages in the console.
 * It is used instead of System.out.println() to avoid blocking the main thread during its execution.
 */
public class StaticPrinter {

    /**
     * Async printer for ansis
     */
    public static void staticPrinter(Ansi msg) {
        runAsync(() -> {
            System.out.println(msg);
            resetCursor();
        });
    }

    /**
     * Async printer for strings
     */
    public static void staticPrinter(String msg) {
        runAsync(() -> {
            System.out.println(msg);
            resetCursor();
        });
    }

    /**
     * Async printer for string builders
     */
    public static void staticPrinter(StringBuilder msg) {
        runAsync(() -> {
            System.out.println(msg);
            resetCursor();
        });
    }

    /**
     * thread starter for async printing
     */
    private static void runAsync(Runnable task) {
        new Thread(task).start();
    }

    /**
     * reset the cursor to the input row
     */
    private static void resetCursor() {
        System.out.println(ansi().cursor(Constants.row_input, 0));
    }

}
