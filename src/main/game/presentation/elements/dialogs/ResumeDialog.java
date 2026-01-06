package main.game.presentation.elements.dialogs;

import main.game.presentation.elements.navigation.RestartButton;
import main.game.presentation.elements.navigation.ResumeButton;
import main.game.service.GameManager;
import main.settings.ThemeConfig;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

public class ResumeDialog extends JDialog {

    public ResumeDialog(JFrame parent, GameManager gameManager) {
        super(parent, "Game Paused", true);
        ThemeConfig theme = gameManager.getThemeConfig();

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(theme.getColorBackground());
        contentPanel.setBorder(BorderFactory.createLineBorder(theme.getColorText(), 1));

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setOpaque(false);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JLabel titleLabel = new JLabel("PAUSED");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(theme.getColorText());
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ResumeButton resumeButton = new ResumeButton(theme, e -> {
            gameManager.resumeGame();
            dispose();
        });
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resumeButton.setMaximumSize(new Dimension(60, 60));

        RestartButton restartButton = new RestartButton(e -> {
            gameManager.restartLevel();
            dispose();
        });
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setPreferredSize(new Dimension(150, 35));
        restartButton.setMaximumSize(new Dimension(150, 35));

        innerPanel.add(titleLabel);
        innerPanel.add(Box.createVerticalStrut(25));
        innerPanel.add(resumeButton);
        innerPanel.add(Box.createVerticalStrut(25));
        innerPanel.add(restartButton);

        contentPanel.add(innerPanel, BorderLayout.CENTER);

        setContentPane(contentPanel);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(parent);
    }

    public static void show(JFrame parent, GameManager gameManager) {
        gameManager.pauseGame();
        ResumeDialog dialog = new ResumeDialog(parent, gameManager);
        dialog.setVisible(true);
    }
}