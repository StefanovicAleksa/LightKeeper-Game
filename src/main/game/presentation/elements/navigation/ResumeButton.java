package main.game.presentation.elements.navigation;

import main.settings.ThemeConfig;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class ResumeButton extends JButton {
    private final ThemeConfig theme;

    public ResumeButton(ThemeConfig theme, ActionListener action) {
        this.theme = theme;

        setPreferredSize(new Dimension(60, 60)); // Slightly larger for the dialog
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        addActionListener(action);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight()) - 4;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        if (getModel().isRollover()) {
            g2.setColor(new Color(255, 255, 255, 40));
        } else {
            g2.setColor(new Color(255, 255, 255, 20));
        }
        g2.fillOval(x, y, size, size);

        g2.setColor(theme.getColorText());
        g2.setStroke(new java.awt.BasicStroke(2f));
        g2.drawOval(x, y, size, size);

        int[] xPoints = {x + size / 3 + 2, x + size / 3 + 2, x + 2 * size / 3 + 2};
        int[] yPoints = {y + size / 3, y + 2 * size / 3, y + size / 2};

        g2.fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    public boolean contains(int x, int y) {
        int size = Math.min(getWidth(), getHeight()) - 4;
        int cx = (getWidth() - size) / 2;
        int cy = (getHeight() - size) / 2;
        Ellipse2D circle = new Ellipse2D.Float(cx, cy, size, size);
        return circle.contains(x, y);
    }
}