package org.penz.emulator.cpu.opcode.instructions.load.adr;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load A into address pointed by the immediate 16 bit address
 */
@SuppressWarnings("unused")
public class LoadAdrAInstruction extends OpCode {

    public LoadAdrAInstruction() {
        super(0xEA, "LD (a16), A", 16, new DataType[]{DataType.a16});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        addressSpace.writeByte(args[0], registers.getA());
        return cycles;
    }
}
