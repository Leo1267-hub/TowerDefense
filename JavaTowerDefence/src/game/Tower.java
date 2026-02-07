package game;

import java.awt.*;
import java.util.ArrayList;

public class Tower {

    // position of the tower
    private int x, y;
    // range of the tower
    private int range = 120;
    // track time until the next shot can be fired
    private int cooldown = 0;

    // constructor to create a tower at a specific position
    public Tower(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // updates the tower once per frame
    public void update(ArrayList<Enemy> enemies, ArrayList<Projectile> projectiles) {
        // checks if the tower is waiting for the next shot
        // if it is, it decreases the cooldown timer and does nothing else
        if (cooldown > 0) {
            cooldown--;
            return;
        }

        // for each enemy checks if the enemy in within range of the tower
        for (Enemy e : enemies) {
            double dx = e.getX() - x;
            double dy = e.getY() - y;
            double dist = Math.sqrt(dx * dx + dy * dy);

            // if it is, it creates a new projectile targeting that enemy and resets the
            // cooldown timer
            if (dist <= range) {
                projectiles.add(new Projectile(x, y, e));
                cooldown = 60;
                break;
            }
        }
    }

    // draw the tower
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x - 10, y - 10, 20, 20);
    }
}
