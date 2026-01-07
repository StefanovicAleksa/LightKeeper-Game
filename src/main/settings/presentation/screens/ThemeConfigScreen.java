package main.settings.presentation.screens;

import main.game.presentation.elements.navigation.BackButton;
import main.game.service.GameManager;
import main.settings.domain.models.ThemeConfig;
import main.settings.presentation.elements.selectors.AttributeSelector;
import main.settings.presentation.elements.selectors.ColorAttributeSelector;
import javax.swing.*;
import java.awt.*;

public class ThemeConfigScreen extends JPanel {
    private final GameManager gameManager;
    private final ThemeConfig theme;
    private final JLabel titleLabel;
    private final JPanel contentPanel;
    private final BackButton backButton;

    public ThemeConfigScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        this.theme = gameManager.getThemeConfig();
        setLayout(new BorderLayout());
        setBackground(theme.getColorBackground());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        headerPanel.setOpaque(false);
        this.backButton = new BackButton(theme, gameManager);
        this.titleLabel = new JLabel("THEME SETTINGS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(theme.getColorLit());
        headerPanel.add(backButton);
        headerPanel.add(titleLabel);

        this.contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        contentPanel.add(new ColorAttributeSelector("Background", theme.getColorBackground(), theme, c -> {
            theme.setColorBackground(c);
            refreshUI();
        }));
        contentPanel.add(new ColorAttributeSelector("Walls", theme.getColorWall(), theme, c -> {
            theme.setColorWall(c);
            refreshUI();
        }));
        contentPanel.add(new ColorAttributeSelector("Empty Cell", theme.getColorRegular(), theme, c -> {
            theme.setColorRegular(c);
            refreshUI();
        }));
        contentPanel.add(new ColorAttributeSelector("Lit Cell", theme.getColorLit(), theme, c -> {
            theme.setColorLit(c);
            refreshUI();
        }));
        contentPanel.add(new ColorAttributeSelector("Error Color", theme.getColorBulbError(), theme, c -> {
            theme.setColorBulbError(c);
            refreshUI();
        }));
        contentPanel.add(new ColorAttributeSelector("Buttons", theme.getColorButton(), theme, c -> {
            theme.setColorButton(c);
            refreshUI();
        }));
        contentPanel.add(new ColorAttributeSelector("Text Color", theme.getColorText(), theme, c -> {
            theme.setColorText(c);
            refreshUI();
        }));
        contentPanel.add(new ColorAttributeSelector("Subtext Color", theme.getColorSubtext(), theme, c -> {
            theme.setColorSubtext(c);
            refreshUI();
        }));

        contentPanel.add(Box.createVerticalGlue());
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void refreshUI() {
        setBackground(theme.getColorBackground());
        titleLabel.setForeground(theme.getColorLit());
        for (Component c : contentPanel.getComponents()) {
            if (c instanceof AttributeSelector selector) {
                selector.refreshColors();
            }
        }
        backButton.repaint();
        repaint();
        gameManager.getMainFrame().getContentPane().setBackground(theme.getColorBackground());
        gameManager.getMainFrame().repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        setBackground(theme.getColorBackground());
        super.paintComponent(g);
    }
}