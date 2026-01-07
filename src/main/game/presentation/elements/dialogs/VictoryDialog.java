package main.game.presentation.elements.dialogs;

import main.game.presentation.elements.game.CheckOptimalLabel;
import main.game.presentation.elements.navigation.RestartButton;
import main.game.presentation.elements.navigation.StartNewLevelButton;
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

public class VictoryDialog extends JDialog {

    public VictoryDialog(JFrame parent, GameManager gameManager, long seconds, int userBulbs) {
        super(parent, "Victory", true);
        ThemeConfig theme = gameManager.getThemeConfig();

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(theme.getColorBackground());
        contentPanel.setBorder(BorderFactory.createLineBorder(theme.getColorText(), 1));

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setOpaque(false);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        JLabel mainTitle = new JLabel("VICTORY!");
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        mainTitle.setForeground(theme.getColorLit());
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subTitle = new JLabel("Level Solved");
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subTitle.setForeground(theme.getColorLit());
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        long mins = seconds / 60;
        long secs = seconds % 60;
        String timeStr = String.format("Time: %02d:%02d", mins, secs);

        JLabel timeLabel = new JLabel(timeStr);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        timeLabel.setForeground(theme.getColorText());
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel statsLabel = new JLabel("Bulbs: " + userBulbs);
        statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statsLabel.setForeground(theme.getColorText().darker());
        statsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        CheckOptimalLabel checkLabel = new CheckOptimalLabel(theme, gameManager, best -> {
            statsLabel.setText(String.format("Bulbs: %d (Best: %d)", userBulbs, best));
            innerPanel.revalidate();
            innerPanel.repaint();
        });

        StartNewLevelButton nextButton = new StartNewLevelButton(theme, e -> {
            gameManager.startNewGame();
            dispose();
        });
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.setMaximumSize(new Dimension(160, 35));
        nextButton.setPreferredSize(new Dimension(160, 35));

        RestartButton restartButton = new RestartButton(e -> {
            gameManager.restartLevel();
            dispose();
        });
        restartButton.setText("Restart Level");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setMaximumSize(new Dimension(160, 35));
        restartButton.setPreferredSize(new Dimension(160, 35));

        innerPanel.add(mainTitle);
        innerPanel.add(Box.createVerticalStrut(5));
        innerPanel.add(subTitle);
        innerPanel.add(Box.createVerticalStrut(20));
        innerPanel.add(timeLabel);
        innerPanel.add(Box.createVerticalStrut(5));
        innerPanel.add(statsLabel);
        innerPanel.add(Box.createVerticalStrut(5));
        innerPanel.add(checkLabel);
        innerPanel.add(Box.createVerticalStrut(20));
        innerPanel.add(nextButton);
        innerPanel.add(Box.createVerticalStrut(15));
        innerPanel.add(restartButton);

        contentPanel.add(innerPanel, BorderLayout.CENTER);

        setContentPane(contentPanel);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(parent);
    }

    public static void show(JFrame parent, GameManager gameManager, int userBulbs) {
        long time = gameManager.getCurrentSession().getTotalPlayTimeSeconds();
        VictoryDialog dialog = new VictoryDialog(parent, gameManager, time, userBulbs);
        dialog.setVisible(true);
    }
}