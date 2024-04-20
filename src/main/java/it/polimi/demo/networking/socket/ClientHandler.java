package it.polimi.demo.networking.socket;

import it.polimi.demo.controller.MainController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintStream;

// scopo della classe: avere un oggetto che mi fa da interfaccia con il client --> STESSA FUNZIONE DELLO STUB IN RMI (VIRTUAL CLIENT SUL SERVER)
// CONTIENE LA LOGICA CON CUI LEGGGO I MESSAGGI CHE MI ARRIVANO DAL CLIENT
public class ClientHandler implements SocketVirtualClient {
    final MainController controller;
    final SocketServer server;
    final BufferedReader input;
    final SocketVirtualClient view;
    private PrintStream output;

    public ClientHandler(MainController controller, SocketServer server, BufferedReader input, BufferedWriter output) {
        this.controller = controller;
        this.server = server;
        this.input = input;
        this.view = new ClientProxy(output);
    }

    public void runVirtualView() throws IOException {
        String line;
        // Read message type -> leggo la pima riga del messaggio -> nella mia serializzazioone mi dice il tipo di azione da compiere
        while ((line = input.readLine()) != null) {
            // Read message and perform action
            //nb: scambio messaggi!!!
            switch (line) {
                case "connect" -> {}
//                case "add" -> {
                                        //leggo la seconda riga, faccio parseInt
//                    this.controller.add(Integer.parseInt(input.readLine()));
//                    this.server.broadcastUpdate(this.controller.getState());
//                }
//                case "reset" -> {
                                        // non leggo la seconda riga, è un messaggio vuoto
//                    this.controller.reset();
//                    this.server.broadcastUpdate(this.controller.getState());

                                    // ne casi più complicati devo usare la deserializzazione:
                        //Formato.deserialize<AddMessage>()    oppure    Formato.deserialize<ResetMessage>()
//                }
                default -> System.err.println("[INVALID MESSAGE]");
            }
        }
    }

    @Override
    public void showUpdate(Integer number) {
        this.output.println("update");
        this.output.println(number);
        //STREAM BUFFERED -> SERVE IL FLUSH X ASSICURARMI CHE IL MESSAGGGIO SIA STATO MANDATO
        this.output.flush();
        synchronized (this.view) {
            this.view.showUpdate(number);
        }
    }

    @Override
    public void reportError(String details) {
        synchronized (this.view) {
            this.view.reportError(details);
        }
    }
}