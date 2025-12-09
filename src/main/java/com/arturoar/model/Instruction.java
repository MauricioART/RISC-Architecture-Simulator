
package com.arturoar.model;

/**
 * Represents a decoded RISC instruction with its operation code and operands.
 * 
 * This class stores the components of an instruction after it has been decoded by the Control Unit.
 * Each instruction contains:
 * - operation: The opcode (0-15) identifying which operation to perform
 * - destRgr: Destination register index (for operations that store results)
 * - srcRgr1: First source register index
 * - srcRgr2: Second source register index (for operations with two source operands)
 * - num: Immediate value or offset (for jumps and load immediate operations)
 * 
 * @author arturoar
 */
public class Instruction {
    
    /**
     * Operation code (opcode): identifies which ALU operation to perform (0-15).
     * 0-4: Arithmetic operations (ADD, SUB, AND, OR, XOR)
     * 5-8: Logical/Memory operations (NOT, SHIFTL, SHIFTR, LOAD)
     * 9-10: Comparison and Store (CMP, STORE)
     * 11-15: Control flow and special (JUMP, JZ, JNZ, LOAD12BIT, NOP)
     */
    private int operation;
    /**
     * Destination register index (0-7).
     * Specifies which general purpose register stores the result of the operation.
     * Not used for comparison, store, or jump operations.
     */
    private int destRgr;
    /**
     * First source register index (0-7).
     * Specifies the first operand register for operations that require it.
     * For unary operations (NOT), this is the only source operand.
     */
    private int srcRgr1;
    /**
     * Second source register index (0-7).
     * Specifies the second operand register for binary operations.
     * Not used for unary operations, load, or jump operations.
     */
    private int srcRgr2;
    /**
     * Immediate value or offset.
     * Used for:
     * - Jump instructions: signed offset to add to program counter
     * - Load immediate (LOAD12BIT): 12-bit constant value to load into register
     * - Unused for register-to-register operations
     */
    private int num;

    /**
     * Retrieves the operation code of this instruction.
     * 
     * @return the opcode (0-15)
     */
    public int getOperation() {
        return operation;
    }

    /**
     * Retrieves the destination register index.
     * 
     * @return the destination register index (0-7)
     */
    public int getDestRgr() {
        return destRgr;
    }

    /**
     * Retrieves the first source register index.
     * 
     * @return the first source register index (0-7)
     */
    public int getSrcRgr1() {
        return srcRgr1;
    }

    /**
     * Retrieves the second source register index.
     * 
     * @return the second source register index (0-7)
     */
    public int getSrcRgr2() {
        return srcRgr2;
    }

    /**
     * Retrieves the immediate value or offset.
     * 
     * @return the immediate value (for LOAD12BIT) or signed offset (for jump operations)
     */
    public int getNum() {
        return num;
    }

    /**
     * Sets the operation code for this instruction.
     * 
     * @param operation the opcode (0-15)
     */
    public void setOperation(int operation) {
        this.operation = operation;
    }

    /**
     * Sets the destination register index.
     * 
     * @param destRgr the destination register index (0-7)
     */
    public void setDestRgr(int destRgr) {
        this.destRgr = destRgr;
    }

    /**
     * Sets the first source register index.
     * 
     * @param srcRgr1 the first source register index (0-7)
     */
    public void setSrcRgr1(int srcRgr1) {
        this.srcRgr1 = srcRgr1;
    }

    /**
     * Sets the second source register index.
     * 
     * @param srcRgr2 the second source register index (0-7)
     */
    public void setSrcRgr2(int srcRgr2) {
        this.srcRgr2 = srcRgr2;
    }

    /**
     * Sets the immediate value or offset.
     * 
     * @param num the immediate value (for LOAD12BIT) or signed offset (for jump operations)
     */
    public void setNum(int num) {
        this.num = num;
    }

    
    
}
