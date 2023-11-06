package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos1;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 1st bit of the data pointed to by the HL register
 */
@SuppressWarnings("unused")
public class RES1HLInstruction extends BitOpCode {

    public RES1HLInstruction() {
        super(0x8E, "RES 1, (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getHL(), BitUtil.setBit(addressSpace.readByte(registers.getHL()), 1, false));
        return cycles;
    }
}