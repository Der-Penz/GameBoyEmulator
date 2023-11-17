package org.penz.emulator.cpu.interrupt;

import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.memory.AddressSpace;

public class InterruptManager implements AddressSpace {

    /**
     * Interrupt Enable Register. Controls whether a particular interrupt is enabled and can be triggered or not.
     */
    private int interruptEnable;

    /**
     * Interrupt Flag Register. Indicates which interrupts are requested and have been triggered.
     */
    private int interruptFlag;

    public InterruptManager() {

    }

    /**
     * Request an interrupt.
     *
     * @param interruptType the interrupt to request
     */
    public void requestInterrupt(InterruptType interruptType) {
        interruptFlag = BitUtil.setBit(interruptFlag, interruptType.getBit());
    }

    /**
     * Clear IF bit for a given interrupt.
     *
     * @param interruptType the interrupt to clear
     */
    public void clearInterrupt(InterruptType interruptType) {
        interruptFlag = BitUtil.setBit(interruptFlag, interruptType.getBit(), false);
    }

    /**
     * Check if any interrupt is requested and enabled.
     */
    public boolean anyInterruptsRequested() {
        return (interruptFlag & interruptEnable) != 0;
    }

    /**
     * return the requested interrupt with the highest priority
     *
     * @return requested interrupt
     */
    public InterruptType getRequestedInterruptWithHighestPriority() {
        for (int i = 0; i < 5; i++) {
            if (BitUtil.getBit(interruptFlag, i) && BitUtil.getBit(interruptEnable, i)) {
                return InterruptType.fromBit(i);
            }
        }
        return null;
    }

    @Override
    public boolean accepts(int address) {
        return address == 0xFFFF || address == 0xFF0F;
    }

    @Override
    public void writeByte(int address, int value) {
        if (address == 0xFFFF) {
            interruptEnable = value;
        } else if (address == 0xFF0F) {
            interruptFlag = value;
        }
    }

    @Override
    public int readByte(int address) {
        if (address == 0xFFFF) {
            return interruptEnable;
        } else if (address == 0xFF0F) {
            return interruptFlag;
        }
        return 0;
    }
}
