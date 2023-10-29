package org.penz.emulator.cpu.opcode.instructions.arithmetic.add;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Add register L and A
 */
@SuppressWarnings("unused")
public class ADDLInstruction extends OpCode {

    public ADDLInstruction() {
        super(0x85, "ADD L", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("ADD", DataType.d8, DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), registers.getL()));
        return cycles;
    }
}
