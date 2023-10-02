package org.penz.emulator.cpu.opcode.instructions.load;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Copy 16 bit immediate value to stack pointer (SP)
 */
public class LoadSPWordInstruction extends OpCode {

    public LoadSPWordInstruction() {
        super(0x31, "LD SP, D16", 12, new DataType[]{DataType.d16});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setSp(args[0]);
        return cycles;
    }
}
