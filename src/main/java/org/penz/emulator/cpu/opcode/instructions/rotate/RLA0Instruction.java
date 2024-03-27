package org.penz.emulator.cpu.opcode.instructions.rotate;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Rotate register A left through Carry flag.
 */
@SuppressWarnings("unused")
public class RLA0Instruction extends OpCode {

    public RLA0Instruction() {
        super(0x17, "RLA", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("RL", DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA()));

        registers.getFlags().setZ(false);
        return cycles;
    }
}
