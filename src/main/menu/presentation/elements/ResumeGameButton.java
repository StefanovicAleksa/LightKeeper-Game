package main.menu.presentation.elements;

import main.settings.domain.models.ThemeConfig;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;

public class ResumeGameButton extends JButton {
    private final ThemeConfig theme;

    public ResumeGameButton(ThemeConfig theme, ActionListener action) {
        super("RESUME");
        this.theme = theme;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Segoe UI", Font.BOLD, 16));
        setForeground(theme.getColorText());
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addActionListener(action);

        setPreferredSize(new Dimension(200, 50));
        setMaximumSize(new Dimension(200, 50));
        setAlignmentX(CENTER_ALIGNMENT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!isEnabled()) return;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color tc = theme.getColorText();
        if (getModel().isRollover()) {
            g2.setColor(new Color(tc.getRed(), tc.getGreen(), tc.getBlue(), 30));
        } else {
            g2.setColor(new Color(tc.getRed(), tc.getGreen(), tc.getBlue(), 10));
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.setColor(theme.getColorText());
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);

        super.paintComponent(g);
    }
}