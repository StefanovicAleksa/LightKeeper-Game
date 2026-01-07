package main.settings.presentation.elements.selectors;

import main.settings.domain.models.ThemeConfig;
import javax.swing.*;
import java.awt.*;

public class AttributeSelector extends JPanel {
    protected final JLabel label;
    protected final JComponent inputComponent;
    protected final ThemeConfig theme;
    protected JLabel valueLabel;

    public AttributeSelector(String labelText, JSlider slider, ThemeConfig theme, String suffix) {
        this(labelText, (JComponent) slider, theme);
        valueLabel = new JLabel(slider.getValue() + suffix);
        valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        valueLabel.setForeground(theme.getColorLit());
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        Dimension labelSize = new Dimension(50, 30);
        valueLabel.setPreferredSize(labelSize);
        valueLabel.setMaximumSize(labelSize);
        valueLabel.setMinimumSize(labelSize);
        valueLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        slider.addChangeListener(e -> valueLabel.setText(slider.getValue() + suffix));
        add(Box.createHorizontalStrut(10));
        add(valueLabel);
    }

    public AttributeSelector(String labelText, JComponent inputComponent, ThemeConfig theme) {
        this.theme = theme;
        this.inputComponent = inputComponent;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(theme.getColorText());
        label.setPreferredSize(new Dimension(140, 30));
        label.setMaximumSize(new Dimension(140, 30));
        label.setMinimumSize(new Dimension(140, 30));
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        inputComponent.setOpaque(false);
        inputComponent.setForeground(theme.getColorLit());
        inputComponent.setMaximumSize(new Dimension(180, 30));
        inputComponent.setPreferredSize(new Dimension(180, 30));
        inputComponent.setAlignmentY(Component.CENTER_ALIGNMENT);
        inputComponent.setFocusable(false);
        add(label);
        add(Box.createHorizontalStrut(10));
        add(inputComponent);
    }

    public void refreshColors() {
        label.setForeground(theme.getColorText());
        inputComponent.setForeground(theme.getColorLit());
        if (valueLabel != null) {
            valueLabel.setForeground(theme.getColorLit());
        }
        repaint();
    }
}