package org.penz.emulator.graphics;

import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.graphics.enums.ObjSize;
import org.penz.emulator.graphics.enums.PixelType;
import org.penz.emulator.graphics.enums.TileDataArea;
import org.penz.emulator.memory.AddressSpace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents an object in the Game Boy
 * pixel fetching is done here instead of using the PixelFetcher
 */
public class Object {

    public static final int OBJECT_TICK_PENALTY = 6;
    private static final int Y_OFFSET = 16;

    private final int x;
    private final int y;
    private final int tileId;
    private final int flags;
    private boolean renderedOnScanline = false;

    public Object(int y, int x, int tileNumber, int flags) {
        this.y = y;
        this.x = x;
        this.tileId = tileNumber;
        this.flags = flags;
    }

    /**
     * creates an object from the given OAM data
     *
     * @param data the OAM data
     */
    public Object(int[] data) {
        this(data[0], data[1], data[2], data[3]);
    }

    /**
     * converts the given OAM data to an array of objects
     *
     * @param oam the OAM data
     * @return list of objects
     */
    public static List<Object> fromOAM(int[] oam) {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < oam.length; i += 4) {
            if (oam[i] != 0 || oam[i + 1] != 0 || oam[i + 2] != 0 || oam[i + 3] != 0) {
                objects.add(new Object(oam[i], oam[i + 1], oam[i + 2], oam[i + 3]));
            }
        }
        return objects;
    }

    /**
     * checks if the object is in the given scanline
     *
     * @param scanline the scanline to check
     * @param size     the size of the object
     * @return true if the object is in the given scanline
     */
    public boolean inScanline(int scanline, ObjSize size) {
        int height = size == ObjSize.SIZE_8x16 ? 16 : 8;
        return y - Y_OFFSET <= scanline && y - Y_OFFSET + height > scanline;
    }

    /**
     * checks if the object is in the given x coordinate
     *
     * @param x   the x coordinate to check
     * @return true if the object is in the given x coordinate
     */
    public boolean xIntersects(int x) {
        return this.x - 8 == x;
    }

    /**
     * fetches the pixel data for the object
     *
     * @param ly   the current scanline
     * @param vram the video ram
     * @return the 8 pixel color ids for the object
     */
    public List<Integer> fetchPixels(int ly, AddressSpace vram, ObjSize size) {
        renderedOnScanline = true;

        int tileId = this.tileId;
        if (size == ObjSize.SIZE_8x16) {
            boolean isBottomTile = (ly - y + Y_OFFSET) >= 8;
            if (isBottomTile) {
                tileId |= 0x01;
            } else {
                tileId &= 0xFE;
            }

        }

        TileDataArea tileDataArea = TileDataArea.AREA_2;
        int sizeOfTileRow = 16;

        int tileLine = (ly % 8) * 2;

        if (yFlip()) {
            tileLine = 14 - tileLine;
        }

        int tileDataAddress = tileDataArea.getBaseAddress() + tileId * sizeOfTileRow;
        tileDataAddress += tileLine;

        int lsb = vram.readByte(tileDataAddress);
        int msb = vram.readByte(tileDataAddress + 1);

        List<Integer> pixelData = Arrays.stream(PixelFetcher.pixelDataToColorId(lsb, msb)).boxed().toList();

        if (xFlip()) {
            return pixelData.reversed();
        }
        return pixelData;
    }

    /**
     * is the object x flipped
     *
     * @return true if the object is x flipped
     */
    public boolean xFlip() {
        return BitUtil.getBit(flags, 5);
    }

    /**
     * is the object y flipped
     *
     * @return true if the object is y flipped
     */
    public boolean yFlip() {
        return BitUtil.getBit(flags, 6);
    }

    /**
     * Does the background and window draw over the object
     *
     * @return true if the background and window is drawing over the object
     */
    public boolean bgPriority() {
        return BitUtil.getBit(flags, 7);
    }

    public int getFlags() {
        return flags;
    }

    /**
     * resets the rendered flag for the next scanline
     */
    public void resetRendered() {
        renderedOnScanline = false;
    }

    /**
     * was the object already rendered on the current scanline
     *
     * @return true if the object was already rendered
     */
    public boolean isRendered() {
        return renderedOnScanline;
    }

    /**
     * gets the pixel type of the object to be later used to determine the right palette
     *
     * @return the pixel type of the object
     */
    public PixelType getPixelType() {
        return BitUtil.getBit(flags, 4) ? PixelType.OBJ1 : PixelType.OBJ0;
    }

    /**
     * is the object visible in the viewport
     *
     * @param size the active object size
     * @return true if the object is visible
     */
    public boolean isVisible(ObjSize size) {
        if (size == ObjSize.SIZE_8x16) {
            return !((x == 0) || (x >= 168) || ((y >= 160) || (y == 0)));
        } else {
            return !((x == 0) || (x >= 168) || ((y >= 160) || (y <= 8)));
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTileId() {
        return tileId;
    }

}
