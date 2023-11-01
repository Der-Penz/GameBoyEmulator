package org.penz.emulator.cpu.opcode.instructions.flow.jump;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Jump to immediate address if C flag is set
 */
@SuppressWarnings("unused")
public class JPCAddr16Instruction extends OpCode {

    public JPCAddr16Instruction() {
        super(0xDA, "JR C, a16", 16, new DataType[]{DataType.a16});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        if (!registers.getFlags().isC()) {
            return 12;
        }

        registers.setPC(args[0]);

        return cycles;
    }
}
