package it.polimi.demo.networking.socket;


import java.io.BufferedWriter;
import java.io.PrintWriter;

public class ClientProxy implements SocketVirtualClient {
    final PrintWriter output;

    public ClientProxy(BufferedWriter output) {
        this.output = new PrintWriter(output);
    }

    @Override
    public void showUpdate(Integer number) {
        output.println("update");
        output.println(number);
        output.flush();
    }

    @Override
    public void reportError(String details) {
        output.println("error");
        output.println(details);
        output.flush();
    }
}
