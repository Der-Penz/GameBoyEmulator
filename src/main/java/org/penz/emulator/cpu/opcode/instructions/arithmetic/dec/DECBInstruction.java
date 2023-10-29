package org.penz.emulator.cpu.opcode.instructions.arithmetic.dec;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Decrement register B
 */
@SuppressWarnings("unused")
public class DECBInstruction extends OpCode {

    public DECBInstruction() {
        super(0x05, "DEC B", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("DEC", DataType.d8);
        registers.setB(aluOperation.apply(registers.getFlags(), registers.getB()));
        return cycles;
    }
}
