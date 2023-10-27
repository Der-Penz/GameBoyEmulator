package org.penz.emulator.cpu.opcode.instructions.load.data8;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load immediate 8 bit data into register L
 */
@SuppressWarnings("unused")
public class LoadLData8Instruction extends OpCode {

    public LoadLData8Instruction() {
        super(0x2E, "LD L, d8", 8, new DataType[]{DataType.d8});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setL(args[0]);
        return cycles;
    }
}
