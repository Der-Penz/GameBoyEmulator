package org.penz.emulator.cpu.opcode.instructions.flow.jump;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Jump to address stored in register HL
 */
@SuppressWarnings("unused")
public class JPHLInstruction extends OpCode {

    public JPHLInstruction() {
        super(0xE9, "JR (HL)", 4, new DataType[]{DataType.a16});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setPC(registers.getHL());

        return cycles;
    }
}
