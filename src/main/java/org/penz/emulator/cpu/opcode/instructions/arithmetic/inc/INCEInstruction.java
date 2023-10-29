package org.penz.emulator.cpu.opcode.instructions.arithmetic.inc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Increment register E
 */
@SuppressWarnings("unused")
public class INCEInstruction extends OpCode {

    public INCEInstruction() {
        super(0x1C, "INC E", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("INC", DataType.d8);
        registers.setE(aluOperation.apply(registers.getFlags(), registers.getE()));
        return cycles;
    }
}
