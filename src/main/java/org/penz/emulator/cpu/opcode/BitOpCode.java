package org.penz.emulator.cpu.opcode;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.memory.AddressSpace;

public abstract class BitOpCode extends OpCode {

    public BitOpCode(int opcode, String name, int cycles) {
        super(opcode, name, cycles);
    }

    public abstract int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args);

    @Override
    public boolean isBitInstruction() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("OpCode: CB %s 0x%02X: %d", this.name, this.opcode, this.cycles);
    }

}
