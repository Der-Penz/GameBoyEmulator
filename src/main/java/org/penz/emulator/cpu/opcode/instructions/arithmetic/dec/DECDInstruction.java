package org.penz.emulator.cpu.opcode.instructions.arithmetic.dec;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Decrement register D
 */
@SuppressWarnings("unused")
public class DECDInstruction extends OpCode {

    public DECDInstruction() {
        super(0x15, "DEC D", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("DEC", DataType.d8);
        registers.setD(aluOperation.apply(registers.getFlags(), registers.getD()));
        return cycles;
    }
}
