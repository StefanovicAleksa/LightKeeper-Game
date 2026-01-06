package main.game.presentation.elements.navigation;

import main.settings.ThemeConfig;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;

public class StartNewLevelButton extends JButton {
    private final ThemeConfig theme;
    private final int radius = 15;

    public StartNewLevelButton(ThemeConfig theme, ActionListener action) {
        super("Start New Level");
        this.theme = theme;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setForeground(Color.BLACK);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addActionListener(action);

        setPreferredSize(new Dimension(140, 35));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isRollover()) {
            g2.setColor(theme.getColorLit().brighter());
        } else {
            g2.setColor(theme.getColorLit());
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g);
    }
}