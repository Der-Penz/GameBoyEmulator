package org.penz.emulator.cpu.opcode.instructions.arithmetic.add;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Add register E and A
 */
@SuppressWarnings("unused")
public class ADDEInstruction extends OpCode {

    public ADDEInstruction() {
        super(0x83, "ADD E", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("ADD", DataType.d8, DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), registers.getE()));
        return cycles;
    }
}
