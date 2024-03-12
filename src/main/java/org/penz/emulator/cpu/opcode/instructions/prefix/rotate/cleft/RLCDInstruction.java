package org.penz.emulator.cpu.opcode.instructions.prefix.rotate.cleft;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.BitOpCode;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register D left. Old bit 7 to Carry flag and bit 0.
 */
@SuppressWarnings("unused")
public class RLCDInstruction extends BitOpCode {

    public RLCDInstruction() {
        super(0x02, "RLC D", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RLC", DataType.d8);
        registers.setD(aluOperation.apply(registers.getFlags(), registers.getD()));

        return cycles;
    }
}