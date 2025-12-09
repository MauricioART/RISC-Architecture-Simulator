package com.arturoar.model;

import com.arturoar.exceptions.CodeSegmentViolatedException;
import com.arturoar.exceptions.WarningException;
import com.arturoar.tools.Assembler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a RISC (Reduced Instruction Set Computer) architecture simulator.
 * 
 * This class models a complete computer system with the following components:
 * - Memory: Manages program and data storage
 * - Control Unit (CU): Coordinates instruction fetch, decode, and execution
 * - Arithmetic Logic Unit (ALU): Performs arithmetic and logical operations
 * - General Purpose Registers (8 × 16-bit): Store intermediate values and operands
 * - Flag Registers (4 × 1-bit): Store status flags (e.g., zero, carry, sign, overflow)
 * - Program Counter (PC): Tracks the current instruction address
 * - Instruction Register (IR): Holds the current instruction being executed
 * - Data Segment (DS): Defines the size of the data/program segment
 * 
 * The Computer class manages the lifecycle of instruction execution, including:
 * loading assembly programs into memory and executing them step by step through
 * the standard fetch-decode-operand search-execute cycle.
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

    /**
     * Initializes a new Computer instance with default components.
     * Sets up memory, control unit, ALU, general purpose registers, flags, and special registers.
     * Creates 8 general purpose registers (16-bit each), 4 flags (1-bit each),
     * and special registers: PC (Program Counter), IR (Instruction Register), and DS (Data Segment).
     */
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
    
    /**
     * Loads a program from an assembly file into the computer's memory.
     * 
     * @param file the assembly file to be loaded and assembled
     * @throws IOException if an I/O error occurs while reading the file
     * @throws FileNotFoundException if the specified file is not found
     * @throws WarningException if a warning occurs during assembly
     */
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
    
    /**
     * Executes the next instruction cycle.
     * Performs the following steps in order: decode, operand search, execute, and fetch.
     * 
     * @throws CodeSegmentViolatedException if an attempt is made to access memory outside the code segment
     */
    public void nextInstruction() throws CodeSegmentViolatedException{
        
        cu.decode();
        cu.operandSearch();
        cu.execute();
        cu.fetch();
    }
    /**
     * Sets the memory component of the computer.
     * 
     * @param mem the Memory object to set
     */
    public void setMem(Memory mem) {
        this.mem = mem;
    }

    /**
     * Sets the control unit component of the computer.
     * 
     * @param cu the ControlUnit object to set
     */
    public void setCu(ControlUnit cu) {
        this.cu = cu;
    }

    /**
     * Sets the arithmetic logic unit (ALU) component of the computer.
     * 
     * @param alu the ALUnit object to set
     */
    public void setAlu(ALUnit alu) {
        this.alu = alu;
    }

    /**
     * Sets the general purpose registers array.
     * 
     * @param registersPG the array of general purpose registers to set
     */
    public void setRegistersPG(Register[] registersPG) {
        this.registersPG = registersPG;
    }

    /**
     * Sets a flag register value at the specified index.
     * 
     * @param value the value to set in the flag register
     * @param index the index of the flag register (0-3)
     */
    public void setFlags(int value,int index) {
        this.flags[index].setValueAsInstr(value);
    }

    /**
     * Sets the program counter (PC) register.
     * 
     * @param PC the Register object to set as the program counter
     */
    public void setPC(Register PC) {
        this.PC = PC;
    }

    /**
     * Sets the instruction register (IR).
     * 
     * @param IR the Register object to set as the instruction register
     */
    public void setIR(Register IR) {
        this.IR = IR;
    }

    /**
     * Sets the data segment (DS) register.
     * 
     * @param DS the Register object to set as the data segment
     */
    public void setDS(Register DS) {
        this.DS = DS;
    }

    /**
     * Retrieves the memory component of the computer.
     * 
     * @return the Memory object
     */
    public Memory getMem() {
        return mem;
    }

    /**
     * Retrieves the control unit component of the computer.
     * 
     * @return the ControlUnit object
     */
    public ControlUnit getCu() {
        return cu;
    }

    /**
     * Retrieves the arithmetic logic unit (ALU) component of the computer.
     * 
     * @return the ALUnit object
     */
    public ALUnit getAlu() {
        return alu;
    }

    /**
     * Retrieves the general purpose registers array.
     * 
     * @return the array of general purpose registers
     */
    public Register[] getRegistersPG() {
        return registersPG;
    }

    /**
     * Retrieves the flags registers array.
     * 
     * @return the array of flag registers
     */
    public Register[] getFlags() {
        return flags;
    }


    /**
     * Retrieves the program counter (PC) register.
     * 
     * @return the program counter register
     */
    public Register getPC() {
        return PC;
    }

    /**
     * Retrieves the instruction register (IR).
     * 
     * @return the instruction register
     */
    public Register getIR() {
        return IR;
    }

    /**
     * Retrieves the data segment (DS) register.
     * 
     * @return the data segment register
     */
    public Register getDS() {
        return DS;
    }

    /**
     * Retrieves the high-level instruction list assembled from the loaded program.
     * 
     * @return an ArrayList containing the high-level assembly instructions
     */
    public ArrayList<String> getHighLevelInstruction() {
        return highLevelInstruction;
    }
    
    
    
    
}
