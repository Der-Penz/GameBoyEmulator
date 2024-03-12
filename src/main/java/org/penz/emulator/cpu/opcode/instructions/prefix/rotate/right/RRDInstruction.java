package org.penz.emulator.cpu.opcode.instructions.prefix.rotate.right;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register D right through Carry flag.
 */
@SuppressWarnings("unused")
public class RRDInstruction extends BitOpCode {

    public RRDInstruction() {
        super(0x1A, "RR D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RR", DataType.d8);
        registers.setD(aluOperation.apply(registers.getFlags(), registers.getD()));


        return cycles;
    }
}