/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorarquitectura;

import java.util.ArrayList;

/**
 *
 * @author arturoar
 */
public class Instruction {
    
    private int operation;
    private int destRgr;
    private int srcRgr1;
    private int srcRgr2;
    private int num;

    public int getOperation() {
        return operation;
    }

    public int getDestRgr() {
        return destRgr;
    }

    public int getSrcRgr1() {
        return srcRgr1;
    }

    public int getSrcRgr2() {
        return srcRgr2;
    }

    public int getNum() {
        return num;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public void setDestRgr(int destRgr) {
        this.destRgr = destRgr;
    }

    public void setSrcRgr1(int srcRgr1) {
        this.srcRgr1 = srcRgr1;
    }

    public void setSrcRgr2(int srcRgr2) {
        this.srcRgr2 = srcRgr2;
    }

    public void setNum(int num) {
        this.num = num;
    }

    
    
}
