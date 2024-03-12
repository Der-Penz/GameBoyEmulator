package org.penz.emulator.cpu.opcode.instructions.prefix.rotate.cright;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register L right. Old bit 0 to Carry flag and bit 7.
 */
@SuppressWarnings("unused")
public class RRCLInstruction extends BitOpCode {

    public RRCLInstruction() {
        super(0x0D, "RRC L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RRC", DataType.d8);
        registers.setL(aluOperation.apply(registers.getFlags(), registers.getL()));


        return cycles;
    }
}