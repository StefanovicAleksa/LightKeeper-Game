package main.game.presentation.elements.game;

import main.game.domain.enums.GameState;
import main.game.domain.models.game.Cell;
import main.game.domain.models.game.CellGrid;
import main.game.domain.models.game.RegularCell;
import main.game.service.GameManager;
import main.settings.GameConfig;
import main.settings.ThemeConfig;

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
        // Use a 2px gap to match the look of the working version
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
        // Calculate the ideal size based on configuration (cells * pixels)
        int n = config.getGridSize();
        int defaultSize = n * theme.getCellPixelSize();

        // If we are attached to a parent, try to fit inside it while maintaining a square aspect ratio
        if (getParent() != null) {
            int w = getParent().getWidth();
            int h = getParent().getHeight();

            // Only resize if the parent has valid dimensions
            if (w > 0 && h > 0) {
                // Find the smallest dimension to ensure the square fits entirely
                int side = Math.min(w, h);
                // Keep a minimum size to prevent UI collapse
                side = Math.max(side, 100);
                return new Dimension(side, side);
            }
        }

        // Fallback size for initial packing or if parent is invalid
        return new Dimension(defaultSize, defaultSize);
    }

    // Force minimum size to match preferred to prevent layout managers from squashing it
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