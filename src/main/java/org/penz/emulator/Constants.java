package org.penz.emulator;

public class Constants {

    private Constants() {
    }

    public static final int WORD_MAX_VALUE = 0xFFFF;

    public static final int BYTE_MAX_VALUE = 0xFF;

    public static final int MSB_MASK = 0xFF00;

    public static final int LSB_MASK = 0x00FF;

    public static final int BYTE_SIZE = 8;

    public static final String GB_BOOT_ROM_PATH = "src/main/resources/bootrom_gb.bin";
}
