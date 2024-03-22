package org.penz.emulator.input;

import org.penz.emulator.GameBoySettings;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardController implements IButtonController, KeyListener {

    private final ButtonConfig[] buttonConfigs;
    private ButtonListener buttonListener;

    public KeyboardController() {
        this.buttonListener = null;

        this.buttonConfigs = new ButtonConfig[]{
                new ButtonConfig(Button.UP, GameBoySettings.getInstance().getButton(Button.UP)),
                new ButtonConfig(Button.DOWN, GameBoySettings.getInstance().getButton(Button.DOWN)),
                new ButtonConfig(Button.LEFT, GameBoySettings.getInstance().getButton(Button.LEFT)),
                new ButtonConfig(Button.RIGHT, GameBoySettings.getInstance().getButton(Button.RIGHT)),
                new ButtonConfig(Button.A, GameBoySettings.getInstance().getButton(Button.A)),
                new ButtonConfig(Button.B, GameBoySettings.getInstance().getButton(Button.B)),
                new ButtonConfig(Button.START, GameBoySettings.getInstance().getButton(Button.START)),
                new ButtonConfig(Button.SELECT, GameBoySettings.getInstance().getButton(Button.SELECT))
        };
    }

    /**
     * Get the button config for a key code
     *
     * @param keyCode The key code to get the button config for
     * @return The button config for the key code
     */
    private ButtonConfig getButtonConfigFromKeyCode(int keyCode) {
        for (ButtonConfig buttonConfig : buttonConfigs) {
            if (buttonConfig.isButtonMappedTo(keyCode)) {
                return buttonConfig;
            }
        }
        return null;
    }

    /**
     * Remap a button to a new key code
     *
     * @param button  The button to remap
     * @param keyCode The new key code to map the button to
     * @return True if the button was remapped, false if the key code is already mapped to another button
     */
    public boolean remapButton(Button button, int keyCode) {
        if (getButtonConfigFromKeyCode(keyCode) != null) {
            return false;
        }

        for (ButtonConfig buttonConfig : buttonConfigs) {
            if (buttonConfig.getButton() == button) {
                buttonConfig.setMappedKeyCode(keyCode);
                GameBoySettings.getInstance().setButton(button, keyCode);
            }
        }

        return true;
    }

    @Override
    public void setButtonListener(ButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        ButtonConfig pressedButton = getButtonConfigFromKeyCode(e.getKeyCode());
        if (pressedButton == null) {
            return;
        }
        buttonListener.onButtonPressed(pressedButton.getButton());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ButtonConfig pressedButton = getButtonConfigFromKeyCode(e.getKeyCode());
        if (pressedButton == null) {
            return;
        }
        buttonListener.onButtonReleased(pressedButton.getButton());
    }

    /**
     * Get the key code mapped to a button
     *
     * @param button The button to get the key code for
     * @return The key code mapped to the button
     */
    public int getButtonConfig(Button button) {
        for (ButtonConfig buttonConfig : buttonConfigs) {
            if (buttonConfig.getButton() == button) {
                return buttonConfig.getMappedKeyCode();
            }
        }
        return -1;
    }
}
