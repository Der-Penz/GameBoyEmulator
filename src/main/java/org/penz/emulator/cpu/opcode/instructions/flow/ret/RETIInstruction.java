package org.penz.emulator.cpu.opcode.instructions.flow.ret;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Return from Interrupt Service Routine
 */
@SuppressWarnings("unused")
public class RETIInstruction extends OpCode {
    public RETIInstruction() {
        super(0xD9, "RETI", 16);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        registers.setPC(addressSpace.readByte(registers.getSP()));
        registers.setPC(BitUtil.toWord(addressSpace.readByte(registers.getSP() + 1), registers.getPC()));
        registers.setSP(registers.getSP() + 2);

        registers.enableInterrupts();
        return cycles;
    }
}
