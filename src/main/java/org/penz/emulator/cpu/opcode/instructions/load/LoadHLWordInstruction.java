package org.penz.emulator.cpu.opcode.instructions.load;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load immediate word into HL
 */
public class LoadHLWordInstruction extends OpCode {

    public LoadHLWordInstruction() {
        super(0x21, "LD HL, D16", 12, new DataType[]{DataType.d16});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setHL(args[0]);
        return cycles;
    }
}
