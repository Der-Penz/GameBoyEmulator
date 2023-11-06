package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos4;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 4th bit of the data pointed to by the HL register
 */
@SuppressWarnings("unused")
public class RES4HLInstruction extends BitOpCode {

    public RES4HLInstruction() {
        super(0xA6, "RES 4, (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getHL(), BitUtil.setBit(addressSpace.readByte(registers.getHL()), 4, false));
        return cycles;
    }
}