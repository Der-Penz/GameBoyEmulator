package org.penz.emulator.memory.cartridge;

import java.util.regex.Pattern;

public enum CartridgeType {

    ROM(0x00),
    ROM_MBC1(0x01),
    ROM_MBC1_RAM(0x02),
    ROM_MBC1_RAM_BATTERY(0x03),
    ROM_MBC2(0x05), ROM_MBC2_BATTERY(0x06),
    ROM_RAM(0x08),
    ROM_RAM_BATTERY(0x09),
    ROM_MMM01(0x0b),
    ROM_MMM01_SRAM(0x0c),
    ROM_MMM01_SRAM_BATTERY(0x0d),
    ROM_MBC3_TIMER_BATTERY(0x0f),
    ROM_MBC3_TIMER_RAM_BATTERY(0x10),
    ROM_MBC3(0x11),
    ROM_MBC3_RAM(0x12),
    ROM_MBC3_RAM_BATTERY(0x13),
    ROM_MBC5(0x19),
    ROM_MBC5_RAM(0x1a),
    ROM_MBC5_RAM_BATTERY(0x01b),
    ROM_MBC5_RUMBLE(0x1c),
    ROM_MBC5_RUMBLE_SRAM(0x1d),
    ROM_MBC5_RUMBLE_SRAM_BATTERY(0x1e);

    private final int id;

    CartridgeType(int id) {
        this.id = id;
    }

    public boolean isMbc1() {
        return containsString("MBC1");
    }

    public boolean isMbc2() {
        return containsString("MBC2");
    }

    public boolean isMbc3() {
        return containsString("MBC3");
    }

    public boolean isMbc5() {
        return containsString("MBC5");
    }

    public boolean isMmm01() {
        return containsString("MMM01");
    }

    public boolean isRam() {
        return containsString("RAM");
    }

    public boolean isSram() {
        return containsString("SRAM");
    }

    public boolean isTimer() {
        return containsString("TIMER");
    }

    public boolean isBattery() {
        return containsString("BATTERY");
    }

    public boolean isRumble() {
        return containsString("RUMBLE");
    }

    private boolean containsString(String str) {
        Pattern p = Pattern.compile("(^|_)" + Pattern.quote(str) + "($|_)");
        return p.matcher(name()).find();
    }

    public static CartridgeType getById(int id) {
        for (CartridgeType t : values()) {
            if (t.id == id) {
                return t;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid cartridge type id: 0x%02X", id));
    }
}