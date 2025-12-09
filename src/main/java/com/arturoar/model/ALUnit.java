package com.arturoar.model;

import com.arturoar.exceptions.CodeSegmentViolatedException;

/**
 * Arithmetic Logic Unit (ALU) for the RISC architecture simulator.
 * 
 * This class implements all arithmetic, logical, memory, and control flow operations
 * supported by the RISC computer. Operations include:
 * - Arithmetic: add, subtract, compare
 * - Logical: AND, OR, XOR, NOT
 * - Memory: load from memory, store to memory
 * - Control Flow: jump, conditional jumps (zero/non-zero), no operation
 * - Bit Operations: shift left, shift right
 * - Data Loading: load 12-bit immediate values
 * 
 * Each operation updates the program counter (PC) and relevant flags after execution.
 * Flag registers track: overflow, carry, zero, and sign status.
 * 
 * @author arturoar
 */
public class ALUnit {

    private final Computer computer;
    
    /**
     * Initializes the ALU with a reference to the computer system.
     * 
     * @param computer the Computer instance this ALU belongs to
     */
    public ALUnit(Computer computer) {
        this.computer = computer;
    }
    /**
     * Performs addition of two source registers and stores the result in destination register.
     * Updates flags for overflow, carry, zero, and sign status.
     * 
     * @param dest the destination register where the result is stored
     * @param src1 the first source operand register
     * @param src2 the second source operand register
     */
    public void add(Register dest, Register src1,Register src2){
        
        int sum = src1.getValue() + src2.getValue();
        int res = dest.setValue(sum);
        if ( res == 1){
            computer.flags[0].setValueAsInstr(0);
            computer.flags[1].setValueAsInstr(0);
            if (sum == 0){
                computer.flags[2].setValueAsInstr(1);
                computer.flags[3].setValueAsInstr(0);
            }else{
                computer.flags[2].setValueAsInstr(0);
                if (sum < 0){
                    computer.flags[3].setValueAsInstr(1);
                }
                else{
                    computer.flags[3].setValueAsInstr(0);
                }
            }
        }
        else{
            computer.flags[0].setValueAsInstr(1);
            computer.flags[1].setValueAsInstr(1);
            computer.flags[2].setValueAsInstr(0);
             if (res == 0){
                computer.flags[3].setValueAsInstr(0);
             }else{
                computer.flags[3].setValueAsInstr(1);
             }
            
        }
        computer.PC.setValue(computer.PC.getValue()+1);
        
    }
    /**
     * Performs subtraction of two source registers and stores the result in destination register.
     * Calculates src1 - src2. Updates flags for overflow, carry, zero, and sign status.
     * 
     * @param dest the destination register where the result is stored
     * @param src1 the first source operand register (minuend)
     * @param src2 the second source operand register (subtrahend)
     */
    public void sub(Register dest, Register src1,Register src2){
        int diff = src1.getValue() - src2.getValue();
        int res = dest.setValue(diff);
        if ( res == 1){
            computer.flags[0].setValueAsInstr(0);
            computer.flags[1].setValueAsInstr(0);
            if (diff == 0){
                computer.flags[2].setValueAsInstr(1);
                computer.flags[3].setValueAsInstr(0);
            }else{
                computer.flags[2].setValueAsInstr(0);
                if (diff < 0){
                    computer.flags[3].setValueAsInstr(1);
                }
                else{
                    computer.flags[3].setValueAsInstr(0);
                }
            }
        }
        else{
            computer.flags[0].setValueAsInstr(1);
            computer.flags[1].setValueAsInstr(1);
            computer.flags[2].setValueAsInstr(0);
             if (res == 0){
                computer.flags[3].setValueAsInstr(0);
             }else{
                computer.flags[3].setValueAsInstr(1);
             }
            
        }
        computer.PC.setValue(computer.PC.getValue()+1);
    }
    /**
     * Performs bitwise AND operation on two source registers and stores the result in destination register.
     * Updates sign and zero flags. Clears overflow and carry flags.
     * 
     * @param dest the destination register where the result is stored
     * @param src1 the first source operand register
     * @param src2 the second source operand register
     */
    public void and(Register dest, Register src1,Register src2){
        dest.setValueAsInstr(src1.getValue() & src2.getValue());
        computer.flags[0].setValueAsInstr(0);
        computer.flags[1].setValueAsInstr(0);
        String res = dest.getBinaryValue();
        if (res.startsWith("1")){
            computer.flags[3].setValueAsInstr(1);
        }
        else{
            computer.flags[3].setValueAsInstr(0);
        }
        if (dest.getValue() == 0){
            computer.flags[2].setValueAsInstr(1);
        }
        else{
            computer.flags[2].setValueAsInstr(0);
        }
        computer.PC.setValue(computer.PC.getValue()+1);
        
    }
    /**
     * Performs bitwise OR operation on two source registers and stores the result in destination register.
     * Updates sign and zero flags.
     * 
     * @param dest the destination register where the result is stored
     * @param src1 the first source operand register
     * @param src2 the second source operand register
     */
    public void or(Register dest, Register src1,Register src2){
        dest.setValueAsInstr(src1.getValue() | src2.getValue());
        String res = dest.getBinaryValue();
        if (res.startsWith("1")){
            computer.flags[3].setValueAsInstr(1);
        }
        else{
            computer.flags[3].setValueAsInstr(0);
        }
        if (dest.getValue() == 0){
            computer.flags[2].setValueAsInstr(1);
        }
        else{
            computer.flags[2].setValueAsInstr(0);
        }
        computer.PC.setValue(computer.PC.getValue()+1);
    }
    /**
     * Performs bitwise XOR (exclusive OR) operation on two source registers and stores the result in destination register.
     * Updates sign and zero flags.
     * 
     * @param dest the destination register where the result is stored
     * @param src1 the first source operand register
     * @param src2 the second source operand register
     */
    public void xor(Register dest, Register src1,Register src2){
        dest.setValueAsInstr(src1.getValue() ^ src2.getValue());
        String res = dest.getBinaryValue();
        if (res.startsWith("1")){
            computer.flags[3].setValueAsInstr(1);
        }
        else{
            computer.flags[3].setValueAsInstr(0);
        }
        if (dest.getValue() == 0){
            computer.flags[2].setValueAsInstr(1);
        }
        else{
            computer.flags[2].setValueAsInstr(0);
        }
        computer.PC.setValue(computer.PC.getValue()+1);
    }
    /**
     * Performs bitwise NOT (complement) operation on a source register and stores the result in destination register.
     * Inverts all bits in the source value. Updates sign and zero flags.
     * 
     * @param dest the destination register where the result is stored
     * @param src the source operand register
     */
    public void not(Register dest, Register src){
        String value = src.getBinaryValue();
        String notValue = "";
        for(int i = 0; i < src.getSize();i++){
            if( value.charAt(i)== '1'){
                notValue = notValue + "0";
            }
            else{
                notValue = notValue + "1";
            }
        }
        dest.setValueAsInstr(Integer.parseInt(notValue, 2));
        String res = dest.getBinaryValue();
        if (res.startsWith("1")){
            computer.flags[3].setValueAsInstr(1);
        }
        else{
            computer.flags[3].setValueAsInstr(0);
        }
        if (dest.getValue() == 0){
            computer.flags[2].setValueAsInstr(1);
        }
        else{
            computer.flags[2].setValueAsInstr(0);
        }
        computer.PC.setValue(computer.PC.getValue()+1);
    }
    /**
     * Compares two source registers by computing src1 - src2 without storing the result.
     * Updates flags based on the comparison result to indicate equality, sign, overflow, and carry.
     * 
     * @param src1 the first source operand register
     * @param src2 the second source operand register
     */
    public void cmp(Register src1,Register src2){
        int diff = src1.getValue() - src2.getValue();
        Register dest = new Register(16);
        int res = dest.setValue(diff);
        if ( res == 1){
            computer.flags[0].setValueAsInstr(0);
            computer.flags[1].setValueAsInstr(0);
            if (diff == 0){
                computer.flags[2].setValueAsInstr(1);
                computer.flags[3].setValueAsInstr(0);
            }else{
                computer.flags[2].setValueAsInstr(0);
                if (diff < 0){
                    computer.flags[3].setValueAsInstr(1);
                }
                else{
                    computer.flags[3].setValueAsInstr(0);
                }
            }
        }
        else{
            computer.flags[0].setValueAsInstr(1);
            computer.flags[1].setValueAsInstr(1);
            computer.flags[2].setValueAsInstr(0);
             if (res == 0){
                computer.flags[3].setValueAsInstr(0);
             }else{
                computer.flags[3].setValueAsInstr(1);
             }
            
        }
        computer.PC.setValue(computer.PC.getValue()+1);
    }
    /**
     * Loads a value from memory into a destination register.
     * The source register contains the memory address. Validates that the address is within the data segment.
     * 
     * @param dest the destination register where the loaded value is stored
     * @param src the source register containing the memory address to load from
     * @throws CodeSegmentViolatedException if the address is outside the valid data segment
     */
    public void load(Register dest, Register src) throws CodeSegmentViolatedException{
        if (src.getValue() >= computer.getDS().getValue() && src.getValue() < computer.mem.getMemorySize()){
            dest.setValue(computer.mem.getMemory()[src.getValue()].getValue());
        }
        else {
             throw new CodeSegmentViolatedException();
        }
       computer.PC.setValue(computer.PC.getValue()+1);
    }
    /**
     * Stores a value from a source register into memory.
     * The first register contains the destination memory address, the second contains the value to store.
     * Validates that the address is within the data segment.
     * 
     * @param src1 the register containing the memory address where the value will be stored
     * @param src2 the register containing the value to store in memory
     * @throws CodeSegmentViolatedException if the address is outside the valid data segment
     */
    public void store(Register src1,Register src2) throws CodeSegmentViolatedException{
        if(src1.getValue() >= computer.getDS().getValue() && src1.getValue() < computer.mem.getMemorySize()){
            computer.mem.memory[src1.getValue()].setValue(src2.getValue());
            computer.PC.setValue(computer.PC.getValue()+1);
        }
        else{
            throw new CodeSegmentViolatedException();
        }
    }
    /**
     * Unconditional jump: sets the program counter to PC + n.
     * Validates that the new address is within the code segment.
     * 
     * @param n the offset to add to the current program counter
     * @throws CodeSegmentViolatedException if the resulting address is outside the valid code segment
     */
    public void jump(int n) throws CodeSegmentViolatedException{
        int newPC = computer.PC.getValue() + n;
        if ( newPC >= 0 && newPC < computer.DS.getValue()){
            computer.PC.setValue(newPC);
        }
        else{
            throw new CodeSegmentViolatedException();
        }
    }
    /**
     * No operation: advances the program counter by 1 without performing any action.
     * Used as a placeholder or for timing purposes.
     */
    public void nop(){
        computer.PC.setValue(computer.PC.getValue()+1);
    }
    /**
     * Conditional jump if zero: jumps to PC + n only if the zero flag is set (result was zero).
     * Otherwise, advances to the next instruction.
     * Validates that the new address is within the code segment.
     * 
     * @param n the offset to add to the current program counter if the zero flag is set
     * @throws CodeSegmentViolatedException if the resulting address is outside the valid code segment
     */
    public void jz(int n) throws CodeSegmentViolatedException{
        if (1 == computer.flags[2].getValue()){
            int newPC = computer.PC.getValue() + n;
            if ( newPC >= 0 && newPC < computer.DS.getValue()){
                computer.PC.setValue(newPC);
            }
            else{
                throw new CodeSegmentViolatedException();
            }
        }
        else{
            computer.PC.setValue(computer.PC.getValue()+1);
        }
    }
    /**
     * Conditional jump if not zero: jumps to PC + n only if the zero flag is clear (result was not zero).
     * Otherwise, advances to the next instruction.
     * Validates that the new address is within the code segment.
     * 
     * @param n the offset to add to the current program counter if the zero flag is clear
     * @throws CodeSegmentViolatedException if the resulting address is outside the valid code segment
     */
    public void jnz(int n) throws CodeSegmentViolatedException{
        if (0 == computer.flags[2].getValue()){
            int newPC = computer.PC.getValue() + n;
            if ( newPC >= 0 && newPC < computer.DS.getValue()){
                computer.PC.setValue(newPC);
            }
            else{
                throw new CodeSegmentViolatedException();
            }
        }
        else{
            computer.PC.setValue(computer.PC.getValue()+1);
        }
    }
    /**
     * Bitwise left shift: shifts all bits in the source register one position to the left.
     * The leftmost bit is discarded and a 0 is inserted on the right.
     * Stores the result in the destination register.
     * 
     * @param dest the destination register where the result is stored
     * @param src the source register to shift
     */
    public void shiftLeft(Register dest, Register src){
        dest.setValue(src.getValue()<<1);
        computer.PC.setValue(computer.PC.getValue()+1);
    }
    /**
     * Bitwise right shift: shifts all bits in the source register one position to the right.
     * The rightmost bit is discarded and a 0 is inserted on the left.
     * Stores the result in the destination register.
     * 
     * @param dest the destination register where the result is stored
     * @param src the source register to shift
     */
    public void shiftRight(Register dest, Register src){
        dest.setValue(src.getValue()>>1);
        computer.PC.setValue(computer.PC.getValue()+1);
    }
    /**
     * Loads a 12-bit immediate value into a register.
     * Used to load constant values directly into registers.
     * 
     * @param loadRegister the register where the immediate value is stored
     * @param n the 12-bit immediate value to load
     */
    public void load12Bit(Register loadRegister,int n){
        loadRegister.setValue(n);
        computer.PC.setValueAsInstr(computer.PC.getValue()+1);
    }
}
