package com.arturoar.controller;

import com.arturoar.exceptions.CodeSegmentViolatedException;
import com.arturoar.exceptions.WarningException;
import com.arturoar.model.Computer;
import com.arturoar.model.Register;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.NoSuchElementException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class RISC_SimulatorController {
    private static final PseudoClass CURRENT = PseudoClass.getPseudoClass("current");
    private static final PseudoClass CHANGED = PseudoClass.getPseudoClass("changed");
    private static final PseudoClass ACTIVE = PseudoClass.getPseudoClass("active");
    private static final PseudoClass ERROR = PseudoClass.getPseudoClass("error");

    @FXML private Button loadBtn, runBtn, nextBtn, clearBtn;
    @FXML private Label programName, status;
    @FXML private Label ax, bx, cx, dx, ex, fx, gx, hx, pc, ir, cs, ds;
    @FXML private Label sign, zero, overflow, carry;
    @FXML private TableView<MemoryTable> memoryTV;
    @FXML private TableColumn<MemoryTable, String> addressTC, instructionTC, contentBinTC,
            contentHexTC, contentDecTC;

    private final Timeline execution = new Timeline(new KeyFrame(Duration.millis(500), event -> step()));
    private Computer comp = new Computer();
    private File program;
    private boolean running;
    private String executionError;
    private int currentAddress = -1;
    private Label[] registers;
    private Label[] flags;

    @FXML
    private void initialize() {
        registers = new Label[]{ax, bx, cx, dx, ex, fx, gx, hx};
        flags = new Label[]{carry, overflow, zero, sign};
        execution.setCycleCount(Timeline.INDEFINITE);

        addressTC.setCellValueFactory(row -> new ReadOnlyStringWrapper(row.getValue().getAddress()));
        instructionTC.setCellValueFactory(row -> new ReadOnlyStringWrapper(row.getValue().getInstruction()));
        contentBinTC.setCellValueFactory(row -> new ReadOnlyStringWrapper(row.getValue().getBinaryFormat()));
        contentHexTC.setCellValueFactory(row -> new ReadOnlyStringWrapper(row.getValue().getHexadecimalFormat()));
        contentDecTC.setCellValueFactory(row -> new ReadOnlyStringWrapper(row.getValue().getDecimalFormat()));
        memoryTV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        memoryTV.setRowFactory(table -> new TableRow<>() {
            @Override
            protected void updateItem(MemoryTable item, boolean empty) {
                super.updateItem(item, empty);
                boolean current = !empty && getIndex() == currentAddress;
                pseudoClassStateChanged(CURRENT, current);
                setAccessibleText(current ? "Instrucción en " + item.getAddress() : null);
            }
        });
        updateScreen();
    }

    @FXML
    private void handleLoad() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Cargar programa");
        File examples = new File(System.getProperty("user.dir"), "ProgramExamples");
        File directory = program == null ? examples : program.getParentFile();
        if (directory != null && directory.isDirectory()) {
            chooser.setInitialDirectory(directory);
        }

        File file = chooser.showOpenDialog(loadBtn.getScene().getWindow());
        if (file != null) {
            loadProgram(file);
        }
    }

    private void loadProgram(File file) {
        Computer loaded = new Computer();
        try {
            loaded.loadProgramIntoMemory(file);
        } catch (IOException | WarningException | NumberFormatException | NoSuchElementException
                 | IndexOutOfBoundsException ex) {
            showError("No se pudo cargar " + file.getName() + ". Revisa el archivo y sus instrucciones.");
            return;
        }
        comp = loaded;
        program = file;
        executionError = null;
        updateScreen();
        memoryTV.scrollTo(0);
    }

    @FXML
    private void handleRun() {
        if (running) {
            stopExecution();
            updateExecutionStatus();
        } else if (canStep()) {
            running = true;
            step();
            if (running) {
                execution.playFromStart();
            }
        }
    }

    @FXML
    private void handleNext() {
        step();
    }

    @FXML
    private void handleClear() {
        stopExecution();
        comp = new Computer();
        program = null;
        executionError = null;
        updateScreen();
        memoryTV.scrollTo(0);
    }

    private void step() {
        if (!canStep()) {
            return;
        }
        try {
            comp.nextInstruction();
        } catch (CodeSegmentViolatedException ex) {
            executionError = "Error en " + address(comp.getPC().getValue()) + " · " + ex.getMessage();
            stopExecution();
            updateExecutionStatus();
            memoryTV.refresh();
            return;
        }

        if (!canStep()) {
            stopExecution();
        }
        updateCpu(true);
        updateExecutionStatus();
        refreshMemory();
        if (currentAddress >= 0) {
            memoryTV.scrollTo(Math.max(0, currentAddress - 3));
        }
    }

    private boolean canStep() {
        return executionError == null && comp.getPC().getValue() < comp.getDS().getValue();
    }

    private void stopExecution() {
        execution.stop();
        running = false;
    }

    private void updateExecutionStatus() {
        boolean ready = canStep();
        int address = comp.getPC().getValue();
        currentAddress = ready || executionError != null ? address : -1;
        loadBtn.setDisable(running);
        runBtn.setDisable(!ready);
        runBtn.setText(running ? "Pausar" : "Ejecutar");
        nextBtn.setDisable(!ready || running);
        clearBtn.setDisable(program == null);
        status.pseudoClassStateChanged(ERROR, false);
        if (executionError != null) {
            showError(executionError);
        } else if (ready) {
            status.setText("PC " + address(address) + "  ·  Siguiente: "
                    + comp.getHighLevelInstruction().get(address).strip());
        } else if (program == null) {
            status.setText("Carga un programa para comenzar");
        } else if (comp.getDS().getValue() == 0) {
            status.setText("El programa está vacío");
        } else {
            status.setText("Programa terminado  ·  PC " + address(address));
        }
    }

    private void showError(String message) {
        status.setText(message);
        status.pseudoClassStateChanged(ERROR, true);
    }

    public void updateScreen() {
        programName.setText(program == null ? "Ningún programa cargado" : program.getName());
        updateCpu(false);
        updateExecutionStatus();
        memoryTV.setItems(setMemory());
    }

    private void updateCpu(boolean markChanges) {
        for (int i = 0; i < registers.length; i++) {
            showRegister(registers[i], comp.getRegistersPG()[i], 4, markChanges);
        }
        showRegister(pc, comp.getPC(), 2, markChanges);
        showRegister(ir, comp.getIR(), 4, markChanges);
        cs.setText("00");
        showRegister(ds, comp.getDS(), 2, markChanges);
        String[] names = {"C", "O", "Z", "S"};
        for (int i = 0; i < flags.length; i++) {
            int value = comp.getFlags()[i].getValue();
            Label flag = flags[i];
            String text = names[i] + " " + value;
            flag.pseudoClassStateChanged(CHANGED, markChanges && !text.equals(flag.getText()));
            flag.pseudoClassStateChanged(ACTIVE, value != 0);
            flag.setText(text);
        }
    }

    private void showRegister(Label label, Register register, int digits, boolean markChanges) {
        String hex = register.getHexValue().toUpperCase(Locale.ROOT);
        String text = hex.substring(hex.length() - digits);
        label.pseudoClassStateChanged(CHANGED, markChanges && !text.equals(label.getText()));
        label.setText(text);
        if (label.getTooltip() == null) {
            label.setTooltip(new Tooltip());
        }
        label.getTooltip().setText("Binario: " + register.getBinaryValue() + "\nDecimal: "
                + register.getDecValue());
    }

    public ObservableList<MemoryTable> setMemory() {
        ObservableList<MemoryTable> rows = FXCollections.observableArrayList();
        Register[] memory = comp.getMem().getMemory();
        for (int i = 0; i < memory.length; i++) {
            rows.add(memoryRow(i, memory[i]));
        }
        return rows;
    }

    private void refreshMemory() {
        ObservableList<MemoryTable> rows = memoryTV.getItems();
        Register[] memory = comp.getMem().getMemory();
        for (int i = 0; i < memory.length; i++) {
            if (!rows.get(i).getDecimalFormat().equals(memory[i].getDecValue())) {
                rows.set(i, memoryRow(i, memory[i]));
            }
        }
        memoryTV.refresh();
    }

    private MemoryTable memoryRow(int index, Register word) {
        String instruction = index < comp.getDS().getValue()
                ? comp.getHighLevelInstruction().get(index).strip() : "—";
        return new MemoryTable(address(index), word.getBinaryValue(),
                word.getHexValue().toUpperCase(Locale.ROOT), word.getDecValue(), instruction);
    }

    private static String address(int value) {
        return String.format(Locale.ROOT, "0x%02X", value);
    }
}
