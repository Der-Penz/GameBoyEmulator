package org.penz.emulator.cpu.opcode.instructions.load.a;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load the value at the address pointed to by the HL register into the A register and increment the HL register
 */
@SuppressWarnings("unused")
public class LoadAHLIncInstruction extends OpCode {

    public LoadAHLIncInstruction() {
        super(0x2A, "LD A, (HL+)", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(addressSpace.readByte(registers.getHL()));
        registers.setHL(registers.getHL() + 1);
        return cycles;
    }
}
