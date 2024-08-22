/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturoar.risc_architecture_simulator;

import com.arturoar.exceptions.CodeSegmentViolatedException;
import com.arturoar.exceptions.WarningException;
import com.arturoar.tools.Assembler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author arturoar
 */
public class Computer {
    protected Memory mem;
    private ControlUnit cu;
    protected ALUnit alu;
    protected Register[] registersPG;
    protected Register[] flags;
    protected Register PC;
    protected Register IR;
    protected Register DS;
    private ArrayList<String> highLevelInstruction;

    public Computer() {
        this.mem = new Memory(8);
        this.cu = new ControlUnit(this);
        this.alu = new ALUnit(this);
        this.registersPG = new Register[8];
        for (int i = 0 ; i < 8 ; i++){
            this.registersPG[i] = new Register(16);
        }
        this.flags = new Register[4];
        for(int i = 0;i < 4;i++){
            this.flags[i] = new Register(1);
        }
        this.PC = new Register(8);
        this.IR = new Register(16);
        this.DS = new Register(8);
    }
    
    public void loadProgramIntoMemory(File file) throws IOException, FileNotFoundException, WarningException{
        Assembler assembler = new Assembler(file);
        ArrayList<String> binaryCode = new ArrayList<>();
        assembler.assemble(binaryCode);
        int numInstr = binaryCode.size();
        for (int i = 0; i < numInstr ; i++){
            this.mem.memory[i].setValueAsInstr(Integer.parseInt(binaryCode.remove(0), 2));
        }
        this.DS.setValueAsInstr(numInstr);
        this.IR.setValueAsInstr(this.mem.memory[0].getValue());
        this.highLevelInstruction = assembler.getHighLevelInstruction();
    }
    
    public void nextInstruction() throws CodeSegmentViolatedException{
        
        cu.decode();
        cu.operandSearch();
        cu.execute();
        cu.fetch();
    }
    public void setMem(Memory mem) {
        this.mem = mem;
    }

    public void setCu(ControlUnit cu) {
        this.cu = cu;
    }

    public void setAlu(ALUnit alu) {
        this.alu = alu;
    }

    public void setRegistersPG(Register[] registersPG) {
        this.registersPG = registersPG;
    }

    public void setFlags(int value,int index) {
        this.flags[index].setValueAsInstr(value);
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

    public Memory getMem() {
        return mem;
    }

    public ControlUnit getCu() {
        return cu;
    }

    public ALUnit getAlu() {
        return alu;
    }

    public Register[] getRegistersPG() {
        return registersPG;
    }

    public Register[] getFlags() {
        return flags;
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

    public ArrayList<String> getHighLevelInstruction() {
        return highLevelInstruction;
    }
    
    
    
    
}
