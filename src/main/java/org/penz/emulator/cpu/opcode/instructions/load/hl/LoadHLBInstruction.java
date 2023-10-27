package org.penz.emulator.cpu.opcode.instructions.load.hl;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register B into the memory location pointed to by register HL
 */
@SuppressWarnings("unused")
public class LoadHLBInstruction extends OpCode {

    public LoadHLBInstruction() {
        super(0x70, "LD (HL), B", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getHL(), registers.getB());

        return cycles;
    }
}
