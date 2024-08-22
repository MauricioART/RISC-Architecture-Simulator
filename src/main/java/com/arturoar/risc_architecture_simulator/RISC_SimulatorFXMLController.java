/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturoar.risc_architecture_simulator;

import com.arturoar.exceptions.CodeSegmentViolatedException;
import com.arturoar.exceptions.WarningException;
import com.arturoar.tools.GFG;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

/**
 * FXML Controller class
 *
 * @author arturoar
 */
public class RISC_SimulatorFXMLController implements Initializable {

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
    private TableView<MemoryTable> memoryTV;

    @FXML
    private TableColumn<MemoryTable, String> addressTC;

    @FXML
    private TableColumn<MemoryTable, String> contentBinTC;

    @FXML
    private TableColumn<MemoryTable, String> contentHexTC;

    @FXML
    private TableColumn<MemoryTable, String> contentDecTC;

    @FXML
    private TableColumn<MemoryTable, String> instructionTC;
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Label errorMessage;
    
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
            errorMessage.setText(ex.getMessage());
            errorMessage.setVisible(true);
            Thread.sleep(3000);
            errorMessage.setVisible(false);
            updateScreen();
        }
    }
    @FXML
    private void handleNext(ActionEvent event){
        try {
            this.comp.nextInstruction();
            updateScreen();
        } catch (CodeSegmentViolatedException ex) {
            errorMessage.setText(ex.getMessage());
            errorMessage.setVisible(true);
            this.comp = new Computer();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex1) {
                
            }
            errorMessage.setVisible(false);
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
        int memorySize = comp.getMem().getMemorySize();
        
        for(int i = 0; i< memorySize; i++){
            if (i < comp.getDS().getValue()){
                rgrMemoria.add(new MemoryTable(GFG.getBinaryNumber(i, 8),
                        comp.getMem().getMemory()[i].getBinaryValue(),
                        comp.getMem().getMemory()[i].getHexValue(),
                        comp.getMem().getMemory()[i].getDecValue(),
                        comp.getHighLevelInstruction().get(i)));
            }
            else{
                rgrMemoria.add(new MemoryTable(GFG.getBinaryNumber(i, 8),
                        comp.getMem().getMemory()[i].getBinaryValue(),
                        comp.getMem().getMemory()[i].getHexValue(),
                        comp.getMem().getMemory()[i].getDecValue(),
                        "-----------"));
            }
            
        }
        return rgrMemoria;
    }
    public void updateScreen( ){
        //Updating registers
        ax.setText(comp.getRegistersPG()[0].getBinaryValue());
        bx.setText(comp.getRegistersPG()[1].getBinaryValue());
        cx.setText(comp.getRegistersPG()[2].getBinaryValue());
        dx.setText(comp.getRegistersPG()[3].getBinaryValue());
        ex.setText(comp.getRegistersPG()[4].getBinaryValue());
        fx.setText(comp.getRegistersPG()[5].getBinaryValue());
        gx.setText(comp.getRegistersPG()[6].getBinaryValue());
        hx.setText(comp.getRegistersPG()[7].getBinaryValue());
        pc.setText(comp.getPC().getBinaryValue());
        ir.setText(comp.getIR().getBinaryValue());
        cs.setText("00000000");
        ds.setText(comp.getDS().getBinaryValue());
        flags.setText(mergeFlags());
        //Updating memory
        addressTC.setCellValueFactory(new PropertyValueFactory<>("address"));
        contentBinTC.setCellValueFactory(new PropertyValueFactory<>("binaryFormat"));
        contentHexTC.setCellValueFactory(new PropertyValueFactory<>("hexadecimalFormat"));
        contentDecTC.setCellValueFactory(new PropertyValueFactory<>("decimalFormat"));
        instructionTC.setCellValueFactory(new PropertyValueFactory<>("instruction"));
        memoryTV.setItems(setMemory());
        if (comp.getPC().getValue() == comp.getDS().getValue()){
            nextBtn.setDisable(true);    
            runBtn.setDisable(true);
            nextBtn.setDisable(true);
        }
        
    }
    
    private String mergeFlags(){
        String flags = "";
        flags = flags + comp.getFlags()[3].getBinaryValue() + "   ";
        flags = flags + comp.getFlags()[2].getBinaryValue() + "   ";
        flags = flags + comp.getFlags()[1].getBinaryValue() + "   ";
        flags = flags + comp.getFlags()[0].getBinaryValue();
        return flags;
    }
}
