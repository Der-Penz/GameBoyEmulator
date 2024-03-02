package org.penz.emulator.graphics;

import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.graphics.enums.Palette;
import org.penz.emulator.memory.AddressSpace;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class PixelFIFO implements AddressSpace {

    private final IDisplay display;
    private final PixelFetcher pixelFetcher;
    private final Queue<Integer> pixelQueue;
    private final LcdControl lcdControl;
    private final LcdRegister lcdRegister;
    private int x;
    private int scy;
    private int scx;
    private int wy;
    private int wx;
    private int counter;
    private int xShift;
    private int bgPalette;

    private int objPalette0;
    private int objPalette1;
    private List<Object> objectsOnScanline = null;

    private int objectPenaltyTicks = 0;

    public PixelFIFO(IDisplay display, PixelFetcher pixelFetcher, LcdControl lcdControl, LcdRegister lcdRegister) {
        this.display = display;
        this.pixelFetcher = pixelFetcher;
        this.pixelQueue = new ArrayDeque<>();
        this.lcdControl = lcdControl;
        this.lcdRegister = lcdRegister;
    }

    public void startScanline() {
        x = 0;
        xShift = scx % 8;
        counter = 0;
        pixelQueue.clear();
        pixelFetcher.reset(scx, scy, false);
        objectPenaltyTicks = 0;

        if (objectsOnScanline != null) {
            objectsOnScanline.forEach(Object::resetRendered);
        }
    }

    public void tick() {
        if (objectPenaltyTicks > 0) {
            objectPenaltyTicks--;
            return;
        }
        counter++;

        Object object = checkForObject();
        if (object != null) {
            List<Integer> pixels = object.fetchPixels(lcdRegister.getLY(), scy, pixelFetcher.getVram(), lcdControl.getObjSize());
            addCombinedObjPixelData(pixels);
            objectPenaltyTicks += Object.OBJECT_TICK_PENALTY;
        }

        if (inWindow() && !pixelFetcher.isWindowFetching()) {
            pixelQueue.clear();
            xShift = 0;
            pixelFetcher.reset(0, 0, true);
        }

        if (counter == 2) {
            counter = 0;
            pixelFetcher.fetch();
            if (pixelFetcher.isPixelDataReady()) {
                pixelQueue.addAll(Arrays.stream(pixelFetcher.pullPixelData()).toList());
            }
        }

        if (pixelQueue.size() > 8) {
            if (xShift > 0) {
                pixelQueue.poll();
                xShift--;
                return;
            }
            display.putPixel(x, lcdRegister.getLY(), getNextPixel());
        }
    }

    /**
     * Returns the color of the next pixel to be drawn on the screen in HEX RGB format
     *
     * @return color in HEX RGB format
     */
    public int getNextPixel() {
        var pixelPaletteID = pixelQueue.poll();

        if (pixelPaletteID == null) {
            return 0;
        }
        int paletteIndex = (bgPalette >> (2 * pixelPaletteID)) & 0b11;
        x++;

        Palette palette = Palette.GRAYSCALE;
        return palette.getColorById(paletteIndex);
    }

    private void addCombinedObjPixelData(List<Integer> objPixelData) {
        Integer[] pixelArray = pixelQueue.toArray(Integer[]::new);

        //TODO Selecting the palette
        //TODO handling sprite overlap, remember pixel authority
        for (int i = 0; i < 8; i++) {
            int pixel = objPixelData.get(i);

            if (pixel != 0) {
                pixelArray[i] = pixel;
            }
        }

        pixelQueue.clear();
        pixelQueue.addAll(List.of(pixelArray));

    }

    public int getX() {
        return x;
    }

    /**
     * Checks if the current pixel is in the window
     *
     * @return true if the current pixel is in the window
     */
    private boolean inWindow() {
        return lcdControl.isWindowEnabled() && lcdControl.isBackgroundWindowEnabled() && x >= wx - 7 && x <= 166 && lcdRegister.getLY() >= wy && wy <= 143;
    }


    @Override
    public boolean accepts(int address) {
        return address == 0xFF42 || address == 0xFF43 || BitUtil.inRange(address, 0xFF47, 0xFF4B);
    }

    @Override
    public void writeByte(int address, int value) {
        switch (address) {
            case 0xFF42 -> scy = value;
            case 0xFF43 -> scx = value;
            case 0xFF47 -> bgPalette = value;
            case 0xFF48 -> objPalette0 = value;
            case 0xFF49 -> objPalette1 = value;
            case 0xFF4A -> wy = value;
            case 0xFF4B -> wx = value;
        }
    }

    @Override
    public int readByte(int address) {
        return switch (address) {
            case 0xFF42 -> scy;
            case 0xFF43 -> scx;
            case 0xFF4A -> wy;
            case 0xFF4B -> wx;
            case 0xFF47 -> bgPalette;
            case 0xFF48 -> objPalette0;
            case 0xFF49 -> objPalette1;
            default -> 0x00;
        };
    }

    /**
     * Checks if there is an object to be rendered on the current pixel
     *
     * @return the object to be rendered or null if there is none
     */
    private Object checkForObject() {
        if (!lcdControl.isObjEnabled()) {
            return null;
        }

        return objectsOnScanline.stream().filter(object -> object.xIntersects(x, scx) && !object.isRendered()).findFirst().orElse(null);
    }

    /**
     * Sets the objects to be rendered on the current scanline
     *
     * @param objectsOnScanline the objects to be rendered on the current scanline
     */
    public void setObjectsOnScanline(List<Object> objectsOnScanline) {
        this.objectsOnScanline = objectsOnScanline;
    }

    /**
     * Checks if there are objects to be rendered on the current scanline
     *
     * @return true if there are objects to be rendered on the current scanline
     */
    public boolean hasObjects() {
        return objectsOnScanline != null;
    }
}

