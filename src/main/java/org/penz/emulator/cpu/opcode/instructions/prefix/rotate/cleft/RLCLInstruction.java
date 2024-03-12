package org.penz.emulator.cpu.opcode.instructions.prefix.rotate.cleft;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register L left. Old bit 7 to Carry flag and bit 0.
 */
@SuppressWarnings("unused")
public class RLCLInstruction extends BitOpCode {

    public RLCLInstruction() {
        super(0x05, "RLC L", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RLC", DataType.d8);
        registers.setL(aluOperation.apply(registers.getFlags(), registers.getL()));

        return cycles;
    }
}