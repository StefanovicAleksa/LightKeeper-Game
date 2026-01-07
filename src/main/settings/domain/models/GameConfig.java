package main.settings.domain.models;

import java.io.Serializable;

public class GameConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private int gridSize;
    private double wallRatio;

    public GameConfig(int gridSize, double wallRatio) {
        this.gridSize = gridSize;
        this.wallRatio = wallRatio;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public double getWallRatio() {
        return wallRatio;
    }

    public void setWallRatio(double wallRatio) {
        this.wallRatio = wallRatio;
    }
}
