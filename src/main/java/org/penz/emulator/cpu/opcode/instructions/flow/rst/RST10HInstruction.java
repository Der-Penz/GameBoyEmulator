package org.penz.emulator.cpu.opcode.instructions.flow.rst;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Restart at address 0x10
 */
@SuppressWarnings("unused")
public class RST10HInstruction extends OpCode {
    public RST10HInstruction() {
        super(0xD7, "RST 10H", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getSPSafe(-1), BitUtil.getMSByte(registers.getPC()));
        addressSpace.writeByte(registers.getSPSafe(-2), BitUtil.getLSByte(registers.getPC()));

        registers.setSP(registers.getSPSafe(-2));
        registers.setPC(0x10);

        return cycles;
    }
}
