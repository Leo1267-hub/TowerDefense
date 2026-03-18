package ui;

import logic.GamePanel;
import logic.GameState;
import java.awt.*;

public class GameHUD {
    private static int panelWidth = 280;
    private static int panelHeight = 110;
    private static int margin = 20;
    private static int panelX = GamePanel.WIDTH - panelWidth - margin;
    private static int panelY = margin;

    public static void drawHud(Graphics2D g2d) {

        g2d.setColor(new Color(0, 0, 0, 160));
        g2d.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 18, 18);

        g2d.setColor(new Color(240, 240, 240));
        g2d.setFont(new Font("SansSerif", Font.BOLD, 20));
        g2d.drawString("Money: $" + GameState.money, panelX + 16, panelY + 34);

        int barX = panelX + 16;
        int barY = panelY + 56;
        int barWidth = panelWidth - 32;
        int barHeight = 24;

        g2d.setColor(new Color(70, 70, 70));
        g2d.fillRoundRect(barX, barY, barWidth, barHeight, 12, 12);

        int healthWidth = (int) ((GameState.health / (double) GameState.MAX_HEALTH) * barWidth);
        g2d.setColor(new Color(54, 196, 95));
        g2d.fillRoundRect(barX, barY, healthWidth, barHeight, 12, 12);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
        g2d.drawString("Health: " + GameState.health + "/" + GameState.MAX_HEALTH, barX + 8, barY + 18);

        if (GameState.moneyPopupTimer > 0) {
            g2d.setColor(new Color(140, 255, 140));
            g2d.setFont(new Font("SansSerif", Font.BOLD, 18));
            g2d.drawString("+" + GameState.ENEMY_KILL_REWARD + "$", panelX + 150, panelY + 34);
        }
    }
}
