package org.penz.emulator.gui;

import org.penz.emulator.cpu.Registers;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RegisterViewer extends JFrame {

    private final Registers registers;

    private final ArrayList<RegisterPanel> registerPanels = new ArrayList<>();

    private final FlagsPanel flagsPanel;

    public RegisterViewer(Registers registers) {
        this.registers = registers;
        flagsPanel = new FlagsPanel(registers);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Register Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(8, 2, -1, -1));

        registerPanels.add(new RegisterPanel("PC", registers::getPC));
        registerPanels.add(new RegisterPanel("SP", registers::getSP));
        registerPanels.add(new RegisterPanel("A", registers::getA));
        registerPanels.add(new RegisterPanel("B", registers::getB));
        registerPanels.add(new RegisterPanel("C", registers::getC));
        registerPanels.add(new RegisterPanel("D", registers::getD));
        registerPanels.add(new RegisterPanel("E", registers::getE));
        registerPanels.add(new RegisterPanel("H", registers::getH));
        registerPanels.add(new RegisterPanel("L", registers::getL));

        registerPanels.forEach(this::add);

        add(flagsPanel);

        pack();
        setVisible(true);
        transferFocus();
    }

    public void updateRegisterData() {
        registerPanels.forEach(RegisterPanel::updateValue);
        flagsPanel.updateValue();
    }

}