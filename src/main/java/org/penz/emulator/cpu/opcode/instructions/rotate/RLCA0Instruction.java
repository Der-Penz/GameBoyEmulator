package org.penz.emulator.cpu.opcode.instructions.rotate;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register A left. Old bit 7 to Carry flag and bit 0.
 */
@SuppressWarnings("unused")
public class RLCA0Instruction extends OpCode {

    public RLCA0Instruction() {
        super(0x07, "RLCA", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RLC", DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA()));

        registers.getFlags().setZ(false);
        return cycles;
    }
}
