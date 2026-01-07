package main.game.presentation.elements.navigation;

import main.game.service.GameManager;
import main.settings.domain.models.ThemeConfig;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;

public class SaveButton extends JButton {
    private final ThemeConfig theme;

    public SaveButton(ThemeConfig theme, GameManager gameManager) {
        this.theme = theme;
        setPreferredSize(new Dimension(45, 45));
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setToolTipText("Save");

        addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Lightkeeper Save (*.sav)", "sav"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = fileChooser.getSelectedFile();
                if (!f.getName().endsWith(".sav")) f = new File(f.getAbsolutePath() + ".sav");
                gameManager.saveGame(f);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int size = Math.min(getWidth(), getHeight());

        if (getModel().isRollover()) {
            Color tc = theme.getColorText();
            g2.setColor(new Color(tc.getRed(), tc.getGreen(), tc.getBlue(), 40));
            g2.fillOval(0, 0, size, size);
        }

        g2.setColor(theme.getColorText());
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(1, 1, size - 2, size - 2);

        Image img = theme.getSaveImage();
        if (img != null) {
            int iconSize = (int)(size * 0.55);
            int x = (getWidth() - iconSize) / 2;
            int y = (getHeight() - iconSize) / 2;
            g2.drawImage(img, x, y, iconSize, iconSize, null);
        }
    }
}