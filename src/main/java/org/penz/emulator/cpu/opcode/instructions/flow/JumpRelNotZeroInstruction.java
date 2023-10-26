package org.penz.emulator.cpu.opcode.instructions.flow;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Jump relative if z flag is not set
 */
@SuppressWarnings("unused")
public class JumpRelNotZeroInstruction extends OpCode {

    public JumpRelNotZeroInstruction() {
        super(0x20, "JR NZ, r8", 8, new DataType[]{DataType.r8});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        if (registers.getFlags().isZ()) {
            return cycles;
        } else {
            //if jump is taken, it takes 12 cycles
            registers.setPC(registers.getPC() + args[0]);
            return 12;
        }
    }
}
