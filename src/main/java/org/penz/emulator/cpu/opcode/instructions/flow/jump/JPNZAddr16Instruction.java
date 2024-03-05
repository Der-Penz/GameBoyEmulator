package org.penz.emulator.cpu.opcode.instructions.flow.jump;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Jump to immediate address if Z flag is not set
 */
@SuppressWarnings("unused")
public class JPNZAddr16Instruction extends OpCode {

    public JPNZAddr16Instruction() {
        super(0xC2, "JP NZ, a16", 16, new DataType[]{DataType.a16});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        if (registers.getFlags().isZ()) {
            return 12;
        }

        registers.setPC(args[0]);

        return cycles;
    }
}
