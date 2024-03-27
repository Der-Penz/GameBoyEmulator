package org.penz.emulator.cpu.opcode.instructions.rotate;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register A right. Old bit 0 to Carry flag and bit 7.
 */
@SuppressWarnings("unused")
public class RRCA0Instruction extends OpCode {

    public RRCA0Instruction() {
        super(0x0F, "RRCA", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RRC", DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA()));

        registers.getFlags().setZ(false);
        return cycles;
    }
}