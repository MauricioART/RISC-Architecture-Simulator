/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturoar.simuladorarquitecturarisc;

import com.arturoar.excepciones.CodeSegmentViolatedException;
import com.arturoar.excepciones.WarningException;
import com.arturoar.herramientas.Assembler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author arturoar
 */
public class Computer {
    protected Memory memoria;
    private ControlUnit cu;
    protected ALUnit alu;
    protected Register registrosPG[];
    protected Register banderas[], PC, IR, DS;
    private ArrayList<String> instrAltoNivel;

    public Computer() {
        this.memoria = new Memory(8);
        this.cu = new ControlUnit(this);
        this.alu = new ALUnit(this);
        this.registrosPG = new Register[8];
        for (int i = 0 ; i < 8 ; i++){
            this.registrosPG[i] = new Register(16);
        }
        this.banderas = new Register[4];
        for(int i = 0;i < 4;i++){
            this.banderas[i] = new Register(1);
        }
        this.PC = new Register(8);
        this.IR = new Register(16);
        this.DS = new Register(8);
    }
    
    public void loadProgramIntoMemory(File archivo) throws IOException, FileNotFoundException, WarningException{
        Assembler asmblr = new Assembler(archivo);
        ArrayList<String> binaryCode = new ArrayList<>();
        asmblr.assemble(binaryCode);
        int numInstr = binaryCode.size();
        for (int i = 0; i < numInstr ; i++){
            this.memoria.memory[i].setValueAsInstr(Integer.parseInt(binaryCode.remove(0), 2));
        }
        this.DS.setValueAsInstr(numInstr);
        this.IR.setValueAsInstr(this.memoria.memory[0].getValue());
        this.instrAltoNivel = asmblr.getInstrAltoNivel();
    }
    
    public void nextInstruction() throws CodeSegmentViolatedException{
        
        cu.decode();
        cu.operandSearch();
        cu.execute();
        cu.fetch();         //Siguiente instrucción
    }
    public void setMemoria(Memory memoria) {
        this.memoria = memoria;
    }

    public void setCu(ControlUnit cu) {
        this.cu = cu;
    }

    public void setAlu(ALUnit alu) {
        this.alu = alu;
    }

    public void setRegistrosPG(Register[] registrosPG) {
        this.registrosPG = registrosPG;
    }

    public void setBanderas(int value,int index) {
        this.banderas[index].setValueAsInstr(value); 
    }

    public void setPC(Register PC) {
        this.PC = PC;
    }

    public void setIR(Register IR) {
        this.IR = IR;
    }

    public void setDS(Register DS) {
        this.DS = DS;
    }

    public Memory getMemoria() {
        return memoria;
    }

    public ControlUnit getCu() {
        return cu;
    }

    public ALUnit getAlu() {
        return alu;
    }

    public Register[] getRegistrosPG() {
        return registrosPG;
    }

    public Register[] getBanderas() {
        return banderas;
    }


    public Register getPC() {
        return PC;
    }

    public Register getIR() {
        return IR;
    }

    public Register getDS() {
        return DS;
    }

    public ArrayList<String> getInstrAltoNivel() {
        return instrAltoNivel;
    }
    
    
    
    
}
