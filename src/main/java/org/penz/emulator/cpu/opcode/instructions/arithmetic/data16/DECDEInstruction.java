package org.penz.emulator.cpu.opcode.instructions.arithmetic.data16;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Decrement register DE
 */
@SuppressWarnings("unused")
public class DECDEInstruction extends OpCode {

    public DECDEInstruction() {
        super(0x1D, "DEC DE", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("DEC", DataType.d16);
        registers.setDE(aluOperation.apply(registers.getFlags(), registers.getDE()));
        return cycles;
    }
}
