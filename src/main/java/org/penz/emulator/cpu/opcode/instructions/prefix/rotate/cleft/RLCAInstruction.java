package org.penz.emulator.cpu.opcode.instructions.prefix.rotate.cleft;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register A left. Old bit 7 to Carry flag and bit 0.
 */
@SuppressWarnings("unused")
public class RLCAInstruction extends BitOpCode {

    public RLCAInstruction() {
        super(0x07, "RLC A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RLCA", DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA()));

        return cycles;
    }
}