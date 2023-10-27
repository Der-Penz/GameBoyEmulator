package org.penz.emulator.cpu.opcode.instructions.load.data8;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load immediate 8 bit data into register D
 */
@SuppressWarnings("unused")
public class LoadDData8Instruction extends OpCode {

    public LoadDData8Instruction() {
        super(0x16, "LD D, d8", 8, new DataType[]{DataType.d8});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(args[0]);
        return cycles;
    }
}
