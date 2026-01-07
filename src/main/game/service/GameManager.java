package main.game.service;

import main.game.domain.enums.GameState;
import main.game.domain.models.game.Cell;
import main.game.domain.models.game.CellGrid;
import main.game.domain.models.game.RegularCell;
import main.game.domain.models.session.GameSession;
import main.game.presentation.elements.dialogs.VictoryDialog;
import main.game.presentation.screens.GameScreen;
import main.game.utilities.GameSolver;
import main.menu.presentation.screens.MainMenuScreen;
import main.settings.domain.models.GameConfig;
import main.settings.domain.models.ThemeConfig;
import main.settings.presentation.screens.ThemeConfigScreen;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameManager {
    private final JFrame mainFrame;
    private GameConfig gameConfig;
    private final ThemeConfig themeConfig;
    private CellGrid grid;
    private GameSession currentSession;

    private GameScreen gameScreen;

    private MainMenuScreen mainMenuScreen;
    private ThemeConfigScreen themeConfigScreen;

    public GameManager() {
        this.gameConfig = new GameConfig(15, 0.3);
        this.themeConfig = new ThemeConfig();

        this.mainFrame = new JFrame("Lightkeeper");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setSize(800, 800);
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setLayout(new BorderLayout());
    }

    public void start() {
        showMainMenu();
        this.mainFrame.setVisible(true);
    }

    public void showMainMenu() {
        mainMenuScreen = new MainMenuScreen(this);
        setContent(mainMenuScreen);
    }

    public void showThemeSettings() {
        if (themeConfigScreen == null) {
            themeConfigScreen = new ThemeConfigScreen(this);
        }
        setContent(themeConfigScreen);
    }

    public void startGame(GameConfig config) {
        this.gameConfig = config;

        this.grid = new CellGrid(gameConfig);
        this.grid.initializeCells();

        this.currentSession = new GameSession();
        this.currentSession.saveInitialBoardState(grid);
        this.currentSession.saveBoardState(grid);
        this.currentSession.start();

        launchGameScreen();
    }

    public void resumeGame() {
        if (currentSession != null && currentSession.getCurrentState() != GameState.SOLVED) {
            currentSession.resume();
            launchGameScreen();
        }
    }

    private void launchGameScreen() {
        this.gameScreen = new GameScreen(this);
        setContent(this.gameScreen);
    }

    private void setContent(JPanel panel) {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(panel, BorderLayout.CENTER);

        Color bg = themeConfig.getColorBackground();
        panel.setBackground(bg);
        mainFrame.getContentPane().setBackground(bg);

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void pauseGame() {
        if (currentSession != null) {
            currentSession.pause();
        }
    }

    public void restartLevel() {
        if (currentSession != null) {
            currentSession.restoreInitialBoardState(grid);
            currentSession.reset();
            currentSession.saveBoardState(grid);
            recalculateLighting();
            if (gameScreen != null) gameScreen.refresh();
        }
    }

    public int getOptimalBulbCount() {
        return new GameSolver(grid).solve();
    }

    public void updateGameState() {
        if (currentSession == null || currentSession.getCurrentState() != GameState.PLAYING) return;

        currentSession.saveBoardState(grid);
        if (grid.isSolved()) {
            currentSession.complete();
            int userBulbs = grid.getBulbCount();

            SwingUtilities.invokeLater(() ->
                    VictoryDialog.show(mainFrame, this, userBulbs)
            );
        }
    }

    public void saveGame(File file) {
        if (currentSession == null) return;
        currentSession.saveBoardState(grid);
        pauseGame();

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(currentSession);
            out.writeObject(gameConfig.getGridSize());
            out.writeObject(gameConfig.getWallRatio());
            out.writeObject(grid);

            JOptionPane.showMessageDialog(mainFrame, "Game saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Error saving game: " + e.getMessage());
        }
    }

    public void loadGame(File file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            GameSession loadedSession = (GameSession) in.readObject();
            int loadedSize = (int) in.readObject();
            double loadedRatio = (double) in.readObject();
            CellGrid loadedGrid = (CellGrid) in.readObject();

            this.gameConfig.setGridSize(loadedSize);
            this.gameConfig.setWallRatio(loadedRatio);

            this.currentSession = loadedSession;
            this.grid = loadedGrid;

            this.currentSession.restoreBoardState(grid);
            recalculateLighting();

            launchGameScreen();

            if (currentSession.getCurrentState() == GameState.PAUSED ||
                    currentSession.getCurrentState() == GameState.PLAYING) {
                currentSession.resume();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Error loading game: " + e.getMessage());
        }
    }

    private void recalculateLighting() {
        int n = grid.getCells().length;
        Cell[][] cells = grid.getCells();
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                if(cells[i][j] instanceof RegularCell rc) {
                    rc.changeLightLevel(-rc.getLightLevel());
                    rc.resetBulbCollisionLevel();
                }
            }
        }
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                if(cells[i][j] instanceof RegularCell rc && rc.hasBulb()) {
                    rc.setHasBulb(false);
                    grid.toggleBulb(i, j);
                }
            }
        }
    }

    public JFrame getMainFrame() { return mainFrame; }
    public GameConfig getGameConfig() { return gameConfig; }
    public ThemeConfig getThemeConfig() { return themeConfig; }
    public CellGrid getGrid() { return grid; }
    public GameSession getCurrentSession() { return currentSession; }
}