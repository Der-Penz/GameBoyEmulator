package org.penz.emulator.cpu.opcode.instructions.arithmetic.sub;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Subtract register H from A
 */
@SuppressWarnings("unused")
public class SUBHInstruction extends OpCode {

    public SUBHInstruction() {
        super(0x94, "SUB H", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SUB", DataType.d8, DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), registers.getH()));
        return cycles;
    }
}
