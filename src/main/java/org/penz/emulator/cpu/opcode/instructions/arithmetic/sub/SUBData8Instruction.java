package org.penz.emulator.cpu.opcode.instructions.arithmetic.sub;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Subtract 8 bit immediate data from A
 */
@SuppressWarnings("unused")
public class SUBData8Instruction extends OpCode {

    public SUBData8Instruction() {
        super(0xD6, "SUB d8", 8, new DataType[]{DataType.d8});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SUB", DataType.d8, DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), args[0]));
        return cycles;
    }
}
