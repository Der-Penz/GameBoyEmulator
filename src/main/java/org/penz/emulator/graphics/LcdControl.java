package org.penz.emulator.graphics;

import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.graphics.enums.ObjSize;
import org.penz.emulator.graphics.enums.TileDataArea;
import org.penz.emulator.graphics.enums.TileMapArea;

public class LcdControl {

    private boolean lcdEnabled;

    private TileMapArea windowTileMapArea;

    private boolean windowEnabled;

    private TileDataArea tileDataArea;

    private TileMapArea backgroundTileMapArea;

    private ObjSize objSize;

    private boolean objEnabled;

    private boolean backgroundWindowEnabled;

    public LcdControl() {
        this.lcdEnabled = false;
        this.windowTileMapArea = TileMapArea.AREA_1;
        this.windowEnabled = false;
        this.tileDataArea = TileDataArea.AREA_1;
        this.backgroundTileMapArea = TileMapArea.AREA_1;
        this.objSize = ObjSize.SIZE_8x8;
        this.objEnabled = false;
        this.backgroundWindowEnabled = false;
    }


    public int getLcdControlRegister() {
        int lcdControlReg = 0;
        lcdControlReg |= ((lcdEnabled ? 1 : 0) << 7);
        lcdControlReg |= ((windowTileMapArea.ordinal() << 6));
        lcdControlReg |= (((windowEnabled ? 1 : 0) << 5));
        lcdControlReg |= (((tileDataArea.ordinal() << 4)));
        lcdControlReg |= (((backgroundTileMapArea.ordinal() << 3)));
        lcdControlReg |= (((objSize.ordinal() << 2)));
        lcdControlReg |= (((objEnabled ? 1 : 0) << 1));
        lcdControlReg |= (((backgroundWindowEnabled ? 1 : 0)));

        return lcdControlReg;
    }

    public void setLcdControlRegister(int value) {
        lcdEnabled = BitUtil.getBit(value, 7);
        windowTileMapArea = TileMapArea.fromValue(BitUtil.getBit(value, 6) ? 1 : 0);
        windowEnabled = BitUtil.getBit(value, 5);
        tileDataArea = TileDataArea.fromValue(BitUtil.getBit(value, 4) ? 1 : 0);
        backgroundTileMapArea = TileMapArea.fromValue(BitUtil.getBit(value, 3) ? 1 : 0);
        objSize = ObjSize.fromValue(BitUtil.getBit(value, 2) ? 1 : 0);
        objEnabled = BitUtil.getBit(value, 1);
        backgroundWindowEnabled = BitUtil.getBit(value, 0);
    }

    public boolean isLcdEnabled() {
        return lcdEnabled;
    }

    public void setLcdEnabled(boolean lcdEnabled) {
        this.lcdEnabled = lcdEnabled;
    }

    public TileMapArea getWindowTileMapArea() {
        return windowTileMapArea;
    }

    public void setWindowTileMapArea(TileMapArea windowTileMapArea) {
        this.windowTileMapArea = windowTileMapArea;
    }

    public boolean isWindowEnabled() {
        return windowEnabled;
    }

    public void setWindowEnabled(boolean windowEnabled) {
        this.windowEnabled = windowEnabled;
    }

    public TileDataArea getTileDataArea() {
        return tileDataArea;
    }

    public void setTileDataArea(TileDataArea tileDataArea) {
        this.tileDataArea = tileDataArea;
    }

    public TileMapArea getBackgroundTileMapArea() {
        return backgroundTileMapArea;
    }

    public void setBackgroundTileMapArea(TileMapArea backgroundTileMapArea) {
        this.backgroundTileMapArea = backgroundTileMapArea;
    }

    public ObjSize getObjSize() {
        return objSize;
    }

    public void setObjSize(ObjSize objSize) {
        this.objSize = objSize;
    }

    public boolean isObjEnabled() {
        return objEnabled;
    }

    public void setObjEnabled(boolean objEnabled) {
        this.objEnabled = objEnabled;
    }

    public boolean isBackgroundWindowEnabled() {
        return backgroundWindowEnabled;
    }

    public void setBackgroundWindowEnabled(boolean backgroundWindowEnabled) {
        this.backgroundWindowEnabled = backgroundWindowEnabled;
    }
}
