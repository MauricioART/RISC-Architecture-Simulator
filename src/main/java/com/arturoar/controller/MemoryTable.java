
package com.arturoar.controller;

import javafx.beans.property.SimpleStringProperty;

/**
 * Data model for memory display in the user interface.
 * 
 * This class represents a single row in the memory table view, providing multiple representations
 * of a memory cell's contents for visualization purposes. Each memory location is displayed in
 * several formats to aid understanding:
 * - Binary format: 16-bit binary representation
 * - Hexadecimal format: Base-16 representation
 * - Decimal format: Base-10 integer representation
 * - Instruction format: Assembly language representation of the instruction (if applicable)
 * 
 * Uses JavaFX SimpleStringProperty for binding with UI controls, allowing real-time updates
 * when memory values change during program execution.
 * 
 * @author arturoar
 */
public class MemoryTable {

    /**
     * Memory address of this cell (in decimal).
     * Identifies the location in memory.
     */
    private SimpleStringProperty address;
    /**
     * Content of the memory cell in binary format (16-bit representation).
     * Provides a view of the cell's bit pattern.
     */
    private SimpleStringProperty binaryFormat;
    /**
     * Content of the memory cell in hexadecimal format (base-16).
     * Provides a compact representation for viewing.
     */
    private SimpleStringProperty hexadecimalFormat;
    /**
     * Content of the memory cell in decimal format (base-10 integer).
     * Provides a human-readable numeric representation.
     */
    private SimpleStringProperty decimalFormat;
    /**
     * Assembly language representation of the instruction stored at this memory address.
     * Displays the human-readable mnemonic and operands (if applicable).
     */
    private SimpleStringProperty instruction;

    /**
     * Initializes a MemoryTable row with all representations of a memory cell's value.
     * 
     * @param address the memory address (decimal) as a string
     * @param binaryFormat the cell content in binary format (16-bit)
     * @param hexadecimalFormat the cell content in hexadecimal format
     * @param decimalFormat the cell content in decimal format
     * @param instruction the assembly language representation of the instruction
     */
    public MemoryTable(String address, String binaryFormat, String hexadecimalFormat, String decimalFormat, String instruction) {
        this.address = new SimpleStringProperty(address);
        this.binaryFormat = new SimpleStringProperty(binaryFormat);
        this.hexadecimalFormat = new SimpleStringProperty(hexadecimalFormat);
        this.decimalFormat = new SimpleStringProperty(decimalFormat);
        this.instruction = new SimpleStringProperty(instruction);
    }

    /**
     * Sets the memory address for this table row.
     * 
     * @param address the memory address as a SimpleStringProperty
     */
    public void setAddress(SimpleStringProperty address) {
        this.address = address;
    }

    /**
     * Sets the binary format representation of the memory cell's content.
     * 
     * @param binaryFormat the 16-bit binary representation as a SimpleStringProperty
     */
    public void setBinaryFormat(SimpleStringProperty binaryFormat) {
        this.binaryFormat = binaryFormat;
    }

    /**
     * Sets the hexadecimal format representation of the memory cell's content.
     * 
     * @param hexadecimalFormat the hexadecimal representation as a SimpleStringProperty
     */
    public void setHexadecimalFormat(SimpleStringProperty hexadecimalFormat) {
        this.hexadecimalFormat = hexadecimalFormat;
    }

    /**
     * Sets the decimal format representation of the memory cell's content.
     * 
     * @param decimalFormat the decimal (base-10) representation as a SimpleStringProperty
     */
    public void setDecimalFormat(SimpleStringProperty decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    /**
     * Sets the assembly language representation of the instruction at this memory address.
     * 
     * @param instruction the instruction mnemonic and operands as a SimpleStringProperty
     */
    public void setInstruction(SimpleStringProperty instruction) {
        this.instruction = instruction;
    }

    /**
     * Retrieves the memory address for this table row.
     * 
     * @return the memory address as a string (decimal)
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * Retrieves the binary format representation of the memory cell's content.
     * 
     * @return the 16-bit binary representation as a string
     */
    public String getBinaryFormat() {
        return binaryFormat.get();
    }

    /**
     * Retrieves the hexadecimal format representation of the memory cell's content.
     * 
     * @return the hexadecimal representation as a string
     */
    public String getHexadecimalFormat() {
        return hexadecimalFormat.get();
    }

    /**
     * Retrieves the decimal format representation of the memory cell's content.
     * 
     * @return the decimal (base-10) representation as a string
     */
    public String getDecimalFormat() {
        return decimalFormat.get();
    }

    /**
     * Retrieves the assembly language representation of the instruction at this memory address.
     * 
     * @return the instruction mnemonic and operands as a string
     */
    public String getInstruction() {
        return instruction.get();
    }


}