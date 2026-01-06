package main.game.presentation.elements.dialogs;

import main.settings.ThemeConfig;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

public class HelpDialog {
    public static void show(Component parent, ThemeConfig theme) {
        String text = """
            GOAL:
            Light up all white cells.

            RULES:
            - Bulbs shine light horizontally and vertically.
            - Walls block the light.
            - Two bulbs cannot shine on each other (Collision).

            CONTROLS:
            - Left Click: Place / Remove Light Bulb
            - Right Click: Mark cell as bad spot for a light bulb (X)
            """;

        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        textArea.setBackground(theme.getColorBackground());
        textArea.setForeground(theme.getColorText());
        textArea.setBorder(null);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 260));
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(theme.getColorBackground());

        JOptionPane.showMessageDialog(parent, scrollPane, "How to Play", JOptionPane.INFORMATION_MESSAGE);
    }
}