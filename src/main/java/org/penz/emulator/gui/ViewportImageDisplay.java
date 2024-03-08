package org.penz.emulator.gui;

import org.penz.emulator.GameBoy;

import java.awt.*;

public class ViewportImageDisplay extends ImageDisplay {


    private int scy;
    private int scx;

    public ViewportImageDisplay(int width, int height, int scale) {
        super(width, height, scale);
    }

    public void setViewport(int scy, int scx) {
        this.scy = scy;
        this.scx = scx;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.drawRect(scx * scale, scy * scale, GameBoy.SCREEN_WIDTH * scale, GameBoy.SCREEN_HEIGHT * scale);

        if (scx + GameBoy.SCREEN_WIDTH > imageWidth) {
            int overflow = (scx + GameBoy.SCREEN_WIDTH) - imageWidth;
            g.drawRect((overflow - GameBoy.SCREEN_WIDTH) * scale, scy * scale, GameBoy.SCREEN_WIDTH * scale, GameBoy.SCREEN_HEIGHT * scale);
        }

        if (scy + GameBoy.SCREEN_HEIGHT > imageHeight) {
            int overflow = (scy + GameBoy.SCREEN_HEIGHT) - imageHeight;
            g.drawRect(scx * scale, (overflow - GameBoy.SCREEN_HEIGHT) * scale, GameBoy.SCREEN_WIDTH * scale, GameBoy.SCREEN_HEIGHT * scale);
        }

        if (scx + GameBoy.SCREEN_WIDTH > imageWidth && scy + GameBoy.SCREEN_HEIGHT > imageHeight) {
            int overflowX = (scx + GameBoy.SCREEN_WIDTH) - imageWidth;
            int overflowY = (scy + GameBoy.SCREEN_HEIGHT) - imageHeight;
            g.drawRect((overflowX - GameBoy.SCREEN_WIDTH) * scale, (overflowY - GameBoy.SCREEN_HEIGHT) * scale, GameBoy.SCREEN_WIDTH * scale, GameBoy.SCREEN_HEIGHT * scale);
        }

    }
}
