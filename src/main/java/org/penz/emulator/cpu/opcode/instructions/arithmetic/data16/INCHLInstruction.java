package org.penz.emulator.cpu.opcode.instructions.arithmetic.data16;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Increment register HL
 */
@SuppressWarnings("unused")
public class INCHLInstruction extends OpCode {

    public INCHLInstruction() {
        super(0x23, "INC HL", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("INC", DataType.d16);
        registers.setHL(aluOperation.apply(registers.getFlags(), registers.getHL()));
        return cycles;
    }
}
