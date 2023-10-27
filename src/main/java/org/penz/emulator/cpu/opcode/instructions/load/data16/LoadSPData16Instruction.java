package org.penz.emulator.cpu.opcode.instructions.load.data16;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load 16 bit immediate value to stack pointer (SP)
 */
@SuppressWarnings("unused")
public class LoadSPData16Instruction extends OpCode {

    public LoadSPData16Instruction() {
        super(0x31, "LD SP, d16", 12, new DataType[]{DataType.d16});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setSP(args[0]);
        return cycles;
    }
}
