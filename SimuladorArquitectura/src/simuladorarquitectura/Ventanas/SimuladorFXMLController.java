/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorarquitectura.Ventanas;

import exceptions.CodeSegmentViolatedException;
import exceptions.WarningException;
import herramientas.GFG;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import simuladorarquitectura.Computer;
import simuladorarquitectura.MemoryTable;

/**
 * FXML Controller class
 *
 * @author arturoar
 */
public class SimuladorFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField ax,bx,cx,dx,ex,fx,gx,hx,pc;
    
    @FXML
    private TextField ir,cs,ds,flags;
    
    @FXML
    private Button loadBtn,runBtn,nextBtn,clearBtn;
     @FXML
    private TableView<MemoryTable> memoriaTV;

    @FXML
    private TableColumn<MemoryTable, String> direccionTC;

    @FXML
    private TableColumn<MemoryTable, String> contenidoBinTC;

    @FXML
    private TableColumn<MemoryTable, String> contenidoHexTC;

    @FXML
    private TableColumn<MemoryTable, String> contenidoDecTC;

    @FXML
    private TableColumn<MemoryTable, String> instruccionTC;
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Label mensajeError;
    
    private Computer comp;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.comp = new Computer();
        updateScreen();
        runBtn.setDisable(true);
        nextBtn.setDisable(true);
        clearBtn.setDisable(true);
        
    }    
    @FXML
    private void handleLoad(ActionEvent event) throws IOException, FileNotFoundException, WarningException{
        final FileChooser fc = new FileChooser();
        //fc.getExtensionFilters().add(new ExtensionFilter())
        File file = fc.showOpenDialog(null);
        if (file != null){
            this.comp.loadProgramIntoMemory(file);
        }
        // UPDATE REGISTERS AND MEMORY
        updateScreen();
        runBtn.setDisable(false);
        nextBtn.setDisable(false);
        clearBtn.setDisable(false);
    }
    @FXML
    private void handleRun(ActionEvent event) throws InterruptedException{
        try {
            int i = 0;
            while(i < comp.getDS().getValue()){

                    this.comp.nextInstruction();
                    updateScreen();
                    Thread.sleep(500);
                    i++;
                     

            }
        } catch (Exception ex) {
            this.comp = new Computer();
            mensajeError.setText(ex.getMessage());
            mensajeError.setVisible(true);   
            Thread.sleep(3000);
            mensajeError.setVisible(false);
            updateScreen();
        }
    }
    @FXML
    private void handleNext(ActionEvent event){
        try {
            this.comp.nextInstruction();
            updateScreen();
        } catch (CodeSegmentViolatedException ex) {
            mensajeError.setText(ex.getMessage());
            mensajeError.setVisible(true);
            this.comp = new Computer();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex1) {
                
            }
            mensajeError.setVisible(false);
            updateScreen();
            
        }
        
    }
    @FXML
    private void handleClear(ActionEvent event){
        this.comp = new Computer();
        updateScreen();
    }
    
    public ObservableList<MemoryTable> setMemory(){
        
        
        ObservableList<MemoryTable> rgrMemoria = FXCollections.observableArrayList();
        int memorySize = comp.getMemoria().getMemorySize();
        
        for(int i = 0; i< memorySize; i++){
            if (i < comp.getDS().getValue()){
                rgrMemoria.add(new MemoryTable(GFG.getBinaryNumber(i, 8),
                        comp.getMemoria().getMemory()[i].getBinaryValue(),
                        comp.getMemoria().getMemory()[i].getHexValue(),
                        comp.getMemoria().getMemory()[i].getDecValue(),
                        comp.getInstrAltoNivel().get(i)));
            }
            else{
                rgrMemoria.add(new MemoryTable(GFG.getBinaryNumber(i, 8),
                        comp.getMemoria().getMemory()[i].getBinaryValue(),
                        comp.getMemoria().getMemory()[i].getHexValue(),
                        comp.getMemoria().getMemory()[i].getDecValue(),
                        "-----------"));
            }
            
        }
        return rgrMemoria;
    }
    public void updateScreen( ){
        //Updating registers
        ax.setText(comp.getRegistrosPG()[0].getBinaryValue());
        bx.setText(comp.getRegistrosPG()[1].getBinaryValue());
        cx.setText(comp.getRegistrosPG()[2].getBinaryValue());
        dx.setText(comp.getRegistrosPG()[3].getBinaryValue());
        ex.setText(comp.getRegistrosPG()[4].getBinaryValue());
        fx.setText(comp.getRegistrosPG()[5].getBinaryValue());
        gx.setText(comp.getRegistrosPG()[6].getBinaryValue());
        hx.setText(comp.getRegistrosPG()[7].getBinaryValue());
        pc.setText(comp.getPC().getBinaryValue());
        ir.setText(comp.getIR().getBinaryValue());
        cs.setText("00000000");
        ds.setText(comp.getDS().getBinaryValue());
        flags.setText(mergeFlags());
        //Updating memory
        direccionTC.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        contenidoBinTC.setCellValueFactory(new PropertyValueFactory<>("binario"));
        contenidoHexTC.setCellValueFactory(new PropertyValueFactory<>("hexadecimal"));
        contenidoDecTC.setCellValueFactory(new PropertyValueFactory<>("decimal"));
        instruccionTC.setCellValueFactory(new PropertyValueFactory<>("instruccion"));
        memoriaTV.setItems(setMemory());
        if (comp.getPC().getValue() == comp.getDS().getValue()){
            nextBtn.setDisable(true);    
            runBtn.setDisable(true);
            nextBtn.setDisable(true);
        }
        
    }
    
    private String mergeFlags(){
        String flags = "";
        flags = flags + comp.getBanderas()[3].getBinaryValue() + "   ";
        flags = flags + comp.getBanderas()[2].getBinaryValue() + "   ";
        flags = flags + comp.getBanderas()[1].getBinaryValue() + "   ";
        flags = flags + comp.getBanderas()[0].getBinaryValue();
        return flags;
    }
}
