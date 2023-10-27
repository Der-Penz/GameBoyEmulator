package org.penz.emulator.cpu.opcode.instructions.load.adr;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load A into address pointed by HL and decrement HL
 */
@SuppressWarnings("unused")
public class LoadDecHLAddressAInstruction extends OpCode {

    public LoadDecHLAddressAInstruction() {
        super(0x32, "LD (HL-), A", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(registers.getHL(), registers.getA());
        registers.setHL(registers.getHL() - 1);
        return cycles;
    }
}
