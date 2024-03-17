package org.penz.emulator.gui;

import org.penz.emulator.GameBoySettings;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.graphics.Object;
import org.penz.emulator.graphics.Pixel;
import org.penz.emulator.graphics.enums.ObjSize;
import org.penz.emulator.graphics.enums.PixelType;
import org.penz.emulator.memory.AddressSpace;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectsViewer extends DebugFrame {

    private final AddressSpace addressSpace;

    private JPanel objectsPanel;
    private JPanel detailsPanel;
    private List<Object> objects;

    public ObjectsViewer(AddressSpace addressSpace) {
        super("Objects Viewer");
        this.addressSpace = addressSpace;
        initializeUI();
    }

    void initializeUI() {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        objectsPanel = new JPanel();
        detailsPanel = new JPanel();

        add(objectsPanel);
        add(detailsPanel);

        pack();
        setResizable(false);
        setVisible(true);
    }

    private List<Object> getObjects() {
        return Object.fromOAM(AddressSpace.readRange(addressSpace, 0xFE00, 160));
    }


    @Override
    public void updateFrame() {
        objects = getObjects();

        while (objects.size() < 40) {
            objects.add(null);
        }

        objectsPanel.removeAll();
        objects.forEach(object -> {
            if (object == null) {
                TilePanel tilePanel = new TilePanel(3);
                Integer[] pixels = new Integer[64];
                for (int i = 0; i < 64; i++) {
                    pixels[i] = i % 9 == 0 ? 0xFF0000 : 0xFFFFFF;
                }
                tilePanel.setPixels(pixels);
                objectsPanel.add(tilePanel);
                return;
            }

            int objectPalette = object.getPixelType() == PixelType.OBJ0 ? addressSpace.readByte(0xFF48) : addressSpace.readByte(0xFF49);
            ObjSize size = BitUtil.getBit(addressSpace.readByte(0xFF40), 2) ? ObjSize.SIZE_8x16 : ObjSize.SIZE_8x8;
            TilePanel tilePanel = new TilePanel(3);
            List<Integer> pixels = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                pixels.addAll(object.fetchPixels(i, 0, addressSpace, size)
                        .stream()
                        .map(pixel -> new Pixel(pixel, object.getPixelType()).toHexColor(objectPalette, GameBoySettings.getInstance().getPalette()))
                        .toList());
            }
            tilePanel.setPixels(pixels.toArray(Integer[]::new));
            if (!object.isVisible(size)) {
                tilePanel.setBorder(BorderFactory.createLineBorder(java.awt.Color.RED, 1));
            }
            objectsPanel.add(tilePanel);
        });
        pack();
    }

}
