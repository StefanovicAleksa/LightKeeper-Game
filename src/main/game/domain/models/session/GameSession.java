package main.game.domain.models.session;

import main.game.domain.enums.GameState;
import main.game.domain.models.game.Cell;
import main.game.domain.models.game.CellGrid;
import main.game.domain.models.game.RegularCell;
import main.game.domain.models.game.WallCell;

import java.io.Serializable;
import java.util.UUID;

public class GameSession implements Serializable {
    private final String sessionId;
    private long startTime;
    private long accumulatedTime;
    private GameState currentState;

    private CellSnapshot[][] boardSnapshot;
    private CellSnapshot[][] initialBoardSnapshot;
    private int savedGridSize;

    public GameSession() {
        this.sessionId = UUID.randomUUID().toString();
        this.currentState = GameState.NOT_STARTED;
        this.accumulatedTime = 0;
    }

    public void start() {
        if (currentState != GameState.PLAYING) {
            this.startTime = System.currentTimeMillis();
            this.currentState = GameState.PLAYING;
        }
    }

    public void pause() {
        if (currentState == GameState.PLAYING) {
            long now = System.currentTimeMillis();
            accumulatedTime += (now - startTime);
            currentState = GameState.PAUSED;
        }
    }

    public void resume() {
        if (currentState == GameState.PAUSED) {
            this.startTime = System.currentTimeMillis();
            this.currentState = GameState.PLAYING;
        }
    }

    public void complete() {
        if (currentState == GameState.PLAYING) {
            long now = System.currentTimeMillis();
            accumulatedTime += (now - startTime);
            currentState = GameState.SOLVED;
        }
    }

    public void reset() {
        this.accumulatedTime = 0;
        this.startTime = System.currentTimeMillis();
        this.currentState = GameState.PLAYING;
    }

    public void saveBoardState(CellGrid grid) {
        this.boardSnapshot = createSnapshot(grid);
    }

    public void saveInitialBoardState(CellGrid grid) {
        this.initialBoardSnapshot = createSnapshot(grid);
        this.savedGridSize = grid.getCells().length;
    }

    private CellSnapshot[][] createSnapshot(CellGrid grid) {
        Cell[][] cells = grid.getCells();
        int n = cells.length;
        CellSnapshot[][] snapshot = new CellSnapshot[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Cell c = cells[i][j];
                boolean isWall = c instanceof WallCell;
                boolean hasBulb = false;
                boolean isMarked = false;

                if (c instanceof RegularCell rc) {
                    hasBulb = rc.hasBulb();
                    isMarked = rc.isMarked();
                }

                snapshot[i][j] = new CellSnapshot(isWall, hasBulb, isMarked);
            }
        }
        return snapshot;
    }

    public void restoreBoardState(CellGrid grid) {
        restoreFromSnapshot(grid, boardSnapshot);
    }

    public void restoreInitialBoardState(CellGrid grid) {
        restoreFromSnapshot(grid, initialBoardSnapshot);
    }

    private void restoreFromSnapshot(CellGrid grid, CellSnapshot[][] snapshot) {
        if (snapshot == null) return;

        Cell[][] cells = grid.getCells();
        if (cells.length != savedGridSize) return;

        for (int i = 0; i < savedGridSize; i++) {
            for (int j = 0; j < savedGridSize; j++) {
                CellSnapshot snap = snapshot[i][j];

                if (snap.isWall) {
                    cells[i][j] = new WallCell();
                } else {
                    RegularCell rc = new RegularCell();
                    cells[i][j] = rc;

                    if (snap.isMarked) rc.setMarked(true);
                    if (snap.hasBulb) rc.setHasBulb(true);
                }
            }
        }
    }

    public long getTotalPlayTimeSeconds() {
        long currentSegment = 0;
        if (currentState == GameState.PLAYING) {
            currentSegment = System.currentTimeMillis() - startTime;
        }
        return (accumulatedTime + currentSegment) / 1000;
    }

    public String getSessionId() { return sessionId; }
    public GameState getCurrentState() { return currentState; }
    public void setCurrentState(GameState s) { this.currentState = s; }

    private static class CellSnapshot implements Serializable {
        boolean isWall;
        boolean hasBulb;
        boolean isMarked;

        CellSnapshot(boolean w, boolean b, boolean m) {
            this.isWall = w;
            this.hasBulb = b;
            this.isMarked = m;
        }
    }
}