package org.penz.emulator.cpu.opcode.instructions.load.adr;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load A into address pointed by BC
 */
@SuppressWarnings("unused")
public class LoadBCAInstruction extends OpCode {

    public LoadBCAInstruction() {
        super(0x02, "LD (BC), A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getBC(), registers.getA());

        return cycles;
    }
}
