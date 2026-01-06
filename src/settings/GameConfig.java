package settings;

public class GameConfig {
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
