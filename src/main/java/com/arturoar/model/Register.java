
package com.arturoar.model;
import com.arturoar.tools.GFG;

/**
 * Represents a processor register with configurable bit width.
 * 
 * This class models a general-purpose register that can store signed integer values.
 * The register size (bit width) is specified at construction and determines:
 * - Maximum positive value: 2^(size-1) - 1
 * - Minimum negative value: -2^(size-1)
 * - Range of values that can be properly stored
 * 
 * For example, a 16-bit register can hold values from -32768 to 32767.
 * 
 * The register provides multiple value representations:
 * - Signed decimal: normal integer value
 * - Binary: 16-bit binary string (two's complement for negative values)
 * - Hexadecimal: base-16 representation
 * 
 * Values are clamped to the valid range, and overflow/underflow status is reported
 * during signed writes. Unsigned writes (via setValueAsInstr) bypass range checking.
 * 
 * @author arturoar
 */
public class Register {
    
    /**
     * Bit width of this register in bits.
     * Determines the range of values that can be stored and represented.
     */
    private final int size;
    /**
     * Maximum positive value that can be stored (2^(size-1) - 1).
     * For 16-bit register: 32767
     */
    private final int maxValue;
    /**
     * Minimum negative value that can be stored (-2^(size-1)).
     * For 16-bit register: -32768
     */
    private final int minValue;
    /**
     * Maximum unsigned value that can be stored as instruction (2^size).
     * Used for unsigned instruction values that bypass range checking.
     * For 16-bit register: 65536
     */
    private final int maxValueAsInst;
    /**
     * The current value stored in this register.
     */
    private int value;

    /**
     * Initializes a register with the specified bit width.
     * Calculates the valid range for signed values based on the bit width.
     * Initial value is set to 0.
     * 
     * @param size the bit width of this register (e.g., 8, 16, 32)
     */
    public Register(int size) {
        this.size = size;
        this.maxValue =(int) (Math.pow(2,size)/2) -1;
        this.minValue =(int) Math.pow(2,size)/ 2;
        this.maxValueAsInst = (int) Math.pow(2,size);
        this.value = 0;
    }

    /**
     * Retrieves the bit width of this register.
     * 
     * @return the size in bits
     */
    public int getSize() {
        return size;
    }

    /**
     * Retrieves the current value stored in this register.
     * 
     * @return the signed integer value
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Sets the register value with range checking for signed values.
     * Values exceeding the maximum positive value are clamped to maxValue.
     * Values exceeding the minimum negative value are clamped to minValue.
     * 
     * @param value the value to store in the register
     * @return 1 if value is within valid range, 0 if clamped to maximum, 2 if clamped to minimum
     */
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
    /**
     * Sets the register value without range checking (for instruction execution).
     * Used when loading raw instruction values that should not be clamped.
     * Allows values from 0 to 2^size - 1 to be stored.
     * 
     * @param value the unsigned value to store in the register
     */
    public void setValueAsInstr(int value){
        this.value = value;
    }
    
    /**
     * Retrieves the binary representation of the register's value.
     * Returns a fixed-width binary string padded with leading zeros.
     * Negative values are represented in two's complement form.
     * 
     * @return the binary string representation (size bits wide)
     */
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
    /**
     * Retrieves the hexadecimal representation of the register's value.
     * Converts the binary representation to base-16 format.
     * Padded with leading zeros to maintain consistent width.
     * 
     * @return the hexadecimal string representation
     */
    public String getHexValue(){
        String output = getBinaryValue();
        int decimal = Integer.parseInt(output,2);
        output = Integer.toHexString(decimal);
        while (output.length()<4){
            output = "0" + output;
        }
        return output;
    }
    /**
     * Retrieves the decimal representation of the register's value.
     * Returns the signed integer value as a string.
     * 
     * @return the decimal string representation
     */
    public String getDecValue(){
       return String.valueOf(this.value);
    }
    
}
