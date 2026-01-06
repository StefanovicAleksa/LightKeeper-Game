package main.game.elements;

public class WallCell extends Cell {
    @Override
    public boolean blocksLight() {
        return true;
    }
}
