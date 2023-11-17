package org.penz.emulator.cpu;

/**
 * Represents the different types of interrupts that can be triggered in the GameBoy.
 */
public enum InterruptType {
    VBLANK(0, 0x40),
    LCD(1, 0x48),
    TIMER(2, 0x50),
    SERIAL(3, 0x58),
    JOYPAD(4, 0x60);

    /**
     * The bit position of the interrupt in the Interrupt Flag Register and Interrupt Enable Register.
     */
    private final int bit;

    /**
     * The address to jump to when the interrupt is triggered.
     */
    private final int jumpAddress;

    InterruptType(int bit, int jumpAddress) {
        this.bit = bit;
        this.jumpAddress = jumpAddress;
    }

    public int getBit() {
        return bit;
    }

    public int getJumpAddress() {
        return jumpAddress;
    }

    /**
     * Get the interrupt type from the bit position in the IF or IE register.
     *
     * @param bitPosition the bit position
     * @return the corresponding interrupt type
     */
    public static InterruptType fromBit(int bitPosition) {
        for (InterruptType interruptType : InterruptType.values()) {
            if (interruptType.getBit() == bitPosition) {
                return interruptType;
            }
        }
        throw new IllegalArgumentException("No interrupt type with bit position " + bitPosition);
    }
}
