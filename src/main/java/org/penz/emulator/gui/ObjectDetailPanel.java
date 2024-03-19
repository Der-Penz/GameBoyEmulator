package org.penz.emulator.gui;

import org.penz.emulator.graphics.Object;

import javax.swing.*;

public class ObjectDetailPanel extends JPanel {

    private final JLabel objectLabel;
    private final JLabel xLabel;
    private final JLabel yLabel;
    private final JLabel tileIdLabel;
    private final JLabel paletteLabel;
    private final JCheckBox xFlipped;
    private final JCheckBox yFlipped;
    private final JCheckBox bgPriority;

    public ObjectDetailPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        objectLabel = new JLabel("Object ");
        xLabel = new JLabel("X: ");
        yLabel = new JLabel("Y: ");
        tileIdLabel = new JLabel("Tile ID: ");
        paletteLabel = new JLabel("Palette: ");
        xFlipped = new JCheckBox("X Flipped");
        yFlipped = new JCheckBox("Y Flipped");
        bgPriority = new JCheckBox("BG Priority");
        xFlipped.setEnabled(false);
        yFlipped.setEnabled(false);
        bgPriority.setEnabled(false);

        add(objectLabel);
        add(xLabel);
        add(yLabel);
        add(tileIdLabel);
        add(paletteLabel);
        add(xFlipped);
        add(yFlipped);
        add(bgPriority);
    }


    public void updateDetails(int i, Object object) {
        objectLabel.setText("Object " + i);
        xLabel.setText("X: " + (object.getX() & 0xFF));
        yLabel.setText("Y: " + (object.getY() & 0xFF));
        tileIdLabel.setText("Tile ID: " + object.getTileId());
        paletteLabel.setText("Palette: " + object.getPixelType().name());
        xFlipped.setSelected(object.xFlip());
        yFlipped.setSelected(object.yFlip());
        bgPriority.setSelected(object.bgPriority());
    }
}
