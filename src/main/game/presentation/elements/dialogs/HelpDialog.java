package main.game.presentation.elements.dialogs;

import main.settings.ThemeConfig;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class HelpDialog extends JDialog {

    public HelpDialog(JFrame parent, ThemeConfig theme) {
        super(parent, "How to Play", true);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(theme.getColorBackground());
        contentPanel.setBorder(BorderFactory.createLineBorder(theme.getColorText(), 1));

        // --- Content Section ---
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        addHeader(textPanel, "GOAL:", theme);
        addText(textPanel, "Light up all empty cells.", theme);

        textPanel.add(Box.createVerticalStrut(20));

        addHeader(textPanel, "RULES:", theme);
        addText(textPanel, "• Bulbs shine light horizontally and vertically.", theme);
        addText(textPanel, "• Walls block the light.", theme);
        addText(textPanel, "• Two bulbs cannot shine on each other.", theme);

        textPanel.add(Box.createVerticalStrut(20));

        addHeader(textPanel, "CONTROLS:", theme);
        addText(textPanel, "• Left Click: Place / Remove Bulb", theme);
        addText(textPanel, "• Right Click: Mark Cell (X)", theme);

        // --- Button Section ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JButton closeButton = createStyledButton("Got it", theme);
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        contentPanel.add(textPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPanel);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(parent);
    }

    private void addHeader(JPanel panel, String text, ThemeConfig theme) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(theme.getColorLit()); // Use accent color for headers
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
    }

    private void addText(JPanel panel, String text, ThemeConfig theme) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(theme.getColorText());
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(3));
    }

    private JButton createStyledButton(String text, ThemeConfig theme) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isRollover()) {
                    g2.setColor(theme.getColorLit().brighter());
                    setForeground(Color.BLACK);
                } else {
                    g2.setColor(theme.getColorLit());
                    setForeground(Color.BLACK);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };

        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 35));
        button.setMaximumSize(new Dimension(120, 35));

        return button;
    }

    public static void show(JFrame parent, ThemeConfig theme) {
        HelpDialog dialog = new HelpDialog(parent, theme);
        dialog.setVisible(true);
    }
}