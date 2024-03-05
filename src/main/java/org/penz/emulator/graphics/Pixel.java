package org.penz.emulator.graphics;

import org.penz.emulator.graphics.enums.Palette;
import org.penz.emulator.graphics.enums.PixelType;

public record Pixel(int pixelId, PixelType pixelType) {

    /**
     * create a new BG pixel
     *
     * @param pixelId the color ID
     * @return the new pixel
     */
    public static Pixel createBgPixel(int pixelId) {
        return new Pixel(pixelId, PixelType.BG);
    }

    /**
     * whether this pixel is an object pixel (OBJ0 or OBJ1)
     *
     * @return true if this pixel is an object pixel
     */
    public boolean isObjPixel() {
        return pixelType == PixelType.OBJ0 || pixelType == PixelType.OBJ1;
    }

    /**
     * returns the index of a color in the color palette from a given ID and index palette
     *
     * @param palette        the index palette to use
     * @param pixelPaletteID the ID of the
     * @return the index of the color in the color palette
     */
    private int paletteIndexFromId(int palette, int pixelPaletteID) {
        return (palette >> (2 * pixelPaletteID)) & 0b11;
    }

    /**
     * Returns the color of this pixel in HEX RGB format
     *
     * @param indexPalette the index palette to use
     * @param colorPalette the color palette to use
     * @return color in HEX RGB format
     */
    public int toHexColor(int indexPalette, Palette colorPalette) {
        return colorPalette.getColorByIndex(paletteIndexFromId(indexPalette, pixelId));
    }
}
