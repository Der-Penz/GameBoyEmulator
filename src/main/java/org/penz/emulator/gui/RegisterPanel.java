package org.penz.emulator.gui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class RegisterPanel extends JPanel {

    private final IGetValue getValue;
    private final JLabel valueLabel;

    public RegisterPanel(String name, IGetValue getValue) {
        this.getValue = getValue;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        add(new JLabel(name + ":"));
        valueLabel = new JLabel("0x" + Integer.toHexString(getValue.get()));
        add(valueLabel);
    }

    public void updateValue() {
        valueLabel.setText("0x" + Integer.toHexString(getValue.get()));
    }

    @FunctionalInterface
    public interface IGetValue {
        int get();
    }
}
