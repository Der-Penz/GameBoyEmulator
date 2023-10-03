package org.penz.emulator.memory.cartridge;

public enum DestinationCode {
    JAPAN,
    INTERNATIONAL;

    public static DestinationCode fromValue(int value) {
        return value == 0 ? JAPAN : INTERNATIONAL;
    }
}
