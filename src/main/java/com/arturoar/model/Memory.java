
package com.arturoar.model;

/**
 * Memory management system for the RISC architecture simulator.
 * 
 * This class represents the computer's main memory, consisting of addressable storage cells.
 * Each memory cell is a 16-bit register that can store instruction or data values.
 * 
 * Memory capacity is determined by the address bus width:
 * - Address bus width determines the number of addressable locations
 * - Memory size = 2^(address bus width)
 * - For example: 8-bit address bus = 256 memory locations
 * 
 * The memory is addressed by the program counter (PC) during instruction fetching
 * and by the load/store instructions during data access.
 * 
 * @author arturoar
 */
public class Memory {
    
    /**
     * Address bus width in bits. Determines the maximum number of addressable memory locations.
     * Memory size = 2^ADRESS_BUS_SIZE
     */
    private final int ADRESS_BUS_SIZE;
    /**
     * Total number of memory locations (bytes/cells) available.
     * Calculated as 2^ADRESS_BUS_SIZE.
     */
    private final int memorySize;
    /**
     * Array of 16-bit registers representing individual memory cells.
     * Each cell can store instruction or data values.
     */
    protected Register[] memory;

    /**
     * Initializes the memory system with specified address bus width.
     * 
     * Allocates memory cells based on the address bus width:
     * - Total cells = 2^addressBusWidth
     * - Each cell is a 16-bit register for storing instructions or data
     * 
     * @param addressBusWidth the width of the address bus in bits (determines memory size)
     */
    public Memory(int addressBusWidth) {
        this.ADRESS_BUS_SIZE = addressBusWidth;
        this.memorySize = (int) Math.pow(2, addressBusWidth);
        this.memory = new Register[this.memorySize];
        for (int i = 0; i < this.memorySize; i++){
            this.memory[i] = new Register(16);
        }
    }

    /**
     * Retrieves the total number of memory locations available.
     * 
     * @return the memory size (2^ADRESS_BUS_SIZE)
     */
    public int getMemorySize() {
        return memorySize;
    }

    /**
     * Retrieves the array of memory cells.
     * 
     * @return the array of 16-bit registers representing memory cells
     */
    public Register[] getMemory() {
        return memory;
    }
    
    
}
