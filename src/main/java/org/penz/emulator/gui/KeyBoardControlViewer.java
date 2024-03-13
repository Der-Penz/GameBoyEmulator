package org.penz.emulator.gui;

import org.penz.emulator.input.Button;
import org.penz.emulator.input.KeyboardController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardControlViewer extends JFrame {

    private final KeyboardController controls;
    boolean remapActive = false;

    public KeyBoardControlViewer(KeyboardController controls) {
        this.controls = controls;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("KeyBoard Controls");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(9, 2, 10, 5));

        add(new JLabel("Gameboy Key"));
        add(new JLabel("Keyboard Key"));

        for (Button button : Button.values()) {
            JLabel button1 = new JLabel(button.name());

            int keycode = controls.getButtonConfig(button);
            JTextField field = new JTextField();
            field.setEditable(false);
            if (keycode == -1) {
                field.setText("Not Set");
            } else {
                field.setText(KeyEvent.getKeyText(keycode));
            }

            field.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (controls.remapButton(button, e.getKeyCode())) {
                        field.setText(KeyEvent.getKeyText(e.getKeyCode()));

                    } else {
                        JOptionPane.showMessageDialog(null, "Key already mapped to another button");
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });

            add(button1);
            add(field);
        }

        pack();
        setVisible(true);
    }


}
