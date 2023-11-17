package org.penz.emulator.input;

/**
 * Interface for listening to button events.
 */
public interface ButtonListener {

    void onButtonPressed(Button button);

    void onButtonReleased(Button button);
}
