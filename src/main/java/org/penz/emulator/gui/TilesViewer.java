package org.penz.emulator.gui;

import org.penz.emulator.GameBoy;
import org.penz.emulator.graphics.Pixel;
import org.penz.emulator.graphics.PixelFetcher;
import org.penz.emulator.graphics.enums.Palette;
import org.penz.emulator.graphics.enums.TileDataArea;
import org.penz.emulator.memory.AddressSpace;

import javax.swing.*;

public class TilesViewer extends JFrame {

    private static final int NUMBER_OF_TILES = 128 * 3;
    private static final int TILES_PER_ROW = 16;
    private static final int TILES_PER_COLUMN = NUMBER_OF_TILES / TILES_PER_ROW;
    private static final int TILE_SIZE = 8;
    private final GameBoy gameBoy;
    private ImageDisplay imagePanel;

    public TilesViewer(GameBoy gameBoy) {
        this.gameBoy = gameBoy;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Tiles Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        imagePanel = new ImageDisplay(TILES_PER_ROW * TILE_SIZE, TILES_PER_COLUMN * TILE_SIZE, 3);
        add(imagePanel);

        pack();
        setLocationRelativeTo(null);
        requestFocus();
        setVisible(true);
    }

    public void updateTileData() {
        Palette palette = Palette.GRAYSCALE;
        int indexPalette = gameBoy.getMemory().readByte(0xFF47);

        int[] buffer = new int[NUMBER_OF_TILES * TILE_SIZE * TILE_SIZE];
        for (int i = 0; i < NUMBER_OF_TILES; i++) {
            int[] tileData = fetchTileData(i);

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
                int pos = (y * TILES_PER_ROW * TILE_SIZE) + x;

                System.arraycopy(colorIds, 0, buffer, pos, TILE_SIZE);
            }
        }
        imagePanel.putPixel(buffer);
        imagePanel.paintImage();
    }

    private int[] fetchTileData(int tileId) {
        int tileSize = 16;
        int address;
        int baseAddress = TileDataArea.AREA_2.getBaseAddress();

        address = baseAddress + (tileId * tileSize);
        return AddressSpace.readRange(gameBoy.getMemory(), address, tileSize);
    }

}
