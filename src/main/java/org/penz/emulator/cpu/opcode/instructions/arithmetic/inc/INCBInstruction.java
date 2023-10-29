package org.penz.emulator.cpu.opcode.instructions.arithmetic.inc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Increment register B
 */
@SuppressWarnings("unused")
public class INCBInstruction extends OpCode {

    public INCBInstruction() {
        super(0x04, "INC B", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("INC", DataType.d8);
        registers.setB(aluOperation.apply(registers.getFlags(), registers.getB()));
        return cycles;
    }
}
