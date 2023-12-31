package org.penz.emulator.cpu.opcode.instructions.arithmetic.inc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Increment register D
 */
@SuppressWarnings("unused")
public class INCDInstruction extends OpCode {

    public INCDInstruction() {
        super(0x14, "INC D", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("INC", DataType.d8);
        registers.setD(aluOperation.apply(registers.getFlags(), registers.getD()));
        return cycles;
    }
}
