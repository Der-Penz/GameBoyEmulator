package org.penz.emulator.cpu.opcode.instructions.prefix.shift.logical;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Logically shift the register C left
 */
@SuppressWarnings("unused")
public class SRLCInstruction extends BitOpCode {

    public SRLCInstruction() {
        super(0x39, "SRL C", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SRL", DataType.d8);
        registers.setC(aluOperation.apply(registers.getFlags(), registers.getC()));

        return cycles;
    }
}