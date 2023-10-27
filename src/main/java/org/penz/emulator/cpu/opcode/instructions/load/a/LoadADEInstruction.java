package org.penz.emulator.cpu.opcode.instructions.load.a;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load the value at the address pointed to by the DE register into the A register
 */
@SuppressWarnings("unused")
public class LoadADEInstruction extends OpCode {

    public LoadADEInstruction() {
        super(0x1A, "LD A, (DE)", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(addressSpace.readByte(registers.getDE()));
        return cycles;
    }
}
