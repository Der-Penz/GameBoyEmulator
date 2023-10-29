package org.penz.emulator.cpu.opcode.instructions.misc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Game boy switches to low power mode until an interrupt occurs.
 * TODO: might need an implementation
 */
public class StopInstruction extends OpCode {
    public StopInstruction() {
        super(0x10, "STOP 0", 4, new DataType[]{DataType.d8});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        return cycles;
    }
}
