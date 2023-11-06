package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos0;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 0th bit of the data pointed to by the HL register
 */
@SuppressWarnings("unused")
public class SET0HLInstruction extends BitOpCode {

    public SET0HLInstruction() {
        super(0xC6, "SET 0, (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getHL(), BitUtil.setBit(addressSpace.readByte(registers.getHL()), 0, true));
        return cycles;
    }
}