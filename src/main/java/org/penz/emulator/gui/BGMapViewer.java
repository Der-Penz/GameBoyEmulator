package org.penz.emulator.gui;

import org.penz.emulator.GameBoy;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.graphics.Pixel;
import org.penz.emulator.graphics.PixelFetcher;
import org.penz.emulator.graphics.enums.Palette;
import org.penz.emulator.graphics.enums.TileDataArea;
import org.penz.emulator.graphics.enums.TileMapArea;
import org.penz.emulator.memory.AddressSpace;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class BGMapViewer extends JFrame {

    private static final int TILES_PER_ROW = 32;

    private static final int TILE_SIZE = 8;
    private final GameBoy gameBoy;
    private TileMapArea tileMapArea;
    private TileDataArea tileDataArea;
    private ViewportImageDisplay imagePanel;

    public BGMapViewer(GameBoy gameBoy) {
        this.tileMapArea = TileMapArea.AREA_1;
        this.tileDataArea = TileDataArea.AREA_2;
        this.gameBoy = gameBoy;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Background map Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

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

        tileMapAreaMenu.add(tileMap1);
        tileMapAreaMenu.add(tileMap2);

        tileDataAreaMenu.add(tileData1);
        tileDataAreaMenu.add(tileData2);

        menuBar.add(tileMapAreaMenu);
        menuBar.add(tileDataAreaMenu);
        setJMenuBar(menuBar);

        imagePanel = new ViewportImageDisplay(TILES_PER_ROW * TILE_SIZE, TILES_PER_ROW * TILE_SIZE, 2);
        add(imagePanel);

        pack();
        setLocationRelativeTo(null);
        requestFocus();
        setVisible(true);
    }

    public void updateTileData() {
        Palette palette = Palette.GRAYSCALE;
        int indexPalette = gameBoy.getMemory().readByte(0xFF47);

        int[] buffer = new int[TILES_PER_ROW * TILES_PER_ROW * TILE_SIZE * TILE_SIZE];
        for (int i = 0; i < TILES_PER_ROW * TILES_PER_ROW; i++) {
            int tileId = fetchTileId(i);
            int[] tileData = fetchTileData(tileId);

            for (int j = 0; j < 16; j += 2) {
                int lsb = tileData[j];
                int msb = tileData[j + 1];

                int[] colorIds = PixelFetcher.pixelDataToColorId(lsb, msb);
                for (int k = 0; k < TILE_SIZE; k++) {
                    var pixel = Pixel.createBgPixel(colorIds[k]);
                    pixel.toHexColor(indexPalette, palette);
                    colorIds[k] = palette.getColorByIndex(colorIds[k]);
                }

                int x = (i % TILES_PER_ROW) * TILE_SIZE;
                int y = (j / 2) + (i / TILES_PER_ROW) * TILE_SIZE;
                int pos = (y * TILES_PER_ROW * TILE_SIZE) + (x);

                System.arraycopy(colorIds, 0, buffer, pos, TILE_SIZE);
            }
        }
        imagePanel.putPixel(buffer);
        imagePanel.setViewport(gameBoy.getMemory().readByte(0xFF42), gameBoy.getMemory().readByte(0xFF43));
        imagePanel.paintImage();
    }

    private int fetchTileId(int index) {
        int address = tileMapArea.getStartAddress() + index;
        return gameBoy.getMemory().readByte(address);
    }

    private int[] fetchTileData(int tileId) {
        int tileSize = 16;
        int address;
        if (tileDataArea == TileDataArea.AREA_1) {
            address = tileDataArea.getBaseAddress() + BitUtil.toSignedByte(tileId) * tileSize;
        } else {
            address = tileDataArea.getBaseAddress() + (tileId * tileSize);
        }
        return AddressSpace.readRange(gameBoy.getMemory(), address, tileSize);
    }

}
