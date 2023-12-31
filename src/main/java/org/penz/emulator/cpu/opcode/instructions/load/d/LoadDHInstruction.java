package org.penz.emulator.cpu.opcode.instructions.load.d;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load register H into register D
 */
@SuppressWarnings("unused")
public class LoadDHInstruction extends OpCode {

    public LoadDHInstruction() {
        super(0x54, "LD D, H", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setD(registers.getH());
        return cycles;
    }
}
