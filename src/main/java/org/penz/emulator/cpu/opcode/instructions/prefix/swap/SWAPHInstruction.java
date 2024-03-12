package org.penz.emulator.cpu.opcode.instructions.prefix.swap;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Swap upper and lower nibble of  register H
 */
@SuppressWarnings("unused")
public class SWAPHInstruction extends BitOpCode {

    public SWAPHInstruction() {
        super(0x34, "SWAP H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SWAP", DataType.d8);
        registers.setH(aluOperation.apply(registers.getFlags(), registers.getH()));

        return cycles;
    }
}