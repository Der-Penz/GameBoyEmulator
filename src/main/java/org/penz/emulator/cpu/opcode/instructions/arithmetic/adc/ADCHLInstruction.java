package org.penz.emulator.cpu.opcode.instructions.arithmetic.adc;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Add memory located at the position register HL points to, A and Carry
 */
@SuppressWarnings("unused")
public class ADCHLInstruction extends OpCode {

    public ADCHLInstruction() {
        super(0x8E, "ADC (HL)", 8);
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        var aluOperation = alu.getOperation("ADC", DataType.d8, DataType.d8);
        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), addressSpace.readByte(registers.getHL())));
        return cycles;
    }
}
