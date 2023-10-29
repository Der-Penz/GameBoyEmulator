package org.penz.emulator.cpu.opcode.instructions.arithmetic.sbc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Sub register A and Carry from A
 */
@SuppressWarnings("unused")
public class SBCAInstruction extends OpCode {

    public SBCAInstruction() {
        super(0x9F, "SBC A", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SBC", DataType.d8, DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), registers.getA()));
        return cycles;
    }
}
