package main.menu.presentation.screens;

import main.game.domain.enums.GameState;
import main.game.domain.models.session.GameSession;
import main.game.service.GameManager;
import main.menu.presentation.elements.LoadGameButton;
import main.menu.presentation.elements.ResumeGameButton;
import main.menu.presentation.elements.StartGameButton;
import main.menu.presentation.elements.ThemeSettingsButton;
import main.settings.presentation.elements.dialogs.GameConfigDialog;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

public class MainMenuScreen extends JPanel {
    private final GameManager gameManager;

    public MainMenuScreen(GameManager gameManager) {
        this.gameManager = gameManager;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(gameManager.getThemeConfig().getColorBackground());

        add(Box.createVerticalGlue());

        JLabel titleLabel = new JLabel("LIGHTKEEPER");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        titleLabel.setForeground(gameManager.getThemeConfig().getColorLit());
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);

        add(Box.createVerticalStrut(60));

        GameSession currentSession = gameManager.getCurrentSession();
        boolean isResumable = currentSession != null &&
                (currentSession.getCurrentState() == GameState.PLAYING ||
                        currentSession.getCurrentState() == GameState.PAUSED);

        if (isResumable) {
            ResumeGameButton resumeButton = new ResumeGameButton(gameManager.getThemeConfig(), e ->
                    gameManager.resumeGame()
            );
            add(resumeButton);
            add(Box.createVerticalStrut(20));
        }

        StartGameButton startButton = new StartGameButton(gameManager.getThemeConfig(), e -> {
            GameConfigDialog dialog = new GameConfigDialog(gameManager.getMainFrame(), gameManager);
            dialog.setVisible(true);
        });
        add(startButton);
        add(Box.createVerticalStrut(20));

        LoadGameButton loadButton = new LoadGameButton(gameManager.getThemeConfig(), e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Save Files", "sav"));
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                gameManager.loadGame(fileChooser.getSelectedFile());
            }
        });
        add(loadButton);
        add(Box.createVerticalStrut(20));

        ThemeSettingsButton settingsButton = new ThemeSettingsButton(gameManager.getThemeConfig(), e ->
                gameManager.showThemeSettings()
        );
        add(settingsButton);

        add(Box.createVerticalGlue());
    }

    @Override
    protected void paintComponent(Graphics g) {
        setBackground(gameManager.getThemeConfig().getColorBackground());
        super.paintComponent(g);
    }
}