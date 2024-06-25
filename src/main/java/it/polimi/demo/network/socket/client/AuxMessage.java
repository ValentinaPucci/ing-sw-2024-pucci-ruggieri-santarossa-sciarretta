package it.polimi.demo.network.socket.client;

import java.io.Serial;
import java.io.Serializable;

public class AuxMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -8001044164833443102L;
    private MessageData data;

    /**
     * Retrieves the user's nickname associated with the message.
     * @return the user's nickname, or null if data is null
     */
    public String getUserNickname() {
        return data != null ? data.nickname() : null;
    }

    /**
     * Sets the user's nickname associated with the message.
     * @param userNickname the user's nickname
     */
    public void setUserNickname(String userNickname) {
        if (data != null) {
            this.data = new MessageData(userNickname, data.isMainControllerTarget());
        } else {
            this.data = new MessageData(userNickname, false);
        }
    }
}
