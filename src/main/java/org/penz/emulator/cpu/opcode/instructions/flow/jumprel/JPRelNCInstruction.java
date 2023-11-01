package org.penz.emulator.cpu.opcode.instructions.flow.jumprel;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Jump relative to signed 8 bit immediate data if C flag is not set
 */
@SuppressWarnings("unused")
public class JPRelNCInstruction extends OpCode {

    public JPRelNCInstruction() {
        super(0x30, "JR NC, r8", 12, new DataType[]{DataType.r8});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        if (registers.getFlags().isC()) {
            return 8;
        }

        registers.setPC(registers.getPC() + args[0]);
        return cycles;
    }
}
