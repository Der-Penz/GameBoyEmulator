package org.penz.emulator.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardController implements IButtonController, KeyListener {

    private ButtonListener buttonListener;

    public KeyboardController() {
        this.buttonListener = null;
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
        Button pressedButton = getButtonFromKeyEvent(e);
        if (pressedButton == null) {
            return;
        }
        buttonListener.onButtonPressed(pressedButton);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Button releasedButton = getButtonFromKeyEvent(e);
        if (releasedButton == null) {
            return;
        }
        buttonListener.onButtonReleased(releasedButton);
    }

    private Button getButtonFromKeyEvent(KeyEvent e) {
        return switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> Button.UP;
            case KeyEvent.VK_DOWN -> Button.DOWN;
            case KeyEvent.VK_LEFT -> Button.LEFT;
            case KeyEvent.VK_RIGHT -> Button.RIGHT;
            case KeyEvent.VK_Z -> Button.A;
            case KeyEvent.VK_X -> Button.B;
            case KeyEvent.VK_ENTER -> Button.START;
            case KeyEvent.VK_BACK_SPACE -> Button.SELECT;
            default -> null;
        };
    }
}
