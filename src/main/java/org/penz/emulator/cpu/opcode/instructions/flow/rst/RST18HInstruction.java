package org.penz.emulator.cpu.opcode.instructions.flow.rst;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Restart at address 0x18
 */
@SuppressWarnings("unused")
public class RST18HInstruction extends OpCode {
    public RST18HInstruction() {
        super(0xDF, "RST 18H", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getSP() - 1, BitUtil.getMSByte(registers.getPC()));
        addressSpace.writeByte(registers.getSP() - 2, BitUtil.getLSByte(registers.getPC()));

        registers.setSP(registers.getSP() - 2);
        registers.setPC(0x18);

        return cycles;
    }
}
