package org.penz.emulator.cpu.opcode.instructions.prefix.swap;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Swap upper and lower nibble of  register A
 */
@SuppressWarnings("unused")
public class SWAPAInstruction extends BitOpCode {

    public SWAPAInstruction() {
        super(0x37, "SWAP A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SWAP", DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA()));

        return cycles;
    }
}