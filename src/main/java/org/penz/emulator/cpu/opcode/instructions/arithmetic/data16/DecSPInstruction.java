package org.penz.emulator.cpu.opcode.instructions.arithmetic.data16;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Decrement register SP
 */
@SuppressWarnings("unused")
public class DecSPInstruction extends OpCode {

    public DecSPInstruction() {
        super(0x3B, "DEC SP", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("DEC", DataType.d16);
        registers.setSP(aluOperation.apply(registers.getFlags(), registers.getSP()));
        return cycles;
    }
}
