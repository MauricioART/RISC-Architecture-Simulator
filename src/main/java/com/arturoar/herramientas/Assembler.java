/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturoar.herramientas;

import com.arturoar.excepciones.WarningException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author arturoar
 */
public class Assembler {
    
    private File archivo;
    private ArrayList<String> instrAltoNivel;

    public Assembler(String direccion) {
        this.archivo = new File(direccion);
        this.instrAltoNivel = new ArrayList<>();
    }

    public Assembler(File archivo) {
        this.archivo = archivo;
        this.instrAltoNivel = new ArrayList<>();
    }
    
    public void assemble(ArrayList<String> binaryCode) throws FileNotFoundException, IOException, WarningException{
        BufferedReader br = new BufferedReader(new FileReader(this.archivo));
        String buffer;
        while((buffer = br.readLine()) != null){
            this.instrAltoNivel.add(buffer);
            binaryCode.add(this.decodeInstruction(buffer));
        }
        br.close();
    }
    
    public String decodeInstruction(String instr) throws WarningException{
        StringTokenizer stkr = new StringTokenizer(instr," ");
        String inst = getOpn(stkr.nextToken());
        switch(stkr.countTokens()){
            case 1:
                String numStr = stkr.nextToken();
                if(inst != "1110"){
                    inst = inst + "0000" + "" + getBinaryNumber(numStr,8);
                }
                else {
                    inst = inst + getBinaryNumber(numStr,12);
                }
               
                break;
            case 2:
                if (inst == "1001" || inst == "1010"){
                    inst = inst + "000";
                    inst = inst + getRgr(stkr.nextToken());
                    inst = inst + getRgr(stkr.nextToken());
                    inst = inst + "000";
                }
                else {
                    inst = inst + getRgr(stkr.nextToken());
                    inst = inst + getRgr(stkr.nextToken());
                    inst = inst + "000000";
                }
                
                break;
            case 3:
                inst = inst + getRgr(stkr.nextToken());
                inst = inst + getRgr(stkr.nextToken());
                inst = inst + getRgr(stkr.nextToken());
                inst = inst + "000";
                break;
            default:
                System.out.println("Error");
                break;
        }
        return inst;
    }
    
    public static String getRgr(String rgr){
        switch (rgr){
            case "Ax":
                rgr = "000";
                break;
            case "Bx":
                rgr = "001";
                break;
            case "Cx":
                rgr = "010";
                break;
            case "Dx":
                rgr = "011";
                break;
            case "Ex":
                rgr = "100";
                break;            
            case "Fx":
                rgr = "101";
                break;
            case "Gx":
                rgr = "110";
                break;
            case "Hx":
                rgr = "111";
                break;
        }
        return rgr;
    }

    public String getOpn(String opn){
        String binaryForm = "";
        switch (opn)
        {
            case "ADD":
                binaryForm = "0000";
                break;
            case "SUB":
                binaryForm = "0001";
                break;
            case "AND":
                binaryForm = "0010";
                break;
            case "OR":
                binaryForm = "0011";
                break;
            case "XOR":
                binaryForm = "0100";
                break;
            case "NOT":
                binaryForm = "0101";
                break;
            case "SHL":
                binaryForm = "0110";
                break;
            case "SHR":
                binaryForm = "0111";
                break;
            case "LOAD":
                binaryForm = "1000";
                break;
            case "CMP":
                binaryForm = "1001";
                break;
            case "STORE":
                binaryForm = "1010";
                break;
            case "JUMP":
                binaryForm = "1011";
                break;
            case "JZ":
                binaryForm = "1100";
                break;
            case "JNZ":
                binaryForm = "1101";
                break;
            case "LOADI":
                binaryForm = "1110";
                break;
            case "NOP":
                binaryForm = "1111";
                break;
            default:
                System.out.println("Error");
        }
        return binaryForm;
    }
    
    public String getBinaryNumber(String numStr,int length) throws WarningException{
       
        String binaryNumber = "";
        if (numStr.startsWith("-")){
                String unsNumStr = numStr.substring(1);
                int num = Integer.parseInt(unsNumStr);
                int max =(int) Math.pow(2, length)/2;
                if ( num <= max){
                    binaryNumber = Integer.toBinaryString(num);
                    while(binaryNumber.length() < length){
                        binaryNumber = "0" + binaryNumber;
                    }
                    binaryNumber = GFG.getTwosComplement(binaryNumber);
                        
                }else
                        throw new WarningException();
        }else {
            int num = Integer.parseInt(numStr);
            int max =(int) Math.pow(2, length)/2 -1;
                if ( num <= max){
                    binaryNumber =  Integer.toBinaryString(num);
                    while(binaryNumber.length() < length){
                        binaryNumber = "0" + binaryNumber;
                    }
                }else
                    throw new WarningException();
        }
        return binaryNumber;
    }

    public ArrayList<String> getInstrAltoNivel() {
        return instrAltoNivel;
    }
    
}
