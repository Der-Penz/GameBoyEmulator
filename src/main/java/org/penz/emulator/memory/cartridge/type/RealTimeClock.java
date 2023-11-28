package org.penz.emulator.memory.cartridge.type;

import org.penz.emulator.cpu.BitUtil;

public class RealTimeClock {


    private long startTimeStamp;

    private long latchStartTimeStamp;

    private boolean latched;

    private int selectedRegister;

    private boolean halt;

    private int latchReg = 0xFF;

    public RealTimeClock(long clockStart) {
        //TODO Use Dependency Injection to inject the clock to be able to use a custom clock for testing
        this.startTimeStamp = clockStart;
        this.latched = false;
        this.halt = false;
    }

    public RealTimeClock() {
        this(System.currentTimeMillis());
    }

    public void latch() {
        this.latched = true;
        this.latchStartTimeStamp = System.currentTimeMillis();
    }

    public void unlatch() {
        this.latched = false;
        this.latchStartTimeStamp = 0;
    }

    public void toggleLatch(int newLatchReg) {
        if (newLatchReg == 0x00 && latchReg == 0x00) {
            if (latched) {
                unlatch();
            } else {
                latch();
            }
        }
        latchReg = newLatchReg;
    }

    /**
     * Converts the time into seconds
     *
     * @return the time in seconds
     */
    private long timeInSeconds() {
        long now;
        if (latched) {
            now = latchStartTimeStamp;
        } else {
            now = System.currentTimeMillis();
        }
        return (now + startTimeStamp) / 1000;
    }

    private int getSeconds() {
        return (int) (timeInSeconds() % 60);
    }

    private int getMinutes() {
        return (int) ((timeInSeconds() % (60 * 60)) / 60);
    }

    private int getHours() {
        return (int) ((timeInSeconds() % (60 * 60 * 24)) / (60 * 60));
    }

    private int getDayCounter() {
        return (int) (timeInSeconds() % (60 * 60 * 24 * 512) / (60 * 60 * 24));
    }

    /**
     * Get the RTC DH Regsiter containing the halt flag and the day counter overflow flag and the upper 1 bit of the day counter
     *
     * @return the RTC DH register
     */
    private int getDayHighRegister() {
        int upperDayBit = (((int) (timeInSeconds() % (60 * 60 * 24 * 512) / (60 * 60 * 24))) & 0x100) >> 8;
        upperDayBit = BitUtil.setBit(upperDayBit, 6, halt);
        boolean isDayCounterOverflow = timeInSeconds() >= 60 * 60 * 24 * 512;
        return BitUtil.setBit(upperDayBit, 7, isDayCounterOverflow);
    }

    /**
     * Select the corresponding RTC register
     *
     * @param value the wanted register
     */
    public void selectRegister(int value) {
        if (value >= 0x08 && value <= 0x0C) {
            selectedRegister = value;
        }
    }

    /**
     * Get the currently selected register value
     *
     * @return the value of the selected register
     */
    public int getSelectedRegister() {
        return switch (selectedRegister) {
            case 0x08 -> getSeconds();
            case 0x09 -> getMinutes();
            case 0x0A -> getHours();
            case 0x0B -> getDayCounter();
            case 0x0C -> getDayHighRegister();
            default -> throw new IllegalArgumentException("Invalid register selected");
        };
    }

    /**
     * Set the value of the currently selected register
     */
    public void setSelectedRegister(int value) {
        //TODO set the time and halt etc register
    }

    public byte[] serialize() {
        byte[] clockData = new byte[8];

        long now;
        if (latched) {
            now = latchStartTimeStamp;
        } else {
            now = System.currentTimeMillis();
        }
        long millis = (now + startTimeStamp);

        clockData[0] = (byte) (millis & 0xFF);
        clockData[1] = (byte) ((millis >> 8) & 0xFF);
        clockData[2] = (byte) ((millis >> 16) & 0xFF);
        clockData[3] = (byte) ((millis >> 24) & 0xFF);
        clockData[4] = (byte) (latched ? 0x01 : 0x00);
        clockData[5] = (byte) (halt ? 0x01 : 0x00);
        clockData[6] = (byte) (latchReg & 0xFF);
        clockData[7] = (byte) (selectedRegister & 0xFF);

        return clockData;
    }

    public void deserialize(byte[] clockData) {
        long millis = clockData[0] & 0xFF;
        millis |= ((long) clockData[1]) << 8;
        millis |= ((long) clockData[2]) << 16;
        millis |= ((long) clockData[3]) << 24;

        this.latched = clockData[4] == 0x01;
        this.halt = clockData[5] == 0x01;
        this.latchReg = clockData[6];
        this.selectedRegister = clockData[7];

        this.startTimeStamp = millis;
        this.latchStartTimeStamp = System.currentTimeMillis();
    }

}
