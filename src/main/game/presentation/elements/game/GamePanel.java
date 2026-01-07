package main.game.presentation.elements.game;

import main.game.domain.enums.GameState;
import main.game.domain.models.game.Cell;
import main.game.domain.models.game.CellGrid;
import main.game.domain.models.game.RegularCell;
import main.game.service.GameManager;
import main.settings.domain.models.GameConfig;
import main.settings.domain.models.ThemeConfig;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    private final CellGrid grid;
    private final GameManager gameManager;
    private final ThemeConfig theme;
    private final GameConfig config;

    public GamePanel(CellGrid grid, GameManager gameManager, GameConfig config, ThemeConfig theme) {
        this.grid = grid;
        this.gameManager = gameManager;
        this.config = config;
        this.theme = theme;

        initializeLayout();
    }

    public void initializeLayout() {
        int n = config.getGridSize();
        setLayout(new GridLayout(n, n, 2, 2));
        setOpaque(false);

        Cell[][] cells = grid.getCells();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Cell cell = cells[i][j];
                CellButton button = new CellButton(i, j, cell, theme);

                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        handleInput(e, button);
                    }
                });

                add(button);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int n = config.getGridSize();
        int defaultSize = n * theme.getCellPixelSize();

        if (getParent() != null) {
            int w = getParent().getWidth();
            int h = getParent().getHeight();

            if (w > 0 && h > 0) {
                int side = Math.min(w, h);
                side = Math.max(side, 100);
                return new Dimension(side, side);
            }
        }

        return new Dimension(defaultSize, defaultSize);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    private void handleInput(MouseEvent e, CellButton source) {
        if (gameManager.getCurrentSession().getCurrentState() != GameState.PLAYING) {
            return;
        }

        int r = source.getRow();
        int c = source.getCol();

        if (SwingUtilities.isRightMouseButton(e)) {
            grid.toggleMark(r, c);
        } else if (SwingUtilities.isLeftMouseButton(e)) {
            if (source.getCell() instanceof RegularCell rc && rc.isMarked())
                grid.toggleMark(r, c);
            else
                grid.toggleBulb(r, c);
        }

        gameManager.updateGameState();
        repaint();
    }
}