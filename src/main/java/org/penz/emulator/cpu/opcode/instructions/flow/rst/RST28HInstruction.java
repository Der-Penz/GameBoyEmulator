package org.penz.emulator.cpu.opcode.instructions.flow.rst;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Restart at address 0x28
 */
@SuppressWarnings("unused")
public class RST28HInstruction extends OpCode {
    public RST28HInstruction() {
        super(0xEF, "RST 28H", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getSP() - 1, BitUtil.getMSByte(registers.getPC()));
        addressSpace.writeByte(registers.getSP() - 2, BitUtil.getLSByte(registers.getPC()));

        registers.setSP(registers.getSP() - 2);
        registers.setPC(0x28);

        return cycles;
    }
}
