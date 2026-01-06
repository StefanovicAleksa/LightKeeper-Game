package main.game.elements;

import main.settings.GameConfig;

public class CellGrid {
    private GameConfig config;
    private Cell[][] cells;

    public CellGrid(GameConfig config)
    {
        this.config = config;
        initializeCells();
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void initializeCells()
    {
        int n = config.getGridSize();
        double r = config.getWallRatio();

        cells = new Cell[n][n];

        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(Math.random() <= r)
                    cells[i][j] = new WallCell();
                else
                    cells[i][j] = new RegularCell();
            }
        }
    }

    public void toggleMark(int i, int j) {
        if (cells[i][j] instanceof RegularCell cell && !cell.hasBulb()) {
            cell.setMarked(!cell.isMarked());
        }
    }

    public void toggleBulb(int i, int j) {
        if(cells[i][j] instanceof RegularCell cell && !cell.isMarked()) {
            boolean toggleOn = !cell.hasBulb();

            cell.setHasBulb(toggleOn);
            if(toggleOn)
                cell.changeLightLevel(1);
            else {
                cell.changeLightLevel(-1);
                cell.resetBulbCollisionLevel();
            }

            handleLightRay(i, j, 'n', toggleOn, cell);
            handleLightRay(i, j, 's', toggleOn, cell);
            handleLightRay(i, j, 'w', toggleOn, cell);
            handleLightRay(i, j, 'e', toggleOn, cell);

        }
    }

    private void handleLightRay(int i, int j, char direction, boolean toggleOn, RegularCell source)
    {
        switch (direction) {
            case 'n': {
                for (i = i - 1; i >= 0 && !cells[i][j].blocksLight(); i--) {
                    handleLightChange(i, j, toggleOn, source);
                }
                break;
            }
            case 's': {
                for (i = i + 1; i < config.getGridSize() && !cells[i][j].blocksLight(); i++) {
                    handleLightChange(i, j, toggleOn, source);
                }
                break;
            }
            case 'w': {
                for (j = j + 1; j < config.getGridSize() && !cells[i][j].blocksLight(); j++) {
                    handleLightChange(i, j, toggleOn, source);
                }
                break;
            }
            case 'e': {
                for (j = j - 1; j >= 0 && !cells[i][j].blocksLight(); j--) {
                    handleLightChange(i, j, toggleOn, source);
                }
                break;
            }
        }

    }

    private void handleLightChange(int i, int j, boolean toggleOn, RegularCell source) {
        RegularCell cell = (RegularCell) cells[i][j];
        if(toggleOn) {
            cell.changeLightLevel(1);
            if(cell.hasBulb()) {
                cell.changeBulbCollisionLevel(1);
                source.changeBulbCollisionLevel(1);
            }
        }
        else {
            cell.changeLightLevel(-1);
            if(cell.hasBulb())
                cell.changeBulbCollisionLevel(-1);
        }
    }


    public boolean isSolved()
    {
        int n = config.getGridSize();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cells[i][j] instanceof RegularCell cell && (cell.getLightLevel() == 0 || cell.getBulbCollisionLevel() > 0))
                    return false;
            }
        }

        return true;
    }



}
