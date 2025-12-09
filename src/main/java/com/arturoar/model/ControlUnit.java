package com.arturoar.model;

import com.arturoar.exceptions.CodeSegmentViolatedException;
import com.arturoar.tools.GFG;

/**
 * Control Unit (CU) for the RISC architecture simulator.
 * 
 * The Control Unit orchestrates the instruction execution cycle (Fetch-Decode-Operand Search-Execute).
 * It manages:
 * - Fetching instructions from memory into the Instruction Register (IR)
 * - Decoding the operation code from the instruction
 * - Extracting operand information (register indices, immediate values) based on instruction format
 * - Dispatching the decoded instruction to the ALU for execution
 * 
 * Instruction formats supported:
 * - Three-register operations (opcodes 0-4): ADD, SUB, AND, OR, XOR
 * - Two-register operations (opcodes 5-8): NOT, SHIFTL, SHIFTR, LOAD
 * - Comparison and store operations (opcodes 9-10): CMP, STORE
 * - Jump operations (opcodes 11-13): JUMP, JZ, JNZ with 8-bit signed offsets
 * - Load immediate (opcode 14): LOAD 12-bit immediate value
 * - No operation (opcode 15): NOP
 * 
 * @author arturoar
 */
public class ControlUnit {
    
    private  Computer comp;
    private  Instruction currInstruction;
    
    /**
     * Initializes the Control Unit with a reference to the computer system.
     * Creates a new instruction object to store the current decoded instruction.
     * 
     * @param comp the Computer instance this control unit belongs to
     */
    public ControlUnit(Computer comp) {
        this.currInstruction = new Instruction();
        this.comp = comp;
    }
    
    
    /**
     * Fetch stage: Loads the instruction at the current program counter address from memory
     * into the Instruction Register (IR).
     * This is the first step of the instruction execution cycle.
     */
    public void fetch(){
        comp.IR.setValueAsInstr(comp.mem.memory[comp.PC.getValue()].getValue());
    }
    
    /**
     * Decode stage: Extracts the operation code (opcode) from the Instruction Register.
     * The opcode is located in the first 4 bits of the instruction and identifies which operation to perform.
     * This is the second step of the instruction execution cycle.
     */
    public void decode(){
        this.currInstruction.setOperation(Integer.parseInt(comp.IR.getBinaryValue().substring(0, 4),2));
    }
    
    /**
     * Operand Search stage: Extracts operand information from the instruction based on its type.
     * Different instruction formats use different bit ranges:
     * - Three-register ops (0-4): destination (bits 4-6), src1 (7-9), src2 (10-12)
     * - Two-register ops (5-8): destination (bits 4-6), src1 (7-9)
     * - Comparison/store (9-10): src1 (7-9), src2 (10-12)
     * - Jump operations (11-13): 8-bit signed offset (bits 8-15)
     * - Load immediate (14): 12-bit signed immediate (bits 4-15)
     * - NOP (15): No operands needed
     * This is the third step of the instruction execution cycle.
     */
    public void operandSearch(){
        if (this.currInstruction.getOperation() < 5){
            this.currInstruction.setDestRgr(Integer.parseInt(comp.IR.getBinaryValue().substring(4, 7),2));
            this.currInstruction.setSrcRgr1(Integer.parseInt(comp.IR.getBinaryValue().substring(7, 10),2));
            this.currInstruction.setSrcRgr2(Integer.parseInt(comp.IR.getBinaryValue().substring(10, 13),2));  
        } 
        else if (this.currInstruction.getOperation() < 9){
            this.currInstruction.setDestRgr(Integer.parseInt(comp.IR.getBinaryValue().substring(4, 7),2));
            this.currInstruction.setSrcRgr1(Integer.parseInt(comp.IR.getBinaryValue().substring(7, 10),2));
        }
        else if (this.currInstruction.getOperation() < 11){
            this.currInstruction.setSrcRgr1(Integer.parseInt(comp.IR.getBinaryValue().substring(7, 10),2));
            this.currInstruction.setSrcRgr2(Integer.parseInt(comp.IR.getBinaryValue().substring(10, 13),2));
        }
        else if (this.currInstruction.getOperation() < 14){
            String numStr = comp.IR.getBinaryValue().substring(8, 16);
            if (numStr.startsWith("1")){
                numStr = GFG.getTwosComplement(numStr);
                this.currInstruction.setNum(-1*Integer.parseInt(numStr,2));
            }
            else
                this.currInstruction.setNum(Integer.parseInt(comp.IR.getBinaryValue().substring(8, 16),2));
        }
        else if (this.currInstruction.getOperation() == 14){
            String numStr = comp.IR.getBinaryValue().substring(4, 16);
            if (numStr.startsWith("1")){
                numStr = GFG.getTwosComplement(numStr);
                this.currInstruction.setNum(-1*Integer.parseInt(numStr,2));
            }
            else
                this.currInstruction.setNum(Integer.parseInt(comp.IR.getBinaryValue().substring(4, 16),2));
        }
        else{
            System.out.println("NOP");
        }
    }
    
