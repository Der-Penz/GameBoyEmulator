package org.penz.emulator.cpu.opcode.instructions.prefix.rotate.cright;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register C right. Old bit 0 to Carry flag and bit 7.
 */
@SuppressWarnings("unused")
public class RRCCInstruction extends BitOpCode {

    public RRCCInstruction() {
        super(0x09, "RRC C", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RRC", DataType.d8);
        registers.setC(aluOperation.apply(registers.getFlags(), registers.getC()));

        return cycles;
    }
}