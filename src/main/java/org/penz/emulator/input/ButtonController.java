package org.penz.emulator.input;

/**
 * Interface for button controllers.
 * Must be implemented to be able to integrate with the joypad
 */
public interface ButtonController {
    void setButtonListener(ButtonListener buttonListener);
}
