package org.penz.emulator.input;

/**
 * Maps a Gameboy joypad button to a keyboard key code.
 */
public class ButtonConfig {

    private final Button button;

    private int mappedKeyCode;

    public ButtonConfig(Button button, int keyCode) {
        this.button = button;
        this.mappedKeyCode = keyCode;
    }

    /**
     * Returns true if the button is mapped to the given key code.
     *
     * @param keyCode the key code to check
     * @return true if the button is mapped to the given key code
     */
    public boolean isButtonMappedTo(int keyCode) {
        return this.mappedKeyCode == keyCode;
    }

    public Button getButton() {
        return button;
    }

    public int getMappedKeyCode() {
        return mappedKeyCode;
    }

    public void setMappedKeyCode(int mappedKeyCode) {
        this.mappedKeyCode = mappedKeyCode;
    }

}
