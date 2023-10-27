package org.penz.emulator.cpu.opcode.instructions.load.adr;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Load value of the stack pointer into the memory address specified by the 16 bit immediate address
 */
@SuppressWarnings("unused")
public class LoadAdrSPInstruction extends OpCode {

    public LoadAdrSPInstruction() {
        super(0x08, "LD (a16), SP", 20, new DataType[]{DataType.a16});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        BitUtil.checkIsWord(args[0] + 1);
        addressSpace.writeByte(args[0], BitUtil.getLSByte(registers.getSP()));
        addressSpace.writeByte(args[0] + 1, BitUtil.getMSByte(registers.getSP()));
        return cycles;
    }
}
