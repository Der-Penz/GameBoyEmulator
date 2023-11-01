package org.penz.emulator.cpu.opcode.instructions.flow.jump;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Jump to immediate address
 */
@SuppressWarnings("unused")
public class JPAddr16Instruction extends OpCode {

    public JPAddr16Instruction() {
        super(0xC3, "JR a16", 16, new DataType[]{DataType.a16});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setPC(args[0]);

        return cycles;
    }
}
