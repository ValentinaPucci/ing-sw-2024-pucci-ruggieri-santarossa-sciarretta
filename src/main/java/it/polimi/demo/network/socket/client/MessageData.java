package it.polimi.demo.network.socket.client;

import java.io.Serial;
import java.io.Serializable; /**
 * This record represents the data of a generic message that can be sent by the client to the server.
 */
public record MessageData(String nickname, boolean isMainControllerTarget) implements Serializable {
    @Serial
    private static final long serialVersionUID = -5886817470118365739L;
}
