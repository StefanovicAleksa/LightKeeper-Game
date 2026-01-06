package settings;

import java.awt.Color;

public class ThemeConfig {
    private Color colorWall;
    private Color colorRegular;
    private Color colorLit;
    private Color colorBulbError;
    private Color colorMark;
    private int cellPixelSize;

    public ThemeConfig() {
        this.colorWall = Color.BLACK;
        this.colorRegular = Color.WHITE;
        this.colorLit = Color.YELLOW;
        this.colorBulbError = Color.RED;
        this.colorMark = Color.GRAY;
        this.cellPixelSize = 50;
    }

    public Color getColorWall() { return colorWall; }
    public void setColorWall(Color c) { this.colorWall = c; }

    public Color getColorRegular() { return colorRegular; }
    public void setColorRegular(Color c) { this.colorRegular = c; }

    public Color getColorLit() { return colorLit; }
    public void setColorLit(Color c) { this.colorLit = c; }

    public Color getColorBulbError() { return colorBulbError; }
    public void setColorBulbError(Color c) { this.colorBulbError = c; }

    public Color getColorMark() { return colorMark; }
    public void setColorMark(Color c) { this.colorMark = c; }

    public int getCellPixelSize() { return cellPixelSize; }
    public void setCellPixelSize(int size) { this.cellPixelSize = size; }
}
