package it.polimi.demo.networking.socket;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;

public class SocketClient implements SocketVirtualClient {
    final BufferedReader input;
    final ServerProxy server;

    protected SocketClient(BufferedReader input, BufferedWriter output) {
        this.input = input;
        this.server = new ServerProxy(output);
    }


    private void run() throws RemoteException {
        new Thread(() -> {
            try { //THREAD X CLIENT HANDLER SUL SERVER -> LEGE I MESSAGGI CHE ARRIVANO AL SERVER
                runVirtualServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
        runCli();
    }


    private void runVirtualServer() throws IOException {
        String line;
        // Read message type
        while ((line = input.readLine()) != null) {
            // Read message and perform action
            switch (line) {
                case "update" -> this.showUpdate(Integer.parseInt(input.readLine()));
                case "error" -> this.reportError(input.readLine());
                default -> System.err.println("[INVALID MESSAGE]");
            }
        }
    }


    //NB COME RMI
    private void runCli() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            int command = scan.nextInt();

            if (command == 0) {
                server.reset();
            } else {
                server.add(command);
            }
        }
    }

    //NB: CHIAMO I METODI CON LO STESSO NOME X FAR SEMBRARE CHE NON CI SIA LA RETE DI MEZZO

    public void showUpdate(Integer number) {
        // TODO Attenzione! Questo può causare data race con il thread dell'interfaccia o un altro thread
        System.out.print("\n= " + number + "\n> ");
    }


    public void reportError(String details) {
        // TODO Attenzione! Questo può causare data race con il thread dell'interfaccia o un altro thread
        System.err.print("\n[ERROR] " + details + "\n> ");
    }

    public static void main(String[] args) throws IOException {
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        //APERTURA DEL SOCKET
        Socket serverSocket = new Socket(host, port);

        InputStreamReader socketRx = new InputStreamReader(serverSocket.getInputStream());
        OutputStreamWriter socketTx = new OutputStreamWriter(serverSocket.getOutputStream());

        //ISTANZIO IL CLIENT
        new SocketClient(new BufferedReader(socketRx), new BufferedWriter(socketTx)).run();
    }
}
