package org.penz.emulator;

import org.penz.emulator.graphics.Display;

public class ConsoleDisplay implements Display {
    @Override
    public void putPixel(int x, int y, int color) {
//        System.out.println("x: " + x + " y: " + y + " color: " + color);
    }

    @Override
    public void enable() {
        System.out.println("Display enabled");
    }

    @Override
    public void disable() {
        System.out.println("Display disabled");
    }

    @Override
    public void onFrameReady() {
        System.out.println("Frame ready");
    }
}
