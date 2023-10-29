package org.penz.emulator.cpu.opcode.instructions.arithmetic.dec;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Decrement register A
 */
@SuppressWarnings("unused")
public class DECAInstruction extends OpCode {

    public DECAInstruction() {
        super(0x3D, "DEC A", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("DEC", DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA()));
        return cycles;
    }
}
