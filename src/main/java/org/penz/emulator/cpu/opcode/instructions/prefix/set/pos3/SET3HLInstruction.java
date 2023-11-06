package org.penz.emulator.cpu.opcode.instructions.prefix.set.pos3;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Set the 3rd bit of the data pointed to by the HL register
 */
@SuppressWarnings("unused")
public class SET3HLInstruction extends BitOpCode {

    public SET3HLInstruction() {
        super(0xDE, "SET 3, (HL)", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getHL(), BitUtil.setBit(addressSpace.readByte(registers.getHL()), 3, true));
        return cycles;
    }
}