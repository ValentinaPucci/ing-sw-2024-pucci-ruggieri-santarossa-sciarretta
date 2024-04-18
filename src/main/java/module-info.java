module it.polimi.demo.ingsw2024pucciruggierisantarossasciarretta {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.fasterxml.jackson.databind;
    requires java.rmi;
    requires org.fusesource.jansi;

    opens it.polimi.demo.model to javafx.fxml;
    exports it.polimi.demo.model;
}