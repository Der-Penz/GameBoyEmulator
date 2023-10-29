package org.penz.emulator.cpu.opcode.instructions.arithmetic.sbc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Sub register D and Carry from A
 */
@SuppressWarnings("unused")
public class SBCDInstruction extends OpCode {

    public SBCDInstruction() {
        super(0x9A, "SBC D", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SBC", DataType.d8, DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), registers.getD()));
        return cycles;
    }
}
