package main.game.presentation.elements.game;

import main.game.service.GameManager;
import main.settings.domain.models.ThemeConfig;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.util.function.Consumer;

public class CheckOptimalLabel extends JLabel {

    public CheckOptimalLabel(ThemeConfig theme, GameManager gameManager, Consumer<Integer> onResult) {
        setText("Check Optimal");
        Font font = new Font("Segoe UI", Font.PLAIN, 12);

        @SuppressWarnings("unchecked")
        Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        setFont(font.deriveFont(attributes));

        setForeground(theme.getColorSubtext());
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setAlignmentX(CENTER_ALIGNMENT);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeMouseListener(this);
                setFont(new Font("Segoe UI", Font.PLAIN, 12));
                setText("Calculating...");
                setCursor(new Cursor(Cursor.WAIT_CURSOR));

                new Thread(() -> {
                    int best = gameManager.getOptimalBulbCount();
                    SwingUtilities.invokeLater(() -> {
                        onResult.accept(best);
                        setVisible(false);
                    });
                }).start();
            }
        });
    }
}