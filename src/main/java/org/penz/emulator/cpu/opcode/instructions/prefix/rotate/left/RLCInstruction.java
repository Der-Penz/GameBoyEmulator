package org.penz.emulator.cpu.opcode.instructions.prefix.rotate.left;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register C left through Carry flag.
 */
@SuppressWarnings("unused")
public class RLCInstruction extends BitOpCode {

    public RLCInstruction() {
        super(0x11, "RL C", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RL", DataType.d8);
        registers.setC(aluOperation.apply(registers.getFlags(), registers.getC()));

        return cycles;
    }
}