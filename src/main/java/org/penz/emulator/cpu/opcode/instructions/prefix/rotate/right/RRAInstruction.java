package org.penz.emulator.cpu.opcode.instructions.prefix.rotate.right;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register A right through Carry flag.
 */
@SuppressWarnings("unused")
public class RRAInstruction extends BitOpCode {

    public RRAInstruction() {
        super(0x1F, "RR A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RR", DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA()));

        return cycles;
    }
}