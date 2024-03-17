package org.penz.emulator.gui;

import org.penz.emulator.cpu.Registers;
import org.penz.emulator.memory.AddressSpace;

import java.awt.*;
import java.util.ArrayList;

public class RegisterViewer extends DebugFrame {

    private final Registers registers;

    private final AddressSpace memory;

    private final ArrayList<RegisterPanel> registerPanels = new ArrayList<>();

    private final FlagsPanel flagsPanel;

    public RegisterViewer(Registers registers, AddressSpace memory) {
        super("Register Viewer");
        this.registers = registers;
        this.memory = memory;
        flagsPanel = new FlagsPanel(registers);
        initializeUI();
    }


    void initializeUI() {
        setLayout(new GridLayout(14, 2, 1, 1));

        registerPanels.add(new RegisterPanel("PC", registers::getPC));
        registerPanels.add(new RegisterPanel("SP", registers::getSP));
        registerPanels.add(new RegisterPanel("A", registers::getA));
        registerPanels.add(new RegisterPanel("B", registers::getB));
        registerPanels.add(new RegisterPanel("C", registers::getC));
        registerPanels.add(new RegisterPanel("D", registers::getD));
        registerPanels.add(new RegisterPanel("E", registers::getE));
        registerPanels.add(new RegisterPanel("H", registers::getH));
        registerPanels.add(new RegisterPanel("L", registers::getL));
        registerPanels.add(new RegisterPanel("DIV", () -> memory.readByte(0xff04)));
        registerPanels.add(new RegisterPanel("TIMA", () -> memory.readByte(0xff05)));
        registerPanels.add(new RegisterPanel("TMA", () -> memory.readByte(0xff06)));
        registerPanels.add(new RegisterPanel("TAC", () -> memory.readByte(0xff07)));
        registerPanels.add(new RegisterPanel("LCDC", () -> memory.readByte(0xff40)));
        registerPanels.add(new RegisterPanel("STAT", () -> memory.readByte(0xff41)));
        registerPanels.add(new RegisterPanel("SCY", () -> memory.readByte(0xff42)));
        registerPanels.add(new RegisterPanel("SCX", () -> memory.readByte(0xff43)));
        registerPanels.add(new RegisterPanel("LY", () -> memory.readByte(0xff44)));
        registerPanels.add(new RegisterPanel("LYC", () -> memory.readByte(0xff45)));
        registerPanels.add(new RegisterPanel("DMA", () -> memory.readByte(0xff46)));
        registerPanels.add(new RegisterPanel("BGP", () -> memory.readByte(0xff47)));
        registerPanels.add(new RegisterPanel("OBP0", () -> memory.readByte(0xff48)));
        registerPanels.add(new RegisterPanel("OBP1", () -> memory.readByte(0xff49)));
        registerPanels.add(new RegisterPanel("WY", () -> memory.readByte(0xff4a)));
        registerPanels.add(new RegisterPanel("WX", () -> memory.readByte(0xff4b)));
        registerPanels.add(new RegisterPanel("IE", () -> memory.readByte(0xffff)));
        registerPanels.add(new RegisterPanel("IF", () -> memory.readByte(0xff0f)));

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

    @Override
    public void updateFrame() {
        updateRegisterData();
    }

}