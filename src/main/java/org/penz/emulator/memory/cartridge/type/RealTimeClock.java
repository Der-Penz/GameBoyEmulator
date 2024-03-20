package org.penz.emulator.memory.cartridge.type;

import java.io.Serializable;

public class RealTimeClock implements Serializable {

    private long offsetSec;
    private long clockStart;
    private boolean halt;
    private long latchStart;
    private int haltSeconds;
    private int haltMinutes;
    private int haltHours;
    private int haltDays;
    private int selectedRegister;
    private boolean latched;

    public RealTimeClock() {
        this.clockStart = System.currentTimeMillis();
    }

    public void latch() {
        latched = true;
        latchStart = System.currentTimeMillis();
    }

    public void unlatch() {
        latched = false;
        latchStart = 0;
    }

    public boolean isLatched() {
        return latched;
    }

    public int getSeconds() {
        return (int) (clockTimeInSec() % 60);
    }

    public void setSeconds(int seconds) {
        if (!halt) {
            return;
        }
        haltSeconds = seconds;
    }

    public int getMinutes() {
        return (int) ((clockTimeInSec() % (60 * 60)) / 60);
    }

    public void setMinutes(int minutes) {
        if (!halt) {
            return;
        }
        haltMinutes = minutes;
    }

    public int getHours() {
        return (int) ((clockTimeInSec() % (60 * 60 * 24)) / (60 * 60));
    }

    public void setHours(int hours) {
        if (!halt) {
            return;
        }
        haltHours = hours;
    }

    public int getDayCounter() {
        return (int) (clockTimeInSec() % (60 * 60 * 24 * 512) / (60 * 60 * 24));
    }

    public void setDayCounter(int dayCounter) {
        if (!halt) {
            return;
        }
        haltDays = dayCounter;
    }

    public boolean isHalt() {
        return halt;
    }

    public void setHalt(boolean halt) {
        if (halt && !this.halt) {
            latch();
            haltSeconds = getSeconds();
            haltMinutes = getMinutes();
            haltHours = getHours();
            haltDays = getDayCounter();
            unlatch();
        } else if (!halt && this.halt) {
            offsetSec = haltSeconds + haltMinutes * 60L + (long) haltHours * 60 * 60 + (long) haltDays * 60 * 60 * 24;
            clockStart = System.currentTimeMillis();
        }
        this.halt = halt;
    }

    public boolean isCounterOverflow() {
        return clockTimeInSec() >= 60 * 60 * 24 * 512;
    }

    public void selectRegister(int register) {
        selectedRegister = register;
    }

    public void clearCounterOverflow() {
        while (isCounterOverflow()) {
            offsetSec -= 60 * 60 * 24 * 512;
        }
    }

    private long clockTimeInSec() {
        long now;
        if (latchStart == 0) {
            now = System.currentTimeMillis();
        } else {
            now = latchStart;
        }
        return (now - clockStart) / 1000 + offsetSec;
    }

    public int getTimer() {
        switch (selectedRegister) {
            case 0x08:
                return getSeconds();

            case 0x09:
                return getMinutes();

            case 0x0a:
                return getHours();

            case 0x0b:
                return getDayCounter() & 0xff;

            case 0x0c:
                int result = ((getDayCounter() & 0x100) >> 8);
                result |= isHalt() ? (1 << 6) : 0;
                result |= isCounterOverflow() ? (1 << 7) : 0;
                return result;
        }
        return 0xff;
    }

    public void setTimer(int value) {
        int dayCounter = getDayCounter();
        switch (selectedRegister) {
            case 0x08:
                setSeconds(value);
                break;
            case 0x09:
                setMinutes(value);
                break;

            case 0x0a:
                setHours(value);
                break;
            case 0x0b:
                setDayCounter((dayCounter & 0x100) | (value & 0xff));
                break;

            case 0x0c:
                setDayCounter((dayCounter & 0xff) | ((value & 1) << 8));
                setHalt((value & (1 << 6)) != 0);
                if ((value & (1 << 7)) == 0) {
                    clearCounterOverflow();
                }
                break;
        }
    }

    public void deserialize(long[] clockData) {
        long seconds = clockData[0];
        long minutes = clockData[1];
        long hours = clockData[2];
        long days = clockData[3];
        long daysHigh = clockData[4];
        long timestamp = clockData[10];

        this.clockStart = timestamp * 1000;
        this.offsetSec = seconds + minutes * 60 + hours * 60 * 60 + days * 24 * 60 * 60 + daysHigh * 256 * 24 * 60 * 60;
    }

    public long[] serialize() {
        long[] clockData = new long[11];
        latch();
        clockData[0] = clockData[5] = getSeconds();
        clockData[1] = clockData[6] = getMinutes();
        clockData[2] = clockData[7] = getHours();
        clockData[3] = clockData[8] = getDayCounter() % 256;
        clockData[4] = clockData[9] = getDayCounter() / 256;
        clockData[10] = latchStart / 1000;
        unlatch();
        return clockData;
    }
}