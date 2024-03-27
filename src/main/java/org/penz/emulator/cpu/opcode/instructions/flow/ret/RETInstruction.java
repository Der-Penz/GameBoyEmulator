package org.penz.emulator.cpu.opcode.instructions.flow.ret;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Return from Subroutine
 */
@SuppressWarnings("unused")
public class RETInstruction extends OpCode {
    public RETInstruction() {
        super(0xC9, "RET", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setPC(addressSpace.readByte(registers.getSP()));
        registers.setPC(BitUtil.toWord(addressSpace.readByte(registers.getSPSafe(1)), registers.getPC()));
        registers.setSP(registers.getSPSafe(2));

        return cycles;
    }
}
