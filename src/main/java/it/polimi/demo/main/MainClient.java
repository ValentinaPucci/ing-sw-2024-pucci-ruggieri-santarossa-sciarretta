package it.polimi.demo.main;

import it.polimi.demo.Constants;
import it.polimi.demo.main.utils.BoolAdd;
import it.polimi.demo.main.utils.StaticPromptValidator;
import it.polimi.demo.view.dynamic.utilities.TypeConnection;
import it.polimi.demo.view.dynamic.GameDynamic;
import it.polimi.demo.view.gui.FXApplication;
import javafx.application.Application;

import static java.lang.Integer.valueOf;

/**
 * MainClient is the main class for the client side of the application.
 */
public class MainClient {

    /**
     * Main method for the client side of the application.
     * @param args the arguments for the main method
     */
    public static void main(String... args) {

        BoolAdd remoteIP = StaticPromptValidator.promptForIP("Insert remote IP (leave empty for localhost): ");
        if (remoteIP.isNotEmpty())
            Constants.serverIp = remoteIP.add();

        BoolAdd yourIp = StaticPromptValidator.promptForIP("Insert your IP (leave empty for localhost): ");
        if (yourIp.isNotEmpty())
            System.setProperty("java.rmi.server.hostname", yourIp.add());

        int connection_selection = StaticPromptValidator.promptForSelection("Select option:\n1) Socket \n2) RMI \n");

        TypeConnection connection = connection_selection == 1 ? TypeConnection.SOCKET : TypeConnection.RMI;

        int ui_selection = StaticPromptValidator.promptForSelection("\nSelect option:\n1) TUI \n2) GUI \n");

        Runnable action_2 = (ui_selection == 1)
                ? () -> new GameDynamic(connection)
                : () -> Application.launch(FXApplication.class, connection.toString());

        action_2.run();
    }
}

















