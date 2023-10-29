package org.penz.emulator.cpu.opcode.instructions.arithmetic.sub;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Subtract register D from A
 */
@SuppressWarnings("unused")
public class SUBDInstruction extends OpCode {

    public SUBDInstruction() {
        super(0x92, "SUB D", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SUB", DataType.d8, DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), registers.getD()));
        return cycles;
    }
}
