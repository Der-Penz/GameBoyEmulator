package org.penz.emulator.cpu.opcode.instructions.load.misc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load the value of A into the internal RAM, port register, or mode register at the address in the range 0xFF00 to 0xFFFF specified by register C.
 * Example: When C = 0x9F
 * LD (C), A : (0xFF9F) <- A
 */
@SuppressWarnings("unused")
public class LDCRamAInstruction extends OpCode {

    public LDCRamAInstruction() {
        super(0xE2, "LD (C), A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(0xFF00 + registers.getC(), registers.getA());
        return cycles;
    }
}
