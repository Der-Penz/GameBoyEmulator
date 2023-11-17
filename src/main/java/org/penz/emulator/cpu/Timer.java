package org.penz.emulator.cpu;

import org.penz.emulator.cpu.interrupt.InterruptManager;
import org.penz.emulator.cpu.interrupt.InterruptType;
import org.penz.emulator.memory.AddressSpace;

public class Timer implements AddressSpace {


    private int div;

    private int tima;

    private int tma;

    private int tac;

    private boolean overflowed;

    private int ticksSinceLastOverflow;

    private final InterruptManager interruptManager;

    public Timer(InterruptManager interruptManager) {
        this.interruptManager = interruptManager;
    }

    public void tick() {
        //TODO handle frequency of timer
        div++;

        if (div > 0xFF) {
            div = 0;
        }


        incrementTIMA();

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

    public void resetDiv() {
        div = 0;
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
        return 0;
    }
}
