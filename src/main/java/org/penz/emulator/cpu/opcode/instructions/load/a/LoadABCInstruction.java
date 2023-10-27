package org.penz.emulator.cpu.opcode.instructions.load.a;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load the value at the address pointed to by the BC register into the A register
 */
@SuppressWarnings("unused")
public class LoadABCInstruction extends OpCode {

    public LoadABCInstruction() {
        super(0x0A, "LD A, (BC)", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setA(addressSpace.readByte(registers.getBC()));
        return cycles;
    }
}
