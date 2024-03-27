package org.penz.emulator.cpu.opcode.instructions.prefix.rotate.cright;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register A right. Old bit 0 to Carry flag and bit 7.
 */
@SuppressWarnings("unused")
public class RRCAInstruction extends BitOpCode {

    public RRCAInstruction() {
        super(0x0F, "RRC A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RRC", DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA()));

        return cycles;
    }
}