    /**
     * Execute stage: Dispatches the current instruction to the ALU based on its opcode.
     * Calls the appropriate ALU method with the extracted operands.
     * Supported operations:
     * - 0: ADD, 1: SUB, 2: AND, 3: OR, 4: XOR
     * - 5: NOT, 6: SHIFT LEFT, 7: SHIFT RIGHT
     * - 8: LOAD, 9: COMPARE, 10: STORE
     * - 11: JUMP, 12: JUMP IF ZERO, 13: JUMP IF NOT ZERO
     * - 14: LOAD 12-BIT IMMEDIATE, 15: NO OPERATION
     * This is the fourth step of the instruction execution cycle.
     * 
     * @throws CodeSegmentViolatedException if memory access attempts to read outside the valid code/data segment
     */
    public void execute() throws CodeSegmentViolatedException{
        switch(this.currInstruction.getOperation()){
            case 0:
                comp.alu.add(comp.registersPG[currInstruction.getDestRgr()],
                        comp.registersPG[currInstruction.getSrcRgr1()],
                        comp.registersPG[currInstruction.getSrcRgr2()]);
                break;
            case 1:
                comp.alu.sub(comp.registersPG[currInstruction.getDestRgr()],
                        comp.registersPG[currInstruction.getSrcRgr1()],
                        comp.registersPG[currInstruction.getSrcRgr2()]);
                break;
            case 2:
                comp.alu.and(comp.registersPG[currInstruction.getDestRgr()],
                        comp.registersPG[currInstruction.getSrcRgr1()],
                        comp.registersPG[currInstruction.getSrcRgr2()]);
                break;
            case 3:
                comp.alu.or(comp.registersPG[currInstruction.getDestRgr()],
                        comp.registersPG[currInstruction.getSrcRgr1()],
                        comp.registersPG[currInstruction.getSrcRgr2()]);
                break;
            case 4:
                comp.alu.xor(comp.registersPG[currInstruction.getDestRgr()],
                        comp.registersPG[currInstruction.getSrcRgr1()],
                        comp.registersPG[currInstruction.getSrcRgr2()]);
                break;
            case 5:
                comp.alu.not(comp.registersPG[currInstruction.getDestRgr()],
                        comp.registersPG[currInstruction.getSrcRgr1()]);
                break;
            case 6:
                comp.alu.shiftLeft(comp.registersPG[currInstruction.getDestRgr()],
                        comp.registersPG[currInstruction.getSrcRgr1()]);
                break;
            case 7:
                comp.alu.shiftRight(comp.registersPG[currInstruction.getDestRgr()],
                        comp.registersPG[currInstruction.getSrcRgr1()]);
                break;
            case 8:
                comp.alu.load(comp.registersPG[currInstruction.getDestRgr()],
                        comp.registersPG[currInstruction.getSrcRgr1()]);
                break;
            case 9:
                comp.alu.cmp(comp.registersPG[currInstruction.getSrcRgr1()],
                        comp.registersPG[currInstruction.getSrcRgr2()]);
                break;
            case 10:
                comp.alu.store(comp.registersPG[currInstruction.getSrcRgr1()],
                        comp.registersPG[currInstruction.getSrcRgr2()]);
                break;
            case 11:
                comp.alu.jump(currInstruction.getNum());
                break;
            case 12:
                comp.alu.jz(currInstruction.getNum());
                break;
            case 13:
                comp.alu.jnz(currInstruction.getNum());
                break;
            case 14:
                comp.alu.load12Bit(comp.registersPG[0], currInstruction.getNum());
                break;
            case 15:
                comp.alu.nop();
                break;
            
        }
    }
    
}
