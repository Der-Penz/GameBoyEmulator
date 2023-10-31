package org.penz.emulator.cpu.opcode.instructions.flow.ret;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Return from Subroutine if C flag is not set
 */
@SuppressWarnings("unused")
public class RETNCInstruction extends OpCode {
    public RETNCInstruction() {
        super(0xD0, "RET NC", 20);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        if (registers.getFlags().isC()) {
            return 8;
        }

        registers.setPC(addressSpace.readByte(registers.getSP()));
        registers.setPC(BitUtil.toWord(addressSpace.readByte(registers.getSP() + 1), registers.getPC()));
        registers.setSP(registers.getSP() + 2);

        return cycles;
    }
}
