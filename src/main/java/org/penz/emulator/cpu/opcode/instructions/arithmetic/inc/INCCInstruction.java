package org.penz.emulator.cpu.opcode.instructions.arithmetic.inc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Increment register C
 */
@SuppressWarnings("unused")
public class INCCInstruction extends OpCode {

    public INCCInstruction() {
        super(0x0C, "INC C", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("INC", DataType.d8);
        registers.setC(aluOperation.apply(registers.getFlags(), registers.getC()));
        return cycles;
    }
}
