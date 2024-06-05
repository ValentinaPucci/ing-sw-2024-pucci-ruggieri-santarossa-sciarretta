module it.polimi.demo.ingsw2024pucciruggierisantarossasciarretta {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.fasterxml.jackson.databind;
    requires java.rmi;
    requires org.fusesource.jansi;
    requires java.logging;
    requires java.desktop;
    requires json.simple;

    opens it.polimi.demo.model to javafx.fxml;
    exports it.polimi.demo.model;

    exports it.polimi.demo.networking.rmi to java.rmi;
    exports it.polimi.demo.networking to java.rmi;
    exports it.polimi.demo.networking.remoteInterfaces to java.rmi;
    exports it.polimi.demo;
    opens it.polimi.demo to javafx.fxml;
    exports it.polimi.demo.listener to java.rmi;
    exports it.polimi.demo.main to java.rmi;

    exports it.polimi.demo.view.gui to javafx.graphics;
    exports it.polimi.demo.view.gui.controllers to javafx.fxml;
    opens it.polimi.demo.view.gui.controllers to javafx.fxml;
}