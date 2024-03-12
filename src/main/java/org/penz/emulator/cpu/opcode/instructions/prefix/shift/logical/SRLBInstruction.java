package org.penz.emulator.cpu.opcode.instructions.prefix.shift.logical;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Logically shift the register B left
 */
@SuppressWarnings("unused")
public class SRLBInstruction extends BitOpCode {

    public SRLBInstruction() {
        super(0x38, "SRL B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SRL", DataType.d8);
        registers.setB(aluOperation.apply(registers.getFlags(), registers.getB()));

        return cycles;
    }
}