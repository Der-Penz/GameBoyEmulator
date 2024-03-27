package org.penz.emulator.cpu.opcode.instructions.flow.call;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Subroutine call to address specified by 16 bit immediate data if Z flag is not set
 */
@SuppressWarnings("unused")
public class CallNZAddr16Instruction extends OpCode {
    public CallNZAddr16Instruction() {
        super(0xC4, "CALL NZ, a16", 24, new DataType[]{DataType.a16});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        if (registers.getFlags().isZ()) {
            return 12;
        }
        addressSpace.writeByte(registers.getSPSafe(-1), BitUtil.getMSByte(registers.getPC()));
        addressSpace.writeByte(registers.getSPSafe(-2), BitUtil.getLSByte(registers.getPC()));

        registers.setPC(args[0]);
        registers.setSP(registers.getSPSafe(-2));

        return cycles;
    }
}
