package main.settings.presentation.elements.dialogs;

import main.game.service.GameManager;
import main.settings.domain.models.GameConfig;
import main.settings.domain.models.ThemeConfig;
import main.settings.presentation.elements.selectors.AttributeSelector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

public class GameConfigDialog extends JDialog {

    private static final int MAX_GRID_SIZE = 50;
    private static final int MIN_GRID_SIZE = 5;

    public GameConfigDialog(JFrame parent, GameManager gameManager) {
        super(parent, "New Game Configuration", true);
        ThemeConfig theme = gameManager.getThemeConfig();

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        content.setBackground(theme.getColorBackground());

        int currentSize = Math.min(Math.max(gameManager.getGameConfig().getGridSize(), MIN_GRID_SIZE), MAX_GRID_SIZE);

        JSlider sizeSlider = new JSlider(MIN_GRID_SIZE, MAX_GRID_SIZE, currentSize);
        AttributeSelector sizeSelector = new AttributeSelector("Grid Size", sizeSlider, theme, "");
        content.add(sizeSelector);

        int currentRatio = (int)(gameManager.getGameConfig().getWallRatio() * 100);
        if (currentRatio > 90) currentRatio = 90;

        JSlider ratioSlider = new JSlider(0, 90, currentRatio);
        AttributeSelector ratioSelector = new AttributeSelector("Wall Density", ratioSlider, theme, "%");
        content.add(ratioSelector);

        content.add(Box.createVerticalStrut(15));

        JButton playButton = new JButton("START GAME");
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setBackground(theme.getColorLit());
        playButton.setForeground(theme.getColorBackground());
        playButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        playButton.setFocusPainted(false);
        playButton.setMaximumSize(new Dimension(150, 40));
        playButton.setPreferredSize(new Dimension(150, 40));

        playButton.addActionListener(e -> {
            int n = sizeSlider.getValue();
            double r = ratioSlider.getValue() / 100.0;

            GameConfig newConfig = new GameConfig(n, r);

            dispose();
            gameManager.startGame(newConfig);
        });

        content.add(playButton);

        setContentPane(content);
        setResizable(false);
        pack();
        setLocationRelativeTo(parent);
    }
}