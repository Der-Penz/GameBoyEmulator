package org.penz.emulator.cpu.opcode.instructions.arithmetic.data16;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Decrement register BC
 */
@SuppressWarnings("unused")
public class DECBCInstruction extends OpCode {

    public DECBCInstruction() {
        super(0x0B, "DEC BC", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("DEC", DataType.d16);
        registers.setBC(aluOperation.apply(registers.getFlags(), registers.getDE()));
        return cycles;
    }
}
