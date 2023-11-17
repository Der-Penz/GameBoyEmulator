package org.penz.emulator.input;

import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.interrupt.InterruptManager;
import org.penz.emulator.cpu.interrupt.InterruptType;
import org.penz.emulator.memory.AddressSpace;

import java.util.HashSet;

/**
 * Implements the joypad register. Handles the button presses and releases.
 */
public class Joypad implements AddressSpace {

    /**
     * Holds the currently pressed buttons.
     */
    HashSet<Button> pressedButtons = new HashSet<>();

    /**
     * P1 is the joypad register.
     * Holds the status of the joypad.
     */
    int p1 = 0x00;

    public Joypad(InterruptManager interruptManager, ButtonController buttonController) {
        buttonController.setButtonListener(new ButtonListener() {
            @Override
            public void onButtonPressed(Button button) {
                pressButton(button);
                interruptManager.requestInterrupt(InterruptType.JOYPAD);
            }

            @Override
            public void onButtonReleased(Button button) {
                releaseButton(button);
            }
        });
    }

    private void pressButton(Button button) {
        pressedButtons.add(button);
    }

    private void releaseButton(Button button) {
        pressedButtons.remove(button);
    }

    private int getPressedButtonState() {
        int pressedButtonState = 0x0F;
        for (Button pressedButton : pressedButtons) {
            if ((p1 & pressedButton.getButtonGroup()) == 0) {
                pressedButtonState = BitUtil.clearBit(pressedButtonState, pressedButton.getPosition());
            }
        }
        return pressedButtonState;
    }

    @Override
    public boolean accepts(int address) {
        return address == 0xFF00;
    }

    @Override
    public void writeByte(int address, int value) {
        p1 = value & 0b00110000;
    }

    @Override
    public int readByte(int address) {
        //if no button group is selected
        if (p1 == 0x30 || p1 == 0x00) {
            return 0x0F;
        }
        return p1 | getPressedButtonState();
    }
}
