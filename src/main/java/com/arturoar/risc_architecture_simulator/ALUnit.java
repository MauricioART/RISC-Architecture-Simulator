/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturoar.risc_architecture_simulator;

import com.arturoar.exceptions.CodeSegmentViolatedException;

/**
 *
 * @author arturoar
 */
public class ALUnit {

    private final Computer computer;
    
    public ALUnit(Computer computer) {
        this.computer = computer;
    }
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
    public void load(Register dest, Register src) throws CodeSegmentViolatedException{
        if (src.getValue() >= computer.getDS().getValue() && src.getValue() < computer.mem.getMemorySize()){
            dest.setValue(computer.mem.getMemory()[src.getValue()].getValue());
        }
        else {
             throw new CodeSegmentViolatedException();
        }
       computer.PC.setValue(computer.PC.getValue()+1);
    }
    public void store(Register src1,Register src2) throws CodeSegmentViolatedException{
        if(src1.getValue() >= computer.getDS().getValue() && src1.getValue() < computer.mem.getMemorySize()){
            computer.mem.memory[src1.getValue()].setValue(src2.getValue());
            computer.PC.setValue(computer.PC.getValue()+1);
        }
        else{
            throw new CodeSegmentViolatedException();
        }
    }
    public void jump(int n) throws CodeSegmentViolatedException{
        int newPC = computer.PC.getValue() + n;
        if ( newPC >= 0 && newPC < computer.DS.getValue()){
            computer.PC.setValue(newPC);
        }
        else{
            throw new CodeSegmentViolatedException();
        }
    }
    public void nop(){
        computer.PC.setValue(computer.PC.getValue()+1);
    }
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
    public void shiftLeft(Register dest, Register src){
        dest.setValue(src.getValue()<<1);
        computer.PC.setValue(computer.PC.getValue()+1);
    }
    public void shiftRight(Register dest, Register src){
        dest.setValue(src.getValue()>>1);
        computer.PC.setValue(computer.PC.getValue()+1);
    }
    public void load12Bit(Register loadRegister,int n){
        loadRegister.setValue(n);
        computer.PC.setValueAsInstr(computer.PC.getValue()+1);
    }
}
