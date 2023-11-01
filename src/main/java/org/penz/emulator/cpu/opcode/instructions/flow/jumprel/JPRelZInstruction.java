package org.penz.emulator.cpu.opcode.instructions.flow.jumprel;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Jump relative to signed 8 bit immediate data if Z flag is set
 */
@SuppressWarnings("unused")
public class JPRelZInstruction extends OpCode {

    public JPRelZInstruction() {
        super(0x28, "JR Z, r8", 12, new DataType[]{DataType.r8});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        if (!registers.getFlags().isZ()) {
            return 8;
        }

        registers.setPC(registers.getPC() + args[0]);
        return cycles;
    }
}
