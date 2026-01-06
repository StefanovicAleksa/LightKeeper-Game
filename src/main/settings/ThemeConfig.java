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
    private Color colorBackground;
    private Color colorText;
    private int cellPixelSize;

    private Image bulbImage;
    private Image markImage;

    public ThemeConfig() {
        this.colorWall = Color.BLACK;
        this.colorRegular = Color.WHITE;
        this.colorLit = new Color(255, 255, 153);
        this.colorBulbError = new Color(255, 102, 102);
        this.colorMark = Color.GRAY;

        this.colorBackground = new Color(35, 35, 40);
        this.colorText = new Color(220, 220, 220);

        this.cellPixelSize = 50;

        loadImages();
    }

    private void loadImages() {
        try {
            bulbImage = ImageIO.read(new File("src/resources/images/light-bulb.png"));
            markImage = ImageIO.read(new File("src/resources/images/close.png"));
        } catch (IOException e) {
            bulbImage = null;
            markImage = null;
        }
    }

    public Color getColorWall() { return colorWall; }
    public Color getColorRegular() { return colorRegular; }
    public Color getColorLit() { return colorLit; }
    public Color getColorBulbError() { return colorBulbError; }
    public Color getColorMark() { return colorMark; }
    public Color getColorBackground() { return colorBackground; }
    public Color getColorText() { return colorText; }

    public int getCellPixelSize() { return cellPixelSize; }
    public Image getBulbImage() { return bulbImage; }
    public Image getMarkImage() { return markImage; }
}