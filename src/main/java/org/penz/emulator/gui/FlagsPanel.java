package org.penz.emulator.gui;

import org.penz.emulator.cpu.Registers;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

class FlagsPanel extends JPanel {

    private final Registers registers;

    private final JLabel flagsLabel;

    public FlagsPanel(Registers registers) {
        this.registers = registers;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        add(new JLabel("Flags:"));
        flagsLabel = new JLabel(registers.getFlags().toString());
        add(flagsLabel);
    }

    public void updateValue() {
        flagsLabel.setText(registers.getFlags().toString());
    }
}
