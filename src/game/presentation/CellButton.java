package game.presentation;

import game.elements.Cell;
import game.elements.RegularCell;
import game.elements.WallCell;
import settings.ThemeConfig;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class CellButton extends JButton {
    private final int row;
    private final int col;
    private final Cell cell;
    private final ThemeConfig theme;

    public CellButton(int row, int col, Cell cell, ThemeConfig theme) {
        this.row = row;
        this.col = col;
        this.cell = cell;
        this.theme = theme;

        // Visual cleanup: Make the button a flat canvas
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Turn on Anti-Aliasing for smoother circles
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // 1. Draw Background
        if (cell instanceof WallCell) {
            g2.setColor(theme.getColorWall());
        }
        else if (cell instanceof RegularCell) {
            RegularCell rc = (RegularCell) cell;

            // Priority: Error (Red) -> Lit (Yellow) -> Default (White)
            if (rc.getBulbCollisionLevel() > 0) {
                g2.setColor(theme.getColorBulbError());
            } else if (rc.getLightLevel() > 0) {
                g2.setColor(theme.getColorLit());
            } else {
                g2.setColor(theme.getColorRegular());
            }
        }
        g2.fillRect(0, 0, w, h);

        // 2. Draw Border
        g2.setColor(Color.GRAY);
        g2.drawRect(0, 0, w - 1, h - 1);

        // 3. Draw Icons (Bulb or X)
        if (cell instanceof RegularCell) {
            RegularCell rc = (RegularCell) cell;

            if (rc.hasBulb()) {
                g2.setColor(Color.BLACK);
                int padding = w / 4;
                g2.fillOval(padding, padding, w - (padding * 2), h - (padding * 2));
            }
            else if (rc.isMarked()) {
                g2.setColor(theme.getColorMark());
                int padding = w / 4;
                g2.setStroke(new java.awt.BasicStroke(2));
                g2.drawLine(padding, padding, w - padding, h - padding);
                g2.drawLine(w - padding, padding, padding, h - padding);
            }
        }
    }
}