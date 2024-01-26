package org.penz.emulator.cpu.opcode.instructions.load.misc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load into register A the content of the internal RAM, port register, or mode register at the address in the range 0xFF00 to 0xFFFF specified by the 8 bit immediate address.
 * Example: When a8 = 0x9F
 * LD A, (a8) : A <- contents of (0xFF9F)
 */
@SuppressWarnings("unused")
public class LDHARamInstruction extends OpCode {

    public LDHARamInstruction() {
        super(0xF0, "LDH A, (a8)", 12, new DataType[]{DataType.d8});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(addressSpace.readByte(0xFF00 + args[0]));
        return cycles;
    }
}
