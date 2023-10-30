package org.penz.emulator.cpu.opcode.instructions.arithmetic.data16;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Add register SP to HL
 */
@SuppressWarnings("unused")
public class ADDHLSPInstruction extends OpCode {

    public ADDHLSPInstruction() {
        super(0x39, "ADD HL, SP", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("ADD", DataType.d16, DataType.d16);
        registers.setHL(aluOperation.apply(registers.getFlags(), registers.getHL(), registers.getSP()));
        return cycles;
    }
}
