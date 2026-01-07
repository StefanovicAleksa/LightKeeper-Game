package main.game.domain.models.game;

import java.io.Serializable;

public abstract class Cell implements Serializable {
    private static final long serialVersionUID = 1L;

    public abstract boolean blocksLight();
}