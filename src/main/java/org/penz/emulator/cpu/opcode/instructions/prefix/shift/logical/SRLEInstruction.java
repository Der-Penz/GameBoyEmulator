package org.penz.emulator.cpu.opcode.instructions.prefix.shift.logical;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Logically shift the register E left
 */
@SuppressWarnings("unused")
public class SRLEInstruction extends BitOpCode {

    public SRLEInstruction() {
        super(0x3B, "SRL E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SRL", DataType.d8);
        registers.setE(aluOperation.apply(registers.getFlags(), registers.getE()));

        return cycles;
    }
}