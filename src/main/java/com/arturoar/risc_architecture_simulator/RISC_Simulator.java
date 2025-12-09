
package com.arturoar.risc_architecture_simulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point for the RISC Architecture Simulator application.
 * 
 * This JavaFX application provides a graphical user interface for simulating
 * a RISC (Reduced Instruction Set Computer) architecture. Users can:
 * - Load assembly programs from files
 * - Execute programs step-by-step or continuously
 * - View memory contents in multiple formats (binary, hex, decimal)
 * - Monitor register states during execution
 * - Track program counter and instruction register values
 * 
 * The application loads its UI from an FXML configuration file and manages
 * the interaction between the simulator engine and the user interface.
 * 
 * @author arturoar
 */
public class RISC_Simulator extends Application{

    /**
     * Initializes and displays the main application window.
     * Loads the FXML user interface configuration from RISC_SimulatorFXML.fxml
     * and sets up the primary stage with fixed dimensions (900x600 pixels).
     * The window is non-resizable to maintain consistent UI layout.
     * 
     * @param stage the primary stage for this application
     * @throws Exception if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(RISC_Simulator.class.getResource("RISC_SimulatorFXML.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Basic RISC Architecture Simulator");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     * Entry point for launching the RISC Architecture Simulator application.
     * Starts the JavaFX application runtime and initializes the GUI.
     * 
     * @param args command-line arguments (passed to JavaFX launch)
     */
    public static void main(String[] args) {		
	launch(args);
        
        
    }
    
}