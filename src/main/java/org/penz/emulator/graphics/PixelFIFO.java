package org.penz.emulator.graphics;

import org.penz.emulator.graphics.enums.Palette;
import org.penz.emulator.memory.AddressSpace;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class PixelFIFO implements AddressSpace {

    private final Display display;
    private int x;

    private int scy;

    private int scx;

    private int wy;

    private int wx;

    private int counter;

    private final PixelFetcher pixelFetcher;

    private final Queue<Integer> pixelQueue;

    private final LcdControl lcdControl;
    private final LcdRegister lcdRegister;

    public PixelFIFO(Display display, PixelFetcher pixelFetcher, LcdControl lcdControl, LcdRegister lcdRegister) {
        this.display = display;
        this.pixelFetcher = pixelFetcher;
        this.pixelQueue = new ArrayDeque<>();
        this.lcdControl = lcdControl;
        this.lcdRegister = lcdRegister;

        reset();
    }

    public void reset() {
        x = 0;
        counter = 0;
        pixelQueue.clear();
    }

    public void tick() {
        counter++;
        if (counter == 2) {
            counter = 0;
            if (pixelFetcher.isPixelDataReady()) {
                pixelQueue.addAll(Arrays.stream(pixelFetcher.pullPixelData()).toList());
            }
            pixelFetcher.fetch(x, scx, scy);
        }


        if (pixelQueue.size() > 8) {
            display.putPixel(x, lcdRegister.getLY(), getNextPixel());
            x++;
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

        Palette palette = Palette.PALETTE_1;

        return palette.getColorById(pixelPaletteID);
    }

    public int getX() {
        return x;
    }

    private boolean inWindow() {
        return lcdControl.isWindowEnabled() && x >= wx && x <= 166 && lcdRegister.getLY() >= wy && wy <= 143;
    }


    @Override
    public boolean accepts(int address) {
        return address == 0xFF42 || address == 0xFF43 || address == 0xFF4A || address == 0xFF4B;
    }

    @Override
    public void writeByte(int address, int value) {
        switch (address) {
            case 0xFF42 -> scy = value;
            case 0xFF43 -> scx = value;
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
            default -> 0x00;
        };
    }
}

