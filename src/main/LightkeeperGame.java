package main;

import main.game.service.GameManager;

public class LightkeeperGame {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
        gameManager.run();
    }
}