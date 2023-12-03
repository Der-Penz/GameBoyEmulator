package org.penz.emulator.graphics.enums;

public enum ObjSize {
    SIZE_8x8,
    SIZE_8x16;

    public static ObjSize fromValue(int value) {
        if (value == 0) {
            return SIZE_8x8;
        } else {
            return SIZE_8x16;
        }
    }
}
