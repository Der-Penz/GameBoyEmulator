package org.penz.emulator.cpu.opcode.instructions.load.a;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load value pointed by 16 bit immediate address into register A
 */
@SuppressWarnings("unused")
public class LoadAAdrInstruction extends OpCode {

    public LoadAAdrInstruction() {
        super(0xFA, "LD A, (a16)", 16, new DataType[]{DataType.a16});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(addressSpace.readByte(args[0]));
        return cycles;
    }
}
