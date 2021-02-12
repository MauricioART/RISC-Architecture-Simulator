/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorarquitectura;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author arturoar
 */
public class MemoryTable {
    
    private SimpleStringProperty direccion;
    private SimpleStringProperty binario;
    private SimpleStringProperty hexadecimal;
    private SimpleStringProperty decimal;
    private SimpleStringProperty instruccion;

    public MemoryTable(String direccion, String binario, String hexadecimal, String decimal, String instruccion) {
        this.direccion = new SimpleStringProperty(direccion);
        this.binario = new SimpleStringProperty(binario);
        this.hexadecimal = new SimpleStringProperty(hexadecimal);
        this.decimal = new SimpleStringProperty(decimal);
        this.instruccion = new SimpleStringProperty(instruccion);
    }

    public void setDireccion(SimpleStringProperty direccion) {
        this.direccion = direccion;
    }

    public void setBinario(SimpleStringProperty binario) {
        this.binario = binario;
    }

    public void setHexadecimal(SimpleStringProperty hexadecimal) {
        this.hexadecimal = hexadecimal;
    }

    public void setDecimal(SimpleStringProperty decimal) {
        this.decimal = decimal;
    }

    public void setInstruccion(SimpleStringProperty instruccion) {
        this.instruccion = instruccion;
    }

    public String getDireccion() {
        return direccion.get();
    }

    public String getBinario() {
        return binario.get();
    }

    public String getHexadecimal() {
        return hexadecimal.get();
    }

    public String getDecimal() {
        return decimal.get();
    }

    public String getInstruccion() {
        return instruccion.get();
    }
    
            
}
