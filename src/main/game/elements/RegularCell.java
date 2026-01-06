package main.game.elements;

public class RegularCell extends Cell {
    private boolean isMarked;
    private boolean hasBulb;
    private int lightLevel;
    private int bulbCollisionLevel;

    public RegularCell()
    {
        isMarked = false;
        hasBulb = false;
        lightLevel = 0;
        bulbCollisionLevel = 0;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public boolean hasBulb() {
        return hasBulb;
    }

    public void setHasBulb(boolean hasBulb) {
        this.hasBulb = hasBulb;
    }

    public int getLightLevel() {
        return lightLevel;
    }

    public void changeLightLevel(int change) {
        this.lightLevel += change;
    }

    public int getBulbCollisionLevel() {
        return bulbCollisionLevel;
    }

    public void changeBulbCollisionLevel(int change) {
        this.bulbCollisionLevel += change;
    }

    public void resetBulbCollisionLevel() {
        bulbCollisionLevel = 0;
    }

    @Override
    public boolean blocksLight() {
        return false;
    }
}
