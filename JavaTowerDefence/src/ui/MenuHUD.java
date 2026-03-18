package ui;

import logic.GamePanel;

import java.awt.*;

public class MenuHUD {

    private final Rectangle startButton = new Rectangle(GamePanel.WIDTH / 2 - 150, GamePanel.HEIGHT / 2 - 10, 300, 70);
    private final Rectangle exitButton = new Rectangle(GamePanel.WIDTH / 2 - 150, GamePanel.HEIGHT / 2 + 85, 300, 70);
    private boolean startHovered;
    private boolean exitHovered;

    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 165));
        g2d.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 74));
        String title = "Tower Defense";
        FontMetrics titleMetrics = g2d.getFontMetrics();
        int titleX = (GamePanel.WIDTH - titleMetrics.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, GamePanel.HEIGHT / 2 - 80);

        drawButton(g2d, startButton, "Start Game", new Color(33, 142, 88), startHovered);
        drawButton(g2d, exitButton, "Exit", new Color(183, 47, 47), exitHovered);
    }

    public boolean isStartClicked(int x, int y) {
        return startButton.contains(x, y);
    }

    public boolean isExitClicked(int x, int y) {
        return exitButton.contains(x, y);
    }

    public void updateHover(int x, int y) {
        startHovered = startButton.contains(x, y);
        exitHovered = exitButton.contains(x, y);
    }

    public void clearHover() {
        startHovered = false;
        exitHovered = false;
    }

    private void drawButton(Graphics2D g2d, Rectangle button, String text, Color fillColor, boolean hovered) {
        Color buttonColor = hovered ? fillColor.brighter() : fillColor;
        g2d.setColor(buttonColor);
        g2d.fillRoundRect(button.x, button.y, button.width, button.height, 16, 16);

        g2d.setColor(new Color(255, 255, 255, hovered ? 230 : 180));
        g2d.setStroke(new BasicStroke(hovered ? 4f : 2f));
        g2d.drawRoundRect(button.x, button.y, button.width, button.height, 16, 16);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 34));
        FontMetrics buttonMetrics = g2d.getFontMetrics();
        int textX = button.x + (button.width - buttonMetrics.stringWidth(text)) / 2;
        int textY = button.y + ((button.height - buttonMetrics.getHeight()) / 2) + buttonMetrics.getAscent();
        g2d.drawString(text, textX, textY);
    }
}
