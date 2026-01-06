package main.game.domain.models.game;

public class WallCell extends Cell {
    @Override
    public boolean blocksLight() {
        return true;
    }
}
