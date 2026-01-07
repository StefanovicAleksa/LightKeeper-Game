package main.game.presentation.screens;

import main.game.presentation.elements.dialogs.HelpDialog;
import main.game.presentation.elements.dialogs.ResumeDialog;
import main.game.presentation.elements.game.GamePanel;
import main.game.presentation.elements.game.TimeElapsed;
import main.game.presentation.elements.navigation.BackButton;
import main.game.presentation.elements.navigation.HelpButton;
import main.game.presentation.elements.navigation.PauseButton;
import main.game.presentation.elements.navigation.SaveButton;
import main.game.service.GameManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

public class GameScreen extends JPanel {
    private final GamePanel gamePanel;
    private final TimeElapsed timeElapsed;
    private final HelpButton helpButton;
    private final PauseButton pauseButton;
    private final BackButton backButton;
    private final SaveButton saveButton;

    public GameScreen(GameManager gameManager) {
        setLayout(new BorderLayout());
        setBackground(gameManager.getThemeConfig().getColorBackground());

        this.gamePanel = new GamePanel(
                gameManager.getGrid(),
                gameManager,
                gameManager.getGameConfig(),
                gameManager.getThemeConfig()
        );
        this.timeElapsed = new TimeElapsed(gameManager);

        this.helpButton = new HelpButton(gameManager.getThemeConfig(), e ->
                HelpDialog.show(gameManager.getMainFrame(), gameManager.getThemeConfig())
        );

        this.pauseButton = new PauseButton(gameManager.getThemeConfig(), e ->
                ResumeDialog.show(gameManager.getMainFrame(), gameManager)
        );

        this.backButton = new BackButton(gameManager.getThemeConfig(), gameManager);
        this.saveButton = new SaveButton(gameManager.getThemeConfig(), gameManager);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JPanel rightWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightWrapper.setOpaque(false);
        rightWrapper.add(helpButton);
        rightWrapper.add(pauseButton);

        JPanel leftWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftWrapper.setOpaque(false);
        leftWrapper.add(backButton);
        leftWrapper.add(saveButton);
        leftWrapper.setMinimumSize(new Dimension(100, 45));

        headerPanel.add(leftWrapper, BorderLayout.WEST);
        headerPanel.add(timeElapsed, BorderLayout.CENTER);
        headerPanel.add(rightWrapper, BorderLayout.EAST);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerWrapper.add(gamePanel);

        add(headerPanel, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);
    }

    public void refresh() {
        gamePanel.removeAll();
        gamePanel.initializeLayout();
        gamePanel.revalidate();
        gamePanel.repaint();
    }
}