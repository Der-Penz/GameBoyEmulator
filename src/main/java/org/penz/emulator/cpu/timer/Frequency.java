package org.penz.emulator.cpu.timer;

import org.penz.emulator.GameBoy;

public enum Frequency {
    FREQ_4096(0b00, 4096),
    FREQ_262144(0b01, 262144),
    FREQ_65536(0b10, 65536),
    FREQ_16384(0b11, 16384);

    private final int bitValue;
    private final int frequency;

    Frequency(int value, int frequency) {
        this.bitValue = value;
        this.frequency = frequency;
    }

    public static Frequency fromBitValue(int bitValue) {
        for (Frequency frequency : values()) {
            if (frequency.bitValue == bitValue) {
                return frequency;
            }
        }
        throw new IllegalArgumentException("Invalid bit value: " + bitValue);
    }

    public int getBitValue() {
        return bitValue;
    }

    public int getCycles() {
        return GameBoy.CLOCK_SPEED / frequency;
    }

    @Override
    public String toString() {
        return "Frequency: " + frequency + "Hz (" + getCycles() + " cycles)";
    }
}
