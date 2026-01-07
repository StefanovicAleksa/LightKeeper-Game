package main;

import main.game.service.GameManager;
import javax.swing.SwingUtilities;

public class LightkeeperGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameManager gameManager = new GameManager();
            gameManager.start();
        });
    }
}