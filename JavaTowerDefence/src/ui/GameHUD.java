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
        if (GameState.moneyPopdownTimer > 0) {
            int amount;
            g2d.setColor(new Color(255, 140, 140));
            g2d.setFont(new Font("SansSerif", Font.BOLD, 18));
            if (GameState.moneyPopupTimer > 0)
                amount = 200;
            else
                amount = 150;
            g2d.drawString("-" + GameState.TOWER_COST + "$", panelX + amount, panelY + 34);
        }
    }

    public static void drawEscapeFlash(Graphics2D g2d, float intensity) {
        int alpha = Math.max(0, Math.min(180, (int) (180 * intensity)));
        int thickness = 120;

        Paint oldPaint = g2d.getPaint();

        g2d.setPaint(new GradientPaint(0, 0, new Color(190, 20, 20, alpha), thickness, 0, new Color(190, 20, 20, 0)));
        g2d.fillRect(0, 0, thickness, GamePanel.HEIGHT);

        g2d.setPaint(
                new GradientPaint(GamePanel.WIDTH, 0, new Color(190, 20, 20, alpha), GamePanel.WIDTH - thickness, 0,
                        new Color(190, 20, 20, 0)));
        g2d.fillRect(GamePanel.WIDTH - thickness, 0, thickness, GamePanel.HEIGHT);

        g2d.setPaint(new GradientPaint(0, 0, new Color(190, 20, 20, alpha), 0, thickness, new Color(190, 20, 20, 0)));
        g2d.fillRect(0, 0, GamePanel.WIDTH, thickness);

        g2d.setPaint(
                new GradientPaint(0, GamePanel.HEIGHT, new Color(190, 20, 20, alpha), 0, GamePanel.HEIGHT - thickness,
                        new Color(190, 20, 20, 0)));
        g2d.fillRect(0, GamePanel.HEIGHT - thickness, GamePanel.WIDTH, thickness);

        g2d.setPaint(oldPaint);
    }
}
