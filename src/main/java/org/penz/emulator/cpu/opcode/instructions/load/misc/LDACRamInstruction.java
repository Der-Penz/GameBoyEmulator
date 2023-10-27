package org.penz.emulator.cpu.opcode.instructions.load.misc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load into register A the content of the internal RAM, port register, or mode register at the address in the range 0xFF00 to 0xFFFF specified by register C.
 * Example: When C = 0x9F
 * LD A, (C) : A <- contents of (0xFF9F)
 */
@SuppressWarnings("unused")
public class LDACRamInstruction extends OpCode {

    public LDACRamInstruction() {
        super(0xF2, "LD A, (C)", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(addressSpace.readByte(0xFF00 + registers.getC()));
        return cycles;
    }
}
