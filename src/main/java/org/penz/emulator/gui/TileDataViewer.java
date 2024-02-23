package org.penz.emulator.gui;

import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.graphics.PixelFetcher;
import org.penz.emulator.graphics.enums.Palette;
import org.penz.emulator.graphics.enums.TileDataArea;
import org.penz.emulator.graphics.enums.TileMapArea;
import org.penz.emulator.memory.AddressSpace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class TileDataViewer extends JFrame {

    private static final int TILES_PER_ROW = 32;
    private final AddressSpace mmu;

    private TilePanel[] tiles;

    private TileMapArea tileMapArea;

    private TileDataArea tileDataArea;

    public TileDataViewer(AddressSpace mmu, int frame) {
        this.tileMapArea = TileMapArea.AREA_1;
        this.tileDataArea = TileDataArea.AREA_2;
        this.mmu = mmu;
        initializeUI(frame);
    }

    private void initializeUI(int frame) {
        setTitle("Tile Data Viewer, Frame: " + frame);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        setResizable(false);
        setMaximumSize(new Dimension(256, 256));
        setSize(500, 500);


        JMenuBar menuBar = new JMenuBar();
        JMenu tileMapAreaMenu = new JMenu("Tile map");
        tileMapAreaMenu.setMnemonic(KeyEvent.VK_M);

        JMenuItem tileMap1 = new JMenuItem("Area 1");
        tileMap1.addActionListener(e -> {
            tileMapArea = TileMapArea.AREA_1;
            updateTileData();
        });

        JMenuItem tileMap2 = new JMenuItem("Area 2");
        tileMap2.addActionListener(e -> {
            tileMapArea = TileMapArea.AREA_2;
            updateTileData();
        });

        JMenu tileDataAreaMenu = new JMenu("Tile data");
        tileDataAreaMenu.setMnemonic(KeyEvent.VK_D);

        JMenuItem tileData1 = new JMenuItem("Area 1");
        tileData1.addActionListener(e -> {
            tileDataArea = TileDataArea.AREA_1;
            updateTileData();
        });

        JMenuItem tileData2 = new JMenuItem("Area 2");
        tileData2.addActionListener(e -> {
            tileDataArea = TileDataArea.AREA_2;
            updateTileData();
        });

        tiles = new TilePanel[TILES_PER_ROW * TILES_PER_ROW];
        for (int i = 0; i < TILES_PER_ROW * TILES_PER_ROW; i++) {
            tiles[i] = new TilePanel();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.ipadx = -2;
            gbc.ipady = -2;

            gbc.gridx = i % TILES_PER_ROW;
            gbc.gridy = i / TILES_PER_ROW;
            add(tiles[i], gbc);
        }

        tileMapAreaMenu.add(tileMap1);
        tileMapAreaMenu.add(tileMap2);

        tileDataAreaMenu.add(tileData1);
        tileDataAreaMenu.add(tileData2);

        menuBar.add(tileMapAreaMenu);
        menuBar.add(tileDataAreaMenu);
        setJMenuBar(menuBar);


        setLocationRelativeTo(null);
        requestFocus();
        setVisible(true);
    }

    public void updateTileData() {
        Palette palette = Palette.GRAYSCALE;
        for (int i = 0; i < tiles.length; i++) {
            TilePanel current = tiles[i];

            int tileId = fetchTileId(i);
            int[] tileData = fetchTileData(tileId);
            Integer[] colors = new Integer[64];
            for (int j = 0; j < 16; j += 2) {
                int lsb = tileData[j];
                int msb = tileData[j + 1];

                Integer[] colorIds = PixelFetcher.pixelDataToColorId(lsb, msb);

                Integer[] rowOfColors = Arrays.stream(colorIds).map(palette::getColorById).toArray(Integer[]::new);
                System.arraycopy(rowOfColors, 0, colors, j * 4, 8);
            }
            current.setPixel(colors);
        }
    }

    private int fetchTileId(int index) {
        int address = tileMapArea.getStartAddress() + index;
        return mmu.readByte(address);
    }

    private int[] fetchTileData(int tileId) {
        int tileSize = 16;
        int address;
        if (tileDataArea == TileDataArea.AREA_1) {
            address = tileDataArea.getBaseAddress() + BitUtil.toSignedByte(tileId) * tileSize;
        } else {
            address = tileDataArea.getBaseAddress() + (tileId * tileSize);
        }
        return AddressSpace.readRange(mmu, address, tileSize);
    }

}
