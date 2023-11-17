package org.penz.emulator.cpu;

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

    /**
     * Interrupt Master Enable. Controls whether any interrupt can be triggered or not.
     * If this is disabled, no interrupt can be triggered, even if the interrupt is enabled in the Interrupt Enable Register.
     * Cannot be read in any way, and is modified by these instructions/events only: ei, di, reti
     */
    private boolean ime;

    public InterruptManager() {
        ime = false;
    }

    /**
     * Enable IME. Called by the EI instruction.
     */
    public void enableInterrupts() {
        ime = true;
    }

    /**
     * Disable IME. Called by the DI instruction.
     */
    public void disableInterrupts() {
        ime = false;
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
     * Check if interrupts are enabled.
     */
    public boolean interruptsEnabled() {
        return ime;
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
