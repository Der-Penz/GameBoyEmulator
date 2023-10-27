package org.penz.emulator.cpu.opcode.instructions.load.data16;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load 16 bit immediate value to register BC
 */
@SuppressWarnings("unused")
public class LoadBCData16Instruction extends OpCode {

    public LoadBCData16Instruction() {
        super(0x01, "LD BC, d16", 12, new DataType[]{DataType.d16});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setBC(args[0]);
        return cycles;
    }
}
