package org.penz.emulator.cpu.opcode.instructions.prefix.swap;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Swap upper and lower nibble of  register B
 */
@SuppressWarnings("unused")
public class SWAPBInstruction extends BitOpCode {

    public SWAPBInstruction() {
        super(0x30, "SWAP B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SWAP", DataType.d8);
        registers.setB(aluOperation.apply(registers.getFlags(), registers.getB()));

        return cycles;
    }
}