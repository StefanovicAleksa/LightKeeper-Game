package main.game.presentation.elements.navigation;

import main.settings.domain.models.ThemeConfig;

import javax.swing.JButton;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;

public class PauseButton extends JButton {
    private final ThemeConfig theme;

    public PauseButton(ThemeConfig theme, ActionListener action) {
        this.theme = theme;

        setPreferredSize(new Dimension(45, 45));
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setToolTipText("Pause Game");
        addActionListener(action);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int size = Math.min(getWidth(), getHeight()) - 4;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        if (getModel().isRollover()) {
            Color tc = theme.getColorText();
            g2.setColor(new Color(tc.getRed(), tc.getGreen(), tc.getBlue(), 40));
            g2.fillOval(x, y, size, size);
        }

        g2.setColor(theme.getColorText());
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(x, y, size, size);

        Image icon = theme.getPauseImage();
        if (icon != null) {
            int iconSize = size / 2;
            int ix = (getWidth() - iconSize) / 2;
            int iy = (getHeight() - iconSize) / 2;
            g2.drawImage(icon, ix, iy, iconSize, iconSize, null);
        } else {
            int cx = getWidth() / 2;
            int cy = getHeight() / 2;
            int h = size / 3;
            int w = size / 10;
            int gap = w / 2;

            g2.fillRect(cx - w - gap, cy - h/2, w, h);
            g2.fillRect(cx + gap, cy - h/2, w, h);
        }
    }
}