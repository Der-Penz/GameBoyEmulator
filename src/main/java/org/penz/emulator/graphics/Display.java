package org.penz.emulator.graphics;

public interface Display {

    void putPixel(int x, int y, int color);

    void enable();

    void disable();

    void onFrameReady();
}
