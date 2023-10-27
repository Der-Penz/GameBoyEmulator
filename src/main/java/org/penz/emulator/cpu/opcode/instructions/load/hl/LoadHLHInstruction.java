package org.penz.emulator.cpu.opcode.instructions.load.hl;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register H into the memory location pointed to by register HL
 */
@SuppressWarnings("unused")
public class LoadHLHInstruction extends OpCode {

    public LoadHLHInstruction() {
        super(0x74, "LD (HL), H", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getHL(), registers.getH());

        return cycles;
    }
}
