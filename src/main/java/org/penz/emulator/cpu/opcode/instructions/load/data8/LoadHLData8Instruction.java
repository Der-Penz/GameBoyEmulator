package org.penz.emulator.cpu.opcode.instructions.load.data8;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load immediate 8 bit data into the memory location pointed to by register HL
 */
@SuppressWarnings("unused")
public class LoadHLData8Instruction extends OpCode {

    public LoadHLData8Instruction() {
        super(0x36, "LD (HL), d8", 8, new DataType[]{DataType.d8});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getHL(), args[0]);
        return cycles;
    }
}
