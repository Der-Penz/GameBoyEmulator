package org.penz.emulator.cpu.opcode.instructions.flow.jumprel;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Jump relative to signed 8 bit immediate data
 */
@SuppressWarnings("unused")
public class JPRelInstruction extends OpCode {

    public JPRelInstruction() {
        super(0x18, "JR r8", 12, new DataType[]{DataType.r8});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setPC(registers.getPC() + args[0]);

        return cycles;
    }
}
