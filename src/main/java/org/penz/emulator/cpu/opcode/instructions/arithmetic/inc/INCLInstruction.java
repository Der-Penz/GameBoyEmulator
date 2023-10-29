package org.penz.emulator.cpu.opcode.instructions.arithmetic.inc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Increment register L
 */
@SuppressWarnings("unused")
public class INCLInstruction extends OpCode {

    public INCLInstruction() {
        super(0x2C, "INC L", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("INC", DataType.d8);
        registers.setL(aluOperation.apply(registers.getFlags(), registers.getL()));
        return cycles;
    }
}
