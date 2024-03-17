package org.penz.emulator.gui;

import javax.swing.*;

public abstract class DebugFrame extends JFrame {

    public DebugFrame(String name) {
        setTitle(name);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    abstract void initializeUI();

    abstract void updateFrame();
}
