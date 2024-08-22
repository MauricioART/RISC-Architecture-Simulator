/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturoar.simuladorarquitecturarisc;
import com.arturoar.herramientas.GFG;

/**
 *
 * @author arturoar
 */
public class Register {
    
    private final int size;
    private final int maxValue;
    private final int minValue;
    private final int maxValueAsInst;
    private int value;

    public Register(int size) {
        this.size = size;
        this.maxValue =(int) (Math.pow(2,size)/2) -1;
        this.minValue =(int) Math.pow(2,size)/ 2;
        this.maxValueAsInst = (int) Math.pow(2,size);
        this.value = 0;
    }

    public int getSize() {
        return size;
    }

    public int getValue() {
        return value;
    }
    
    public int setValue(int value) {
        if (value >= 0){
            if (value > this.maxValue){
                this.value = this.maxValue;
                return 0;
            }   
        }else{
            if (value < this.minValue){
                this.value = this.minValue;
                return 2;
            }
        }
        this.value = value;
        return 1;
    }
    public void setValueAsInstr(int value){
        this.value = value;
    }
    
    public String getBinaryValue(){
        
        int unsignedValue = Math.abs(this.value);
        String binaryValue = Integer.toBinaryString(unsignedValue);
        while (binaryValue.length() < this.size){
            binaryValue = "0" + binaryValue;
        }
        if (this.value >= 0)
            return binaryValue;
        else
            return GFG.getTwosComplement(binaryValue);
    }
    public String getHexValue(){
        String output = getBinaryValue();
        int decimal = Integer.parseInt(output,2);
        output = Integer.toHexString(decimal);
        while (output.length()<4){
            output = "0" + output;
        }
        return output;
    }
    public String getDecValue(){
       return String.valueOf(this.value);
    }
    
}
