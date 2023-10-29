package org.penz.emulator.cpu.opcode.instructions.arithmetic.sub;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Subtract register L from A
 */
@SuppressWarnings("unused")
public class SUBLInstruction extends OpCode {

    public SUBLInstruction() {
        super(0x95, "SUB L", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("SUB", DataType.d8, DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), registers.getL()));
        return cycles;
    }
}
