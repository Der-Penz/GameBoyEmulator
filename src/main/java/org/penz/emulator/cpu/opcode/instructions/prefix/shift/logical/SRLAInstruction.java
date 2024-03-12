package org.penz.emulator.cpu.opcode.instructions.prefix.shift.logical;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Logically shift the register A left
 */
@SuppressWarnings("unused")
public class SRLAInstruction extends BitOpCode {

    public SRLAInstruction() {
        super(0x3F, "SRL A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SRL", DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA()));

        return cycles;
    }
}