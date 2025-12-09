module com.arturoar.simuladorarquitecturarisc {
    requires javafx.controls;
    requires javafx.fxml;

    // Abre los paquetes para el uso reflexivo por parte de javafx.fxml
    opens com.arturoar.risc_architecture_simulator to javafx.fxml;
    opens com.arturoar.controller to javafx.fxml;
    opens com.arturoar.exceptions to javafx.fxml;
    opens com.arturoar.tools to javafx.fxml;

    // Exporta los paquetes para que otros módulos puedan usarlos
    exports com.arturoar.risc_architecture_simulator;
    exports com.arturoar.controller;
    exports com.arturoar.exceptions;
    exports com.arturoar.tools;
}