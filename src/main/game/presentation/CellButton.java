package main.game.presentation;

import main.game.elements.Cell;
import main.game.elements.RegularCell;
import main.game.elements.WallCell;
import main.settings.ThemeConfig;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Image;

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

        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public Cell getCell() { return cell; }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int w = getWidth();
        int h = getHeight();

        if (cell instanceof WallCell) {
            g2.setColor(theme.getColorWall());
        }
        else if (cell instanceof RegularCell) {
            RegularCell rc = (RegularCell) cell;

            if (rc.getBulbCollisionLevel() > 0)
                g2.setColor(theme.getColorBulbError());
            else if (rc.getLightLevel() > 0)
                g2.setColor(theme.getColorLit());
            else
                g2.setColor(theme.getColorRegular());
        }

        g2.fillRect(0, 0, w, h);

        g2.setColor(Color.GRAY);
        g2.drawRect(0, 0, w - 1, h - 1);

        if (cell instanceof RegularCell) {
            RegularCell rc = (RegularCell) cell;

            if (rc.hasBulb()) {
                Image img = theme.getBulbImage();
                if (img != null) {
                    int p = w / 6;
                    g2.drawImage(img, p, p, w - 2*p, h - 2*p, null);
                }
                else {
                    g2.setColor(Color.BLACK);
                    g2.fillOval(w/4, h/4, w/2, h/2);
                }
            }
            else if (rc.isMarked()) {
                Image img = theme.getMarkImage();
                if (img != null) {
                    int p = w / 6;
                    g2.drawImage(img, p, p, w - 2*p, h - 2*p, null);
                }
                else {
                    g2.setColor(theme.getColorMark());
                    g2.setStroke(new java.awt.BasicStroke(3));
                    g2.drawLine(w/4, h/4, 3*w/4, 3*h/4);
                    g2.drawLine(3*w/4, h/4, w/4, 3*h/4);
                }
            }
        }
    }
}