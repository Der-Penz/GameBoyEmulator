package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos0;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 0th bit of the data pointed to by the HL register
 */
@SuppressWarnings("unused")
public class RES0HLInstruction extends BitOpCode {

    public RES0HLInstruction() {
        super(0x86, "RES 0, (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getHL(), BitUtil.setBit(addressSpace.readByte(registers.getHL()), 0, false));
        return cycles;
    }
}