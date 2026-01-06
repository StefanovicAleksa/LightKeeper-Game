import game.elements.CellGrid;
import game.presentation.GamePanel;
import settings.GameConfig;
import settings.ThemeConfig;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class AkariGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Akari Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // 1. Settings
            GameConfig gameConfig = new GameConfig(7, 0.3); // 7x7 Grid
            ThemeConfig themeConfig = new ThemeConfig();

            // 2. Logic
            CellGrid grid = new CellGrid(gameConfig);

            // 3. UI
            GamePanel panel = new GamePanel(grid, gameConfig, themeConfig);
            frame.add(panel, BorderLayout.CENTER);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}