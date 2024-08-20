/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorarquitectura;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import simuladorarquitectura.Ventanas.*;
import herramientas.GFG;
/**
 *
 * @author arturoar
 */
public class SimuladorArquitectura extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("./Ventanas/SimuladorFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Simulador Arquitectura Básica Computadora");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public static void main(String[] args) {		
	launch(args);
        
        
    }
    
}