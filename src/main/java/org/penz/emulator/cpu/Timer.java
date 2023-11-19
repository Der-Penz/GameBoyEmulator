package org.penz.emulator.cpu;

import org.penz.emulator.cpu.interrupt.InterruptManager;
import org.penz.emulator.cpu.interrupt.InterruptType;
import org.penz.emulator.memory.AddressSpace;

public class Timer implements AddressSpace {

    /**
     * Divider register, incremented at a rate of 16384Hz
     */
    private int div;

    /**
     * Timer counter register, incremented at a rate specified by the TAC register
     */
    private int tima;

    /**
     * Timer modulo register, when TIMA overflows, it is reset to this value
     */
    private int tma;

    /**
     * Timer control register, controls the timer speed and whether it is enabled
     */
    private int tac;

    private boolean overflowed;

    private int ticksSinceLastOverflow;

    private boolean previousBit;

    private static final int[] FREQ_TO_BIT = {9, 3, 5, 7};

    private final InterruptManager interruptManager;

    public Timer(InterruptManager interruptManager) {
        this.interruptManager = interruptManager;
    }

    /**
     * One tick of the timer
     */
    public void tick() {
        div = (div + 1) & 0xFFFF;
        //inc every 4096 clock ticks
        //TODO understand how timer works
        int bitPos = FREQ_TO_BIT[tac & 0b11];
        boolean bit = (div & (1 << bitPos)) != 0;
        bit &= (tac & (1 << 2)) != 0;
        if (!bit && previousBit) {
            incrementTIMA();
        }
        previousBit = bit;

        if (overflowed) {
            ticksSinceLastOverflow += 1;
            if (ticksSinceLastOverflow == 4) {
                interruptManager.requestInterrupt(InterruptType.TIMER);
            }
            if (ticksSinceLastOverflow == 5) {
                tima = tma;
                overflowed = false;
                ticksSinceLastOverflow = 0;
            }
        }

    }

    private void incrementTIMA() {
        if (enabled()) {
            tima++;
        }
        if (tima >= 0xFF) {
            ticksSinceLastOverflow = 0;
            overflowed = true;
        }
    }

    private boolean enabled() {
        return BitUtil.getBit(tac, 2);
    }

    @Override
    public boolean accepts(int address) {
        return address >= 0xFF04 && address <= 0xFF07;
    }

    @Override
    public void writeByte(int address, int value) {
        if (address == 0xFF04) {
            div = 0;
        } else if (address == 0xFF05) {
            tima = value;
        } else if (address == 0xFF06) {
            tma = value;
        } else if (address == 0xFF07) {
            tac = value & 0x07;
        }
    }

    @Override
    public int readByte(int address) {
        if (address == 0xFF04) {
            return div >> 8;
        } else if (address == 0xFF05) {
            return tima;
        } else if (address == 0xFF06) {
            return tma;
        } else if (address == 0xFF07) {
            return tac;
        }
        return 0;
    }
}
