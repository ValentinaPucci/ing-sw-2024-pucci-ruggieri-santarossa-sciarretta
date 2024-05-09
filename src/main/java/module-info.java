module it.polimi.demo.ingsw2024pucciruggierisantarossasciarretta {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.fasterxml.jackson.databind;
    requires java.rmi;
    requires org.fusesource.jansi;
    requires java.logging;
    requires java.desktop;

    opens it.polimi.demo.model to javafx.fxml;
    exports it.polimi.demo.model;

    //exports it.polimi.demo.networking.rmi to java.rmi;
    exports it.polimi.demo.networking to java.rmi;
    exports it.polimi.demo.controller.ControllerInterfaces to java.rmi;
    exports it.polimi.demo;
    opens it.polimi.demo to javafx.fxml;
    exports it.polimi.demo.listener to java.rmi;
    exports it.polimi.demo.networking.Applications;
    exports it.polimi.demo.view.UI.GUI to java.rmi;
}