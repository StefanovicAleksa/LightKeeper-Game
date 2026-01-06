package main.game.presentation.screens;

import main.game.presentation.elements.dialogs.HelpDialog;
import main.game.presentation.elements.dialogs.ResumeDialog;
import main.game.presentation.elements.game.GamePanel;
import main.game.presentation.elements.game.TimeElapsed;
import main.game.presentation.elements.navigation.HelpButton;
import main.game.presentation.elements.navigation.PauseButton;
import main.game.service.GameManager;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

public class GameScreen {
    private final JFrame frame;
    private final GamePanel gamePanel;
    private final TimeElapsed timeElapsed;
    private final HelpButton helpButton;
    private final PauseButton pauseButton;

    public GameScreen(GameManager gameManager) {
        frame = new JFrame("Lightkeeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.getContentPane().setBackground(gameManager.getThemeConfig().getColorBackground());

        this.gamePanel = new GamePanel(
                gameManager.getGrid(),
                gameManager,
                gameManager.getGameConfig(),
                gameManager.getThemeConfig()
        );

        this.timeElapsed = new TimeElapsed(gameManager);

        this.helpButton = new HelpButton(gameManager.getThemeConfig(), e -> {
            HelpDialog.show(frame, gameManager.getThemeConfig());
        });

        this.pauseButton = new PauseButton(gameManager.getThemeConfig(), e -> {
            ResumeDialog.show(frame, gameManager);
        });

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JPanel rightWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightWrapper.setOpaque(false);
        rightWrapper.add(pauseButton);
        rightWrapper.add(helpButton);

        JPanel leftSpacer = new JPanel();
        leftSpacer.setOpaque(false);
        leftSpacer.setPreferredSize(new java.awt.Dimension(100, 45));

        headerPanel.add(leftSpacer, BorderLayout.WEST);
        headerPanel.add(timeElapsed, BorderLayout.CENTER);
        headerPanel.add(rightWrapper, BorderLayout.EAST);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(gamePanel);

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(centerWrapper, BorderLayout.CENTER);
    }

    public void show() {
        frame.setSize(600, 750);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void refresh() {
        gamePanel.removeAll();
        gamePanel.initializeLayout();
        gamePanel.revalidate();
        gamePanel.repaint();
    }
}