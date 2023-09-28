package org.penz.emulator.cpu.instructions;

import org.penz.emulator.cpu.Registers;

public class NopInstruction extends OpCode {

    private int cycles;

    public NopInstruction() {
        super(0x00, "NOP", 4);
    }

    @Override
    int execute(Registers registers) {
        return cycles;
    }
}

