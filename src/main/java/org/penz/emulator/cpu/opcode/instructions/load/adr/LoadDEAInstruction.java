package org.penz.emulator.cpu.opcode.instructions.load.adr;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load A into address pointed by DE
 */
@SuppressWarnings("unused")
public class LoadDEAInstruction extends OpCode {

    public LoadDEAInstruction() {
        super(0x12, "LD (DE), A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getDE(), registers.getA());

        return cycles;
    }
}
