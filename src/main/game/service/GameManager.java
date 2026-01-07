package main.game.service;

import main.game.domain.enums.GameState;
import main.game.domain.models.game.Cell;
import main.game.domain.models.game.CellGrid;
import main.game.domain.models.game.RegularCell;
import main.game.domain.models.session.GameSession;
import main.game.presentation.elements.dialogs.VictoryDialog;
import main.game.presentation.screens.GameScreen;
import main.game.utilities.GameSolver;
import main.settings.GameConfig;
import main.settings.ThemeConfig;

import javax.swing.SwingUtilities;

public class GameManager {
    private final GameConfig gameConfig;
    private final ThemeConfig themeConfig;
    private final CellGrid grid;
    private GameSession currentSession;
    private GameScreen gameScreen;

    public GameManager() {
        this.gameConfig = new GameConfig(7, 0.3);
        this.themeConfig = new ThemeConfig();
        this.grid = new CellGrid(gameConfig);
        startNewGame();
    }

    public void run() {
        SwingUtilities.invokeLater(() -> {
            this.gameScreen = new GameScreen(this);
            this.gameScreen.show();
        });
    }

    public void startNewGame() {
        this.grid.initializeCells();

        this.currentSession = new GameSession();
        this.currentSession.saveInitialBoardState(grid);
        this.currentSession.saveBoardState(grid);
        this.currentSession.start();

        if (gameScreen != null) {
            gameScreen.refresh();
        }
    }

    public int getOptimalBulbCount() {
        return new GameSolver(grid).solve();
    }

    public void restartLevel() {
        if (currentSession != null) {
            currentSession.restoreInitialBoardState(grid);
            currentSession.reset();
            currentSession.saveBoardState(grid);
            recalculateLighting();

            if (gameScreen != null) {
                gameScreen.refresh();
            }
        }
    }

    public void pauseGame() {
        if (currentSession != null) {
            currentSession.pause();
        }
    }

    public void resumeGame() {
        if (currentSession != null) {
            currentSession.resume();
        }
    }

    public void updateGameState() {
        if (currentSession.getCurrentState() != GameState.PLAYING) return;
        currentSession.saveBoardState(grid);

        if (grid.isSolved()) {
            currentSession.complete();
            int userBulbs = grid.getBulbCount();

            SwingUtilities.invokeLater(() -> {
                if (gameScreen != null) {
                    VictoryDialog.show(gameScreen.getFrame(), this, userBulbs);
                }
            });
        }
    }

    private void recalculateLighting() {
        int n = grid.getCells().length;
        Cell[][] cells = grid.getCells();

        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                if(cells[i][j] instanceof RegularCell rc) {
                    rc.changeLightLevel( -rc.getLightLevel() );
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

    public CellGrid getGrid() { return grid; }
    public GameConfig getGameConfig() { return gameConfig; }
    public ThemeConfig getThemeConfig() { return themeConfig; }
    public GameSession getCurrentSession() { return currentSession; }
}