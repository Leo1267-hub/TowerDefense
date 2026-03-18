package ui;

import logic.GamePanel;

import java.awt.*;

public class EndGameHUD {

    private final Rectangle restartButton = new Rectangle(GamePanel.WIDTH / 2 - 110, GamePanel.HEIGHT / 2 + 20, 220,
            60);
    private boolean restartHovered;

    public void draw(Graphics2D g2d, boolean win) {
        g2d.setColor(new Color(0, 0, 0, 175));
        g2d.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 72));
        String title = win ? "You Win" : "Game Over";
        FontMetrics titleMetrics = g2d.getFontMetrics();
        int titleX = (GamePanel.WIDTH - titleMetrics.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, GamePanel.HEIGHT / 2 - 20);

        Color restartColor = restartHovered ? new Color(53, 190, 118) : new Color(40, 155, 95);
        g2d.setColor(restartColor);
        g2d.fillRoundRect(restartButton.x, restartButton.y, restartButton.width, restartButton.height, 16, 16);

        g2d.setColor(new Color(255, 255, 255, restartHovered ? 230 : 180));
        g2d.setStroke(new BasicStroke(restartHovered ? 4f : 2f));
        g2d.drawRoundRect(restartButton.x, restartButton.y, restartButton.width, restartButton.height, 16, 16);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 30));
        String buttonText = "Restart";
        FontMetrics buttonMetrics = g2d.getFontMetrics();
        int textX = restartButton.x + (restartButton.width - buttonMetrics.stringWidth(buttonText)) / 2;
        int textY = restartButton.y + ((restartButton.height - buttonMetrics.getHeight()) / 2)
                + buttonMetrics.getAscent();
        g2d.drawString(buttonText, textX, textY);
    }

    public boolean isRestartClicked(int x, int y) {
        return restartButton.contains(x, y);
    }

    public void updateHover(int x, int y) {
        restartHovered = restartButton.contains(x, y);
    }

    public void clearHover() {
        restartHovered = false;
    }
}
