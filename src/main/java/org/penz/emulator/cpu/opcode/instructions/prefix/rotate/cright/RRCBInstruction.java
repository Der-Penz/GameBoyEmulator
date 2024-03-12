package org.penz.emulator.cpu.opcode.instructions.prefix.rotate.cright;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register B right. Old bit 0 to Carry flag and bit 7.
 */
@SuppressWarnings("unused")
public class RRCBInstruction extends BitOpCode {

    public RRCBInstruction() {
        super(0x08, "RRC B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RRC", DataType.d8);
        registers.setB(aluOperation.apply(registers.getFlags(), registers.getB()));

        return cycles;
    }
}