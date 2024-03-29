package org.penz.emulator.cpu.opcode.instructions.prefix.rotate.cleft;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register E left. Old bit 7 to Carry flag and bit 0.
 */
@SuppressWarnings("unused")
public class RLCEInstruction extends BitOpCode {

    public RLCEInstruction() {
        super(0x03, "RLC E", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RLC", DataType.d8);
        registers.setE(aluOperation.apply(registers.getFlags(), registers.getE()));

        return cycles;
    }
}