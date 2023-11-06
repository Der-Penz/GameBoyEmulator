package org.penz.emulator.cpu.opcode.instructions.prefix.res.pos6;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Reset the 6th bit of the data pointed to by the HL register
 */
@SuppressWarnings("unused")
public class RES6HLInstruction extends BitOpCode {

    public RES6HLInstruction() {
        super(0xB6, "RES 6, (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getHL(), BitUtil.setBit(addressSpace.readByte(registers.getHL()), 6, false));
        return cycles;
    }
}