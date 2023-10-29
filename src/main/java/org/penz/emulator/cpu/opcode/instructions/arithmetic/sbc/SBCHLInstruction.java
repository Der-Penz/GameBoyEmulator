package org.penz.emulator.cpu.opcode.instructions.arithmetic.sbc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Subtract memory located at the position register HL points to and Carry from A
 */
@SuppressWarnings("unused")
public class SBCHLInstruction extends OpCode {

    public SBCHLInstruction() {
        super(0x9E, "SBC (HL)", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SBC", DataType.d8, DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), addressSpace.readByte(registers.getHL())));
        return cycles;
    }
}
