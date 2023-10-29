package org.penz.emulator.cpu.opcode.instructions.arithmetic.adc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Add register L, A and Carry
 */
@SuppressWarnings("unused")
public class ADCLInstruction extends OpCode {

    public ADCLInstruction() {
        super(0x8D, "ADC L", 4);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("ADC", DataType.d8, DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), registers.getL()));
        return cycles;
    }
}
