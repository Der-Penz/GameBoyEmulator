package org.penz.emulator.cpu.opcode.instructions.prefix.rotate.left;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register D left through Carry flag.
 */
@SuppressWarnings("unused")
public class RLDInstruction extends BitOpCode {

    public RLDInstruction() {
        super(0x12, "RL D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RL", DataType.d8);
        registers.setD(aluOperation.apply(registers.getFlags(), registers.getD()));

        return cycles;
    }
}