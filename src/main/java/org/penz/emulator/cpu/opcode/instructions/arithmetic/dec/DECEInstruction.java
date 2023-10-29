package org.penz.emulator.cpu.opcode.instructions.arithmetic.dec;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Decrement register E
 */
@SuppressWarnings("unused")
public class DECEInstruction extends OpCode {

    public DECEInstruction() {
        super(0x1D, "DEC E", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("DEC", DataType.d8);
        registers.setE(aluOperation.apply(registers.getFlags(), registers.getE()));
        return cycles;
    }
}
