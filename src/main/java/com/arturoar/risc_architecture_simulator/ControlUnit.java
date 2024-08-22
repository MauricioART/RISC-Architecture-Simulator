/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturoar.risc_architecture_simulator;

import com.arturoar.exceptions.CodeSegmentViolatedException;
import com.arturoar.tools.GFG;

/**
 *
 * @author arturoar
 */
public class ControlUnit {
    
    private  Computer comp;
    private  Instruction currInstruction;
    public ControlUnit(Computer comp) {
        this.currInstruction = new Instruction();
        this.comp = comp;
    }
    
    
    public void fetch(){
        comp.IR.setValueAsInstr(comp.mem.memory[comp.PC.getValue()].getValue());
    }
    
    public void decode(){
        this.currInstruction.setOperation(Integer.parseInt(comp.IR.getBinaryValue().substring(0, 4),2));
    }
    
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
