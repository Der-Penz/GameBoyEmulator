package org.penz.emulator.cpu.opcode.instructions.arithmetic.dec;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Decrement value in memory pointed to by HL
 */
@SuppressWarnings("unused")
public class DECHLInstruction extends OpCode {

    public DECHLInstruction() {
        super(0x35, "DEC (HL)", 12);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("DEC", DataType.d8);
        addressSpace.writeByte(registers.getHL(), aluOperation.apply(registers.getFlags(), addressSpace.readByte(registers.getHL())));
        return cycles;
    }
}
