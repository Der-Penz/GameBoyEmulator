package org.penz.emulator.gui;

import org.penz.emulator.GameBoySettings;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.graphics.Object;
import org.penz.emulator.graphics.Pixel;
import org.penz.emulator.graphics.enums.ObjSize;
import org.penz.emulator.graphics.enums.PixelType;
import org.penz.emulator.memory.AddressSpace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class ObjectsViewer extends DebugFrame {

    private final AddressSpace addressSpace;
    private final List<TilePanel> tilePanels = new ArrayList<>();
    private ObjectDetailPanel detailsPanel;
    private int selectedObject = 0;

    public ObjectsViewer(AddressSpace addressSpace) {
        super("Objects Viewer");
        this.addressSpace = addressSpace;

        initializeUI();
    }

    void initializeUI() {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        JPanel objectsPanel = new JPanel();
        detailsPanel = new ObjectDetailPanel();

        objectsPanel.setLayout(new GridLayout(5, 8, 5, 5));

        for (int i = 0; i < 40; i++) {
            tilePanels.add(getEmptyPanel(i));
            objectsPanel.add(tilePanels.get(i));
        }

        detailsPanel.setPreferredSize(new Dimension(75, 100));

        add(objectsPanel);
        add(detailsPanel);

        pack();
        setResizable(false);
        setVisible(true);
    }

    private List<Object> getObjects() {
        return Object.fromOAM(AddressSpace.readRange(addressSpace, 0xFE00, 160));
    }

    private TilePanel getEmptyPanel(int index) {
        TilePanel tilePanel = new TilePanel(6);
        toEmptyPanel(tilePanel);

        tilePanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedObject = index;
                try {
                    detailsPanel.updateDetails(selectedObject, getObjects().get(selectedObject));
                } catch (Exception ex) {
                    //selecting empty object does nothing
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        return tilePanel;
    }

    private void toEmptyPanel(TilePanel tilePanel) {
        Integer[] pixels = new Integer[64];
        for (int i = 0; i < 64; i++) {
            pixels[i] = i % 9 == 0 ? 0xFF0000 : 0xFFFFFF;
        }
        tilePanel.setPixels(pixels);
        tilePanel.setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    public void updateFrame() {
        List<Object> objects = getObjects();


        ObjSize size = BitUtil.getBit(addressSpace.readByte(0xFF40), 2) ? ObjSize.SIZE_8x16 : ObjSize.SIZE_8x8;

        for (int i = 0; i < objects.size(); i++) {
            var object = objects.get(i);
            var tilePanel = tilePanels.get(i);

            int objectPalette = object.getPixelType() == PixelType.OBJ0 ? addressSpace.readByte(0xFF48) : addressSpace.readByte(0xFF49);

            List<Integer> pixels = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                pixels.addAll(object.fetchPixels(j, 0, addressSpace, size)
                        .stream()
                        .map(pixel -> new Pixel(pixel, object.getPixelType()).toHexColor(objectPalette, GameBoySettings.getInstance().getPalette()))
                        .toList());
            }
            tilePanel.setPixels(pixels.toArray(Integer[]::new));

            if (!object.isVisible(size)) {
                tilePanel.setBorder(BorderFactory.createLineBorder(java.awt.Color.RED, 2));
            } else {
                tilePanel.setBorder(BorderFactory.createEmptyBorder());
            }

        }

        for (int i = objects.size(); i < 40; i++) {
            toEmptyPanel(tilePanels.get(i));
        }

        try {
            detailsPanel.updateDetails(selectedObject, objects.get(selectedObject));
        } catch (Exception e) {
            //selecting empty object does nothing
        }
    }

}
