package org.penz.emulator.cpu.opcode.instructions.prefix.rotate.left;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register A left through Carry flag.
 */
@SuppressWarnings("unused")
public class RLAInstruction extends BitOpCode {

    public RLAInstruction() {
        super(0x17, "RL A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RL", DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA()));

        return cycles;
    }
}