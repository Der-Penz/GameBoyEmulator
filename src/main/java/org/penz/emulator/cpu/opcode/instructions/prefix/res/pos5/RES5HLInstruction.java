package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos5;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 5th bit of the data pointed to by the HL register
 */
@SuppressWarnings("unused")
public class RES5HLInstruction extends BitOpCode {

    public RES5HLInstruction() {
        super(0xAE, "RES 5, (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getHL(), BitUtil.setBit(addressSpace.readByte(registers.getHL()), 5, false));
        return cycles;
    }
}