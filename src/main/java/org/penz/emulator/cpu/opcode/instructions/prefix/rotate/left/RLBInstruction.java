package org.penz.emulator.cpu.opcode.instructions.prefix.rotate.left;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register B left through Carry flag.
 */
@SuppressWarnings("unused")
public class RLBInstruction extends BitOpCode {

    public RLBInstruction() {
        super(0x10, "RL B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RL", DataType.d8);
        registers.setB(aluOperation.apply(registers.getFlags(), registers.getB()));

        return cycles;
    }
}