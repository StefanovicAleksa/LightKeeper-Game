package main.settings;

import java.awt.Color;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ThemeConfig {
    private Color colorWall;
    private Color colorRegular;
    private Color colorLit;
    private Color colorBulbError;
    private Color colorMark;
    private int cellPixelSize;

    private Image bulbImage;
    private Image markImage;

    public ThemeConfig() {
        this.colorWall = Color.BLACK;
        this.colorRegular = Color.WHITE;
        this.colorLit = new Color(255, 255, 153);
        this.colorBulbError = new Color(255, 102, 102);
        this.colorMark = Color.GRAY;
        this.cellPixelSize = 50;

        loadImages();
    }

    private void loadImages() {
        try {
            bulbImage = ImageIO.read(new File("src/resources/images/light-bulb.png"));
            markImage = ImageIO.read(new File("src/resources/images/close.png"));
        } catch (IOException e) {
            System.out.println("Failed loading images");
            bulbImage = null;
            markImage = null;
        }
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

    public Image getBulbImage() { return bulbImage; }
    public Image getMarkImage() { return markImage; }
}