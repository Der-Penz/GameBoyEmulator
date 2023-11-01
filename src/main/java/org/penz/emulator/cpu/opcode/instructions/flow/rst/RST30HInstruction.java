package org.penz.emulator.cpu.opcode.instructions.flow.rst;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Restart at address 0x30
 */
@SuppressWarnings("unused")
public class RST30HInstruction extends OpCode {
    public RST30HInstruction() {
        super(0xF7, "RST 30H", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getSP() - 1, BitUtil.getMSByte(registers.getPC()));
        addressSpace.writeByte(registers.getSP() - 2, BitUtil.getLSByte(registers.getPC()));

        registers.setSP(registers.getSP() - 2);
        registers.setPC(0x30);

        return cycles;
    }
}
