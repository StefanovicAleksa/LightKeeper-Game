package main.game.presentation.elements.game;

import main.game.service.GameManager;
import main.settings.ThemeConfig;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.FlowLayout;
import java.awt.Font;

public class TimeElapsed extends JPanel {
    private final JLabel timeLabel;
    private final GameManager gameManager;

    public TimeElapsed(GameManager gameManager) {
        this.gameManager = gameManager;
        ThemeConfig theme = gameManager.getThemeConfig();

        this.timeLabel = new JLabel("00:00");

        setLayout(new FlowLayout(FlowLayout.CENTER));
        setOpaque(false);

        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        timeLabel.setForeground(theme.getColorText());

        add(timeLabel);
        startTimer();
    }

    private void startTimer() {
        // Update every 100ms (0.1s) to prevent visual lag when pausing
        Timer timer = new Timer(100, e -> updateTime());
        timer.start();
    }

    private void updateTime() {
        if (gameManager.getCurrentSession() == null) return;

        long totalSeconds = gameManager.getCurrentSession().getTotalPlayTimeSeconds();
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }
}