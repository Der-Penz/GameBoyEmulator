package org.penz.emulator.cpu.opcode.instructions.arithmetic.misc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Complement Carry Flag
 */
@SuppressWarnings("unused")
public class CCFInstruction extends OpCode {
    public CCFInstruction() {
        super(0x3F, "CCF", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("CCF", DataType.d8);
        aluOperation.apply(registers.getFlags(), 0x00);

        return cycles;
    }
}
