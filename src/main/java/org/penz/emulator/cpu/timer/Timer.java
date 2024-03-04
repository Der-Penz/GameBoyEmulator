package org.penz.emulator.cpu.timer;

import org.penz.emulator.cpu.BitUtil;
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
    private int tac = 0xF8;

    private boolean overflowed;

    private int ticksSinceLastOverflow;

    private int divCyclesCounter = 0;

    private int timaCyclesCounter = 0;

    private final InterruptManager interruptManager;

    public Timer(InterruptManager interruptManager) {
        this.interruptManager = interruptManager;
    }

    /**
     * Controls the timer ticks by synchronizing with the passed cycles from the CPU
     * @param passedCycles the cycles passed since the last tick
     * */
    public void tick(int passedCycles) {
        divCyclesCounter += passedCycles;
        timaCyclesCounter += passedCycles;

        //inc every 256 clock ticks
        // GameBoy.clockSpeed / 16384
        if (divCyclesCounter >= 256) {
            divCyclesCounter -= 256;
            div = (div + 1) & 0xFF;
        }

        //inc every n clock ticks specified by TAC and the corresponding Frequency
        Frequency frequency = Frequency.fromBitValue(tac & 0b11);
        if (timaCyclesCounter >= frequency.getCycles()) {
            timaCyclesCounter -= frequency.getCycles();
            incrementTIMA();
        }

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

    /**
     * Increments the TIMA register only if it is enabled and checks for overflow
     */
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
            return div;
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
