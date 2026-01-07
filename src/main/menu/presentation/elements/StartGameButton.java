package main.menu.presentation.elements;

import main.settings.domain.models.ThemeConfig;

import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;

public class StartGameButton extends JButton {
    private final ThemeConfig theme;

    public StartGameButton(ThemeConfig theme, ActionListener action) {
        super("NEW GAME");
        this.theme = theme;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Segoe UI", Font.BOLD, 16));
        setForeground(theme.getColorBackground());
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addActionListener(action);

        setPreferredSize(new Dimension(200, 50));
        setMaximumSize(new Dimension(200, 50));
        setAlignmentX(CENTER_ALIGNMENT);
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

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        super.paintComponent(g);
    }
}