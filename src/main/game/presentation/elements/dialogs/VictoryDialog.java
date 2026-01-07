package main.game.presentation.elements.dialogs;

import main.game.presentation.elements.game.CheckOptimalLabel;
import main.game.presentation.elements.navigation.RestartButton;
import main.game.presentation.elements.navigation.StartNewLevelButton;
import main.game.service.GameManager;
import main.settings.domain.models.ThemeConfig;
import main.settings.presentation.elements.dialogs.GameConfigDialog;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

public class VictoryDialog extends JDialog {

    public VictoryDialog(JFrame parent, GameManager gameManager, long seconds, int userBulbs) {
        super(parent, "Victory", true);
        ThemeConfig theme = gameManager.getThemeConfig();

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(theme.getColorBackground());
        contentPanel.setBorder(BorderFactory.createLineBorder(theme.getColorText(), 1));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        headerPanel.setOpaque(false);

        JButton closeButton = createCloseButton(theme);
        headerPanel.add(closeButton);

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setOpaque(false);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(5, 40, 25, 40));

        JLabel mainTitle = new JLabel("VICTORY!");
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        mainTitle.setForeground(theme.getColorLit());
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        long mins = seconds / 60;
        long secs = seconds % 60;
        JLabel timeLabel = new JLabel(String.format("Time: %02d:%02d", mins, secs));
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        timeLabel.setForeground(theme.getColorText());
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel statsLabel = new JLabel("Bulbs: " + userBulbs);
        statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statsLabel.setForeground(theme.getColorText().darker());
        statsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        CheckOptimalLabel checkLabel = new CheckOptimalLabel(theme, gameManager, best -> {
            statsLabel.setText(String.format("Bulbs: %d (Best: %d)", userBulbs, best));
            pack();
            setLocationRelativeTo(parent);
        });

        StartNewLevelButton nextButton = new StartNewLevelButton(theme, e -> {
            new GameConfigDialog(gameManager.getMainFrame(), gameManager).setVisible(true);
            dispose();
        });
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.setMaximumSize(new Dimension(200, 40));
        nextButton.setPreferredSize(new Dimension(200, 40));

        RestartButton restartButton = new RestartButton(theme, e -> {
            gameManager.restartLevel();
            dispose();
        });
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setMaximumSize(new Dimension(200, 40));
        restartButton.setPreferredSize(new Dimension(200, 40));

        innerPanel.add(mainTitle);
        innerPanel.add(Box.createVerticalStrut(20));
        innerPanel.add(timeLabel);
        innerPanel.add(Box.createVerticalStrut(5));
        innerPanel.add(statsLabel);
        innerPanel.add(Box.createVerticalStrut(5));
        innerPanel.add(checkLabel);
        innerPanel.add(Box.createVerticalStrut(20));
        innerPanel.add(nextButton);
        innerPanel.add(Box.createVerticalStrut(10));
        innerPanel.add(restartButton);

        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(innerPanel, BorderLayout.CENTER);

        setContentPane(contentPanel);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(parent);
    }

    private JButton createCloseButton(ThemeConfig theme) {
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(30, 30));
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> dispose());

        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, javax.swing.JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (btn.getModel().isRollover()) {
                    Color err = theme.getColorBulbError();
                    g2.setColor(new Color(err.getRed(), err.getGreen(), err.getBlue(), 50));
                    g2.fillOval(0, 0, c.getWidth(), c.getHeight());
                }

                Image img = theme.getMarkImage();
                if (img != null) {
                    int p = 8;
                    g2.drawImage(img, p, p, c.getWidth() - 2*p, c.getHeight() - 2*p, null);
                } else {
                    g2.setColor(theme.getColorText());
                    g2.setStroke(new BasicStroke(2));
                    int p = 10;
                    g2.drawLine(p, p, c.getWidth() - p, c.getHeight() - p);
                    g2.drawLine(c.getWidth() - p, p, p, c.getHeight() - p);
                }
            }
        });
        return btn;
    }

    public static void show(JFrame parent, GameManager gameManager, int userBulbs) {
        long time = gameManager.getCurrentSession().getTotalPlayTimeSeconds();
        VictoryDialog dialog = new VictoryDialog(parent, gameManager, time, userBulbs);
        dialog.setVisible(true);
    }
}