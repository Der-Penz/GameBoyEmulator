package org.penz.emulator.graphics;

public interface IDisplay {

    void putPixel(int x, int y, int color);

    void enableDisplay();

    void disableDisplay();

    void onFrameReady();
}
