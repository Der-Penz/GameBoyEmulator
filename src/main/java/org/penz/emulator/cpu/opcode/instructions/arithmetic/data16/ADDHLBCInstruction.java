package org.penz.emulator.cpu.opcode.instructions.arithmetic.data16;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Add register BC to HL
 */
@SuppressWarnings("unused")
public class ADDHLBCInstruction extends OpCode {

    public ADDHLBCInstruction() {
        super(0x09, "ADD HL, BC", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("ADD", DataType.d16, DataType.d16);
        registers.setHL(aluOperation.apply(registers.getFlags(), registers.getHL(), registers.getBC()));
        return cycles;
    }
}
