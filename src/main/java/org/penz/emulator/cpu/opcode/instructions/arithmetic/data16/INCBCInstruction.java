package org.penz.emulator.cpu.opcode.instructions.arithmetic.data16;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Increment register BC
 */
@SuppressWarnings("unused")
public class INCBCInstruction extends OpCode {

    public INCBCInstruction() {
        super(0x03, "INC BC", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("INC", DataType.d16);
        registers.setBC(aluOperation.apply(registers.getFlags(), registers.getDE()));
        return cycles;
    }
}
