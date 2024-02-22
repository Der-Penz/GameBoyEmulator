package org.penz.emulator.graphics;

import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.graphics.enums.PixelFetcherState;
import org.penz.emulator.graphics.enums.TileDataArea;
import org.penz.emulator.graphics.enums.TileMapArea;
import org.penz.emulator.memory.AddressSpace;

import java.util.ArrayList;

public class PixelFetcher {

    private final AddressSpace vram;

    private final LcdControl lcdControl;

    private final LcdRegister lcdRegister;
    private final ArrayList<Integer> currentTileData;
    private PixelFetcherState state;
    private int currentTileId;

    private boolean discardFirstFetch;

    public PixelFetcher(AddressSpace vram, LcdControl lcdControl, LcdRegister lcdRegister) {
        this.vram = vram;
        this.lcdControl = lcdControl;
        this.lcdRegister = lcdRegister;
        this.state = PixelFetcherState.READ_TILE_ID;
        currentTileData = new ArrayList<>();

    }

    /**
     * Fetches the Tile data for the next 8 pixel from VRAM.
     * Depending on the current state it will perform different actions
     *
     * @return true if the next 8 pixel are ready to be pulled
     */
    public void fetch(int x, int scx, int scy) {
        switch (state) {
            case READ_TILE_ID:

                readTileId(x, scx, scy);
                break;
            case READ_TILE_DATA_1, READ_TILE_DATA_2:
                readTileData(scy);
                break;
            case IDLE:
                break;
        }

        if (!discardFirstFetch && isPixelDataReady()) {
            discardFirstFetch = true;
            currentTileData.clear();
            return;
        }
        state = getNextState();
    }

    private void readTileId(int x, int scx, int scy) {
        TileMapArea tileMapArea = lcdControl.getBackgroundTileMapArea();

        // TODO window support
        int fetcherX = ((scx + x) / 8) & 0x1F;
        int fetcherY = (lcdRegister.getLY() + scy) & 0xFF;
        int yPos = (fetcherY / 8) * 32;

        int address = tileMapArea.getStartAddress() + yPos + fetcherX + 1;

        currentTileId = vram.readByte(address);
    }

    /**
     * Reads tile data from VRAM into currentTileData array
     * uses currentTileId to determine which tile to read and depending on
     * state reads either low or high byte
     */
    private void readTileData(int scy) {
        //TODO check for object tile data if enabled
        TileDataArea tileDataArea = lcdControl.getTileDataArea();
        boolean isLowByte = (state == PixelFetcherState.READ_TILE_DATA_1);
        int tileData;

        int sizeOfTileRow = 16;
        int tileLine = (lcdRegister.getLY() + scy) % 8;
        if (tileDataArea == TileDataArea.AREA_1) {
            int tileDataAddress = tileDataArea.getBaseAddress() + BitUtil.toSignedByte(currentTileId) * sizeOfTileRow;
            tileDataAddress += tileLine * 2;
            if (!isLowByte) tileDataAddress += 1;
            tileData = vram.readByte(tileDataAddress);
        } else {
            int tileDataAddress = tileDataArea.getBaseAddress() + currentTileId * sizeOfTileRow;
            tileDataAddress += tileLine * 2;
            if (!isLowByte) tileDataAddress += 1;
            tileData = vram.readByte(tileDataAddress);
        }

        currentTileData.add(tileData);
    }

    private PixelFetcherState getNextState() {
        return switch (state) {
            case READ_TILE_ID -> PixelFetcherState.READ_TILE_DATA_1;
            case READ_TILE_DATA_1 -> PixelFetcherState.READ_TILE_DATA_2;
            case READ_TILE_DATA_2 -> PixelFetcherState.IDLE;
            case IDLE -> PixelFetcherState.READ_TILE_ID;
        };
    }

    public boolean isPixelDataReady() {
        return currentTileData.size() >= 2 && state == PixelFetcherState.IDLE;
    }

    /**
     * Pulls the current tile data from the pixel fetcher
     * and resets the current tile data
     * Only works if a full 2 byte tile data is available
     * Converts the 2 byte tile data into 8 pixel data
     *
     * @return 8 pixel color data in an array
     */
    public Integer[] pullPixelData() {
        if (currentTileData.size() < 2) throw new IllegalStateException("Not enough tile data available");
        int lsb = currentTileData.get(0);
        int msb = currentTileData.get(1);
        currentTileData.clear();

        return PixelFetcher.pixelDataToColorId(lsb, msb);
    }

    /**
     * Converts the 2 byte tile data into 8 pixel data id's
     *
     * @param lsb low byte
     * @param msb high byte
     * @return 8 pixel color id's
     */
    public static Integer[] pixelDataToColorId(int lsb, int msb) {
        Integer[] pixelData = new Integer[8];
        for (int i = 0; i < 8; i++) {
            pixelData[7 - i] = 0;
            pixelData[7 - i] |= (BitUtil.getBit(msb, i) ? 1 : 0) << 1;
            pixelData[7 - i] |= BitUtil.getBit(lsb, i) ? 1 : 0;
        }

        return pixelData;
    }

    public void reset() {
        state = PixelFetcherState.READ_TILE_ID;
        currentTileData.clear();
        discardFirstFetch = false;
    }
}
