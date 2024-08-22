module com.arturoar.simuladorarquitecturarisc {
    requires javafx.controls;
    requires javafx.fxml;

    // Abre los paquetes para el uso reflexivo por parte de javafx.fxml
    opens com.arturoar.simuladorarquitecturarisc to javafx.fxml;
    opens com.arturoar.excepciones to javafx.fxml;
    opens com.arturoar.herramientas to javafx.fxml;

    // Exporta los paquetes para que otros módulos puedan usarlos
    exports com.arturoar.simuladorarquitecturarisc;
    exports com.arturoar.excepciones;
    exports com.arturoar.herramientas;
}