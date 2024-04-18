package it.polimi.demo.networking.socket;
import it.polimi.demo.controller.MainController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketServer {
    final ServerSocket listenSocket;
    final MainController controller;
    //ELENCO DEGLI HANDLER CHE UTILIZZO X COMUNICARE CON I CLIENT
    final List<ClientHandler> clients = new ArrayList<>();

    public SocketServer(ServerSocket listenSocket, MainController controller) {
        this.listenSocket = listenSocket;
        this.controller = controller;
    }

    private void runServer() throws IOException {
        Socket clientSocket = null; //TIPO DI JAVA
        //PROVO AD ACCETTARE CONNESSIONI, NEL MOMENTO IN CUI ARRIVANO LE INSERISCO NEL SOCKET, PER PARLARE CON IL CLIENT
        while ((clientSocket = this.listenSocket.accept()) != null) {

            //OSS: SOCKET è MOLTO SIMILE A STANDARD INPUT E STANDARD OUTPUT -> HO UN MODO PER LEGGERE E UN MODO PER SCRIVERE
            InputStreamReader socketRx = new InputStreamReader(clientSocket.getInputStream());
            OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream());

            //PASSO UN CLIENT HANDLER AL SERVER IN MODO CHE SE, DOPO AVER INTERAGITO CON IN CONTROLLER DEVO PROPAGARE UNA MODIFICA A TUTTI I CLIENT, è PIù SEMPLICE FARLO
            ClientHandler handler = new ClientHandler(this.controller, this, new BufferedReader(socketRx), new BufferedWriter(socketTx));

            synchronized (this.clients) {
                //AGGIUNGO IL NUOVO HANDLER ALL'ELENCO DI HANDLER CHE CONOSCEVO GIà
                clients.add(handler);
            }

            //NUOVO THREAD CHE GESTISCE GLI INPUT DEL NUOVO CLIENT
            new Thread(() -> {
                try {
                    handler.runVirtualView();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    //BROADCAST DEGLI UPDATE A TUTTI I CLIENT
    public void broadcastUpdate(Integer value) {
        synchronized (this.clients) {
            for (var client : this.clients) {
                client.showUpdate(value);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        String host = args[0]; //ip (può anche non servire)
        int port = Integer.parseInt(args[1]); //porta del server

        ServerSocket listenSocket = new ServerSocket(port); //devo ascoltare da un socket (TIPO PREDEFINITO DA JAVA)
        //uso un server socket che mi permette di ascoltare nuove connessioni

        new SocketServer(listenSocket, new MainController());
        //NB: SE NON SVILUPPO PARTITE MULTIPLE -> PASSO IL CONTROLLER LEGATO ALLLA PARTITA CHE STO GESTENDO
        //NB: NEL CASO DI PARTITE MULTIPLE -> POSSO DECIDERE DINAMICAMENTE DI CREARE UN NUOVO CONTROLLER NEL MOMENTO HO RIEMPITO UNA PARTITA
        // COSì DA ASSEGNARE LE SUCCESSIVE CONNESSIONI CHE ARRIVANO ALLA NUOVA PARTITA (GESTIONE INTERNA AL MAIN CONTROLLER)

        //NEL MOMENTO IN CUI ACCETTO UNA CONNESSIONE HO UN SOCKET CON CUI COMUNICARE AL CLIENT
    }
}
