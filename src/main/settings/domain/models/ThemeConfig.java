package main.settings.domain.models;

import java.awt.Color;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;

public class ThemeConfig {
    private Color colorWall;
    private Color colorRegular;
    private Color colorLit;
    private Color colorBulbError;
    private Color colorMark;
    private Color colorBackground;
    private Color colorButton;
    private Color colorText;
    private Color colorSubtext;
    private int cellPixelSize;

    private Image bulbImage;
    private Image markImage;
    private Image backImage;
    private Image saveImage;
    private Image helpImage;
    private Image pauseImage;

    public ThemeConfig() {
        this.colorWall = Color.BLACK;
        this.colorRegular = Color.WHITE;
        this.colorLit = new Color(255, 255, 153);
        this.colorBulbError = new Color(255, 102, 102);
        this.colorMark = Color.GRAY;
        this.colorBackground = new Color(35, 35, 40);
        this.colorButton = new Color(60, 60, 65);
        this.colorText = new Color(220, 220, 220);
        this.colorSubtext = new Color(160, 160, 160);
        this.cellPixelSize = 50;
        loadImages();
    }

    private void loadImages() {
        try { bulbImage = ImageIO.read(new File("src/resources/images/light-bulb.png")); } catch (Exception e) { bulbImage = null; }
        try { markImage = ImageIO.read(new File("src/resources/images/close.png")); } catch (Exception e) { markImage = null; }
        try { backImage = ImageIO.read(new File("src/resources/images/arrow-back.png")); } catch (Exception e) { backImage = null; }
        try { saveImage = ImageIO.read(new File("src/resources/images/download.png")); } catch (Exception e) { saveImage = null; }
        try { helpImage = ImageIO.read(new File("src/resources/images/question.png")); } catch (Exception e) { helpImage = null; }
        try { pauseImage = ImageIO.read(new File("src/resources/images/pause.png")); } catch (Exception e) { pauseImage = null; }
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
    public Color getColorBackground() { return colorBackground; }
    public void setColorBackground(Color c) { this.colorBackground = c; }
    public Color getColorButton() { return colorButton; }
    public void setColorButton(Color c) { this.colorButton = c; }
    public Color getColorText() { return colorText; }
    public void setColorText(Color c) { this.colorText = c; }
    public Color getColorSubtext() { return colorSubtext; }
    public void setColorSubtext(Color c) { this.colorSubtext = c; }
    public int getCellPixelSize() { return cellPixelSize; }
    public Image getBulbImage() { return bulbImage; }
    public Image getMarkImage() { return markImage; }
    public Image getBackImage() { return backImage; }
    public Image getSaveImage() { return saveImage; }
    public Image getHelpImage() { return helpImage; }
    public Image getPauseImage() { return pauseImage; }
}