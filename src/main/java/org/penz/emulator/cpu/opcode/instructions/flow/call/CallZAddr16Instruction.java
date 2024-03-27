package org.penz.emulator.cpu.opcode.instructions.flow.call;

import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

/**
 * Subroutine call to address specified by 16 bit immediate data if Z flag is set
 */
@SuppressWarnings("unused")
public class CallZAddr16Instruction extends OpCode {
    public CallZAddr16Instruction() {
        super(0xCC, "CALL Z, a16", 24, new DataType[]{DataType.a16});
    }

    @Override
    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {
        if (!registers.getFlags().isZ()) {
            return 12;
        }
        addressSpace.writeByte(registers.getSPSafe(-1), BitUtil.getMSByte(registers.getPC()));
        addressSpace.writeByte(registers.getSPSafe(-2), BitUtil.getLSByte(registers.getPC()));

        registers.setPC(args[0]);
        registers.setSP(registers.getSPSafe(-2));

        return cycles;
    }
}
