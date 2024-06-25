package it.polimi.demo.network.socket.client.ClientToServerMessages;

import it.polimi.demo.network.socket.client.AuxMessage;

import java.io.Serial;
import java.io.Serializable;

public abstract class C2SGenericMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 5406701198899441141L;

    private final AuxMessage data = new AuxMessage();

    public String getUserNickname() {
        return data.getUserNickname();
    }
    public void setUserNickname(String userNickname) {
        data.setUserNickname(userNickname);
    }
}
