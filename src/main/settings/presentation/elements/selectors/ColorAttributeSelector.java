package main.settings.presentation.elements.selectors;

import main.settings.domain.models.ThemeConfig;
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ColorAttributeSelector extends AttributeSelector {
    public ColorAttributeSelector(String label, Color initialColor, ThemeConfig theme, Consumer<Color> onSelect) {
        super(label, createColorButton(initialColor, theme, onSelect), theme);
    }

    private static JButton createColorButton(Color initial, ThemeConfig theme, Consumer<Color> onSelect) {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 10, 10);
                g2.setColor(theme.getColorText());
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 10, 10);
            }
        };
        button.setBackground(initial);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(button, "Select Color", button.getBackground());
            if (newColor != null) {
                button.setBackground(newColor);
                button.repaint();
                onSelect.accept(newColor);
            }
        });
        return button;
    }
}