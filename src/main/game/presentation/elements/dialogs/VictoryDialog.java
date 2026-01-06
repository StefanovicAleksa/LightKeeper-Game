package main.game.presentation.elements.dialogs;

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

    public VictoryDialog(JFrame parent, GameManager gameManager, long seconds) {
        super(parent, "Victory", true);
        ThemeConfig theme = gameManager.getThemeConfig();

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(theme.getColorBackground());
        contentPanel.setBorder(BorderFactory.createLineBorder(theme.getColorText(), 1));

        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.setOpaque(false);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        JLabel mainTitle = new JLabel("VICTORY! 🎉");
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        mainTitle.setForeground(theme.getColorLit());
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subTitle = new JLabel("Level Solved");
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subTitle.setForeground(theme.getColorLit());
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(mainTitle);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subTitle);

        long mins = seconds / 60;
        long secs = seconds % 60;
        String timeStr = String.format("Time: %02d:%02d", mins, secs);

        JLabel timeLabel = new JLabel(timeStr, JLabel.CENTER);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        timeLabel.setForeground(theme.getColorText());
        timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

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

        buttonPanel.add(nextButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(restartButton);

        innerPanel.add(titlePanel, BorderLayout.NORTH);
        innerPanel.add(timeLabel, BorderLayout.CENTER);
        innerPanel.add(buttonPanel, BorderLayout.SOUTH);

        contentPanel.add(innerPanel, BorderLayout.CENTER);

        setContentPane(contentPanel);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(parent);
    }

    public static void show(JFrame parent, GameManager gameManager) {
        long time = gameManager.getCurrentSession().getTotalPlayTimeSeconds();
        VictoryDialog dialog = new VictoryDialog(parent, gameManager, time);
        dialog.setVisible(true);
    }
}