package game.presentation;

import game.elements.Cell;
import game.elements.CellGrid;
import settings.GameConfig;
import settings.ThemeConfig;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    private final CellGrid grid;
    private final ThemeConfig theme;
    private final GameConfig config;

    public GamePanel(CellGrid grid, GameConfig config, ThemeConfig theme) {
        this.grid = grid;
        this.config = config;
        this.theme = theme;

        initializeLayout();
    }

    private void initializeLayout() {
        int n = config.getGridSize();
        setLayout(new GridLayout(n, n));

        // Calculate window size based on theme
        int pixelSize = n * theme.getCellPixelSize();
        setPreferredSize(new Dimension(pixelSize, pixelSize));

        // Use the method you requested
        Cell[][] cells = grid.getCells();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Access array directly
                Cell cell = cells[i][j];

                CellButton button = new CellButton(i, j, cell, theme);

                // Add Click Handler
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

    private void handleInput(MouseEvent e, CellButton source) {
        int r = source.getRow();
        int c = source.getCol();

        if (SwingUtilities.isRightMouseButton(e)) {
            grid.toggleMark(r, c);
        } else if (SwingUtilities.isLeftMouseButton(e)) {
            grid.toggleBulb(r, c);
        }

        // Force UI update
        repaint();
    }
}