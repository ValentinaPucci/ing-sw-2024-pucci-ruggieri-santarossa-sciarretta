package it.polimi.demo.networking.socket;

import java.io.BufferedWriter;
import java.io.PrintWriter;

public class ServerProxy implements SocketVirtualServer {
    final PrintWriter output;

    public ServerProxy(BufferedWriter output) {
        this.output = new PrintWriter(output);
    }

    @Override
    public void connect(SocketVirtualClient client) {
        output.println("connect");
        output.flush();
    }

    @Override
    public void add(Integer number) {
        output.println("add");
        output.println(number);
        output.flush();
    }

    @Override
    public void reset() {
        output.println("reset");
        output.flush();
    }
}