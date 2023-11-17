package org.penz.emulator.input;

/**
 * Represents a button on the GameBoy.
 */
public enum Button {

    RIGHT(0x00, 0x10),
    LEFT(0x01, 0x10),
    UP(0x02, 0x10),
    DOWN(0x03, 0x10),
    A(0x00, 0x20),
    B(0x01, 0x20),
    SELECT(0x02, 0x20),
    START(0x03, 0x20);

    /**
     * The position of the button in the button group.
     */
    private final int position;

    /**
     * The button group this button belongs to. either direction buttons or action buttons.
     */
    private final int buttonGroup;

    Button(int position, int buttonGroup) {
        this.position = position;
        this.buttonGroup = buttonGroup;
    }

    public int getPosition() {
        return position;
    }

    public int getButtonGroup() {
        return buttonGroup;
    }
}
