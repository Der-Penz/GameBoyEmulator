package org.penz.emulator.cpu;

import org.penz.emulator.memory.AddressSpace;

public class Cpu {

    /**
     * Cpu Registers and Flags
     */
    private final Registers registers;

    private final AddressSpace memory;

    public Cpu(AddressSpace memory) {
        this.registers = new Registers();
        this.memory = memory;
    }

    public Registers getRegisters() {
        return registers;
    }
}
