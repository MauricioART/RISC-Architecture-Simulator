
package com.arturoar.controller;

import com.arturoar.exceptions.CodeSegmentViolatedException;
import com.arturoar.exceptions.WarningException;
import com.arturoar.model.Computer;
import com.arturoar.tools.GFG;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
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
 * FXML Controller for the RISC Architecture Simulator user interface.
 * 
 * This controller manages all user interactions and maintains synchronization between
 * the simulator engine (Computer class) and the JavaFX UI components. It handles:
 * - File loading: Reading and assembling RISC programs from files
 * - Program execution: Running programs step-by-step or continuously
 * - UI updates: Refreshing register and memory displays after each operation
 * - Error handling: Displaying error messages when code segment violations occur
 * 
 * The controller displays:
 * - 8 general purpose registers (AX, BX, CX, DX, EX, FX, GX, HX) in binary format
 * - Special registers: Program Counter (PC), Instruction Register (IR), Code Segment (CS), Data Segment (DS)
 * - Flag registers: Status flags (sign, zero, carry, overflow)
 * - Memory table: Shows all memory cells with address, binary, hex, decimal, and instruction representations
 * 
 * @author arturoar
 */
public class RISC_SimulatorController implements Initializable {

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
    
    
    /**
     * Initializes the controller and prepares the UI for operation.
     * Creates a new Computer instance, displays initial register/memory state,
     * and disables execution buttons until a program is loaded.
     * 
     * @param url the location used to resolve relative paths for the root object
     * @param rb the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.comp = new Computer();
        updateScreen();
        runBtn.setDisable(true);
        nextBtn.setDisable(true);
        clearBtn.setDisable(true);
        
    }    
    /**
     * Handles the Load button event.
     * Opens a file chooser to select an assembly program file, loads it into memory,
     * and updates the display. Enables execution buttons after successful load.
     * Defaults to the ProgramExamples directory for file browsing.
     * 
     * @param event the action event triggered by clicking the Load button
     * @throws IOException if an I/O error occurs while reading the file
     * @throws FileNotFoundException if the selected file is not found
     * @throws WarningException if a warning occurs during assembly
     */
    @FXML
    private void handleLoad(ActionEvent event) throws IOException, FileNotFoundException, WarningException{
        final FileChooser fc = new FileChooser();

        String projectRoot = System.getProperty("user.dir");
        File defaultDirectory = Paths.get(projectRoot, "RISC-Architecture-Simulator/ProgramExamples/").toFile();
        
        // Establecer la ruta inicial
        fc.setInitialDirectory(defaultDirectory);

        File file = fc.showOpenDialog(null);
        if (file != null){
            this.comp.loadProgramIntoMemory(file);
            // UPDATE REGISTERS AND MEMORY
            updateScreen();
            runBtn.setDisable(false);
            nextBtn.setDisable(false);
            clearBtn.setDisable(false);
        }
    }
    /**
     * Handles the Run button event.
     * Executes the loaded program continuously, updating the display after each instruction.
     * Pauses 500ms between instructions for visibility. Stops when all instructions complete
     * or if a code segment violation occurs. Displays error messages and resets the computer on exception.
     * 
     * @param event the action event triggered by clicking the Run button
     * @throws InterruptedException if the execution thread is interrupted
     */
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
    /**
     * Handles the Next button event.
     * Executes a single instruction and updates the display.
     * If a code segment violation occurs, displays an error message, resets the computer,
     * and updates the display.
     * 
     * @param event the action event triggered by clicking the Next button
     */
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
    /**
     * Handles the Clear button event.
     * Resets the computer to its initial state and updates the display.
     * Clears all registers, memory, and flags.
     * 
     * @param event the action event triggered by clicking the Clear button
     */
    @FXML
    private void handleClear(ActionEvent event){
        this.comp = new Computer();
        updateScreen();
    }
    
    /**
     * Creates an observable list of memory table rows for UI display.
     * Includes all memory locations up to the data segment size.
     * Instructions are labeled; data-only locations show "-----------".
     * Each row displays address (binary), content (binary, hex, decimal), and instruction.
     * 
     * @return an ObservableList of MemoryTable objects representing all memory cells
     */
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
    /**
     * Updates the entire UI display with current simulator state.
     * Refreshes all register displays (in binary format), special registers (PC, IR, CS, DS),
     * flag status, and memory table contents. Disables execution buttons when program
     * counter reaches the end of the program.
     */
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
    
    /**
     * Formats the four flag registers into a display string.
     * Combines flag values (sign, zero, carry, overflow) with spacing for readability.
     * 
     * @return a formatted string of flag values separated by spaces
     */
    private String mergeFlags(){
        String flags = "";
        flags = flags + comp.getFlags()[3].getBinaryValue() + "   ";
        flags = flags + comp.getFlags()[2].getBinaryValue() + "   ";
        flags = flags + comp.getFlags()[1].getBinaryValue() + "   ";
        flags = flags + comp.getFlags()[0].getBinaryValue();
        return flags;
    }
}
