/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturoar.risc_architecture_simulator;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author arturoar
 */
public class MemoryTable {

    private SimpleStringProperty address;
    private SimpleStringProperty binaryFormat;
    private SimpleStringProperty hexadecimalFormat;
    private SimpleStringProperty decimalFormat;
    private SimpleStringProperty instruction;

    public MemoryTable(String address, String binaryFormat, String hexadecimalFormat, String decimalFormat, String instruction) {
        this.address = new SimpleStringProperty(address);
        this.binaryFormat = new SimpleStringProperty(binaryFormat);
        this.hexadecimalFormat = new SimpleStringProperty(hexadecimalFormat);
        this.decimalFormat = new SimpleStringProperty(decimalFormat);
        this.instruction = new SimpleStringProperty(instruction);
    }

    public void setAddress(SimpleStringProperty address) {
        this.address = address;
    }

    public void setBinaryFormat(SimpleStringProperty binaryFormat) {
        this.binaryFormat = binaryFormat;
    }

    public void setHexadecimalFormat(SimpleStringProperty hexadecimalFormat) {
        this.hexadecimalFormat = hexadecimalFormat;
    }

    public void setDecimalFormat(SimpleStringProperty decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    public void setInstruction(SimpleStringProperty instruction) {
        this.instruction = instruction;
    }

    public String getAddress() {
        return address.get();
    }

    public String getBinaryFormat() {
        return binaryFormat.get();
    }

    public String getHexadecimalFormat() {
        return hexadecimalFormat.get();
    }

    public String getDecimalFormat() {
        return decimalFormat.get();
    }

    public String getInstruction() {
        return instruction.get();
    }


}