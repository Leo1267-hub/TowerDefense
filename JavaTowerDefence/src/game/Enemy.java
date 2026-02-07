package game;

import java.awt.*;

public class Enemy {

    private double x, y;
    private int health = 100;
    private double speed = 1.5;
    private int pathIndex = 0;

    // constructor with default to watch the first point on the path
    public Enemy(Path path) {
        x = path.getPoint(0).x;
        y = path.getPoint(0).y;
    }

    // update movement of the enemy along the path
    public void update(Path path) {
        // once the enemy reaches the end of the path, it should stop moving
        if (pathIndex >= path.length())
            return;

        // otherwise it should move towards the next point on the path
        Point target = path.getPoint(pathIndex);
        double dx = target.x - x;
        double dy = target.y - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        // if it close enough to the target point, it should move to the next point on
        // the path
        if (dist < speed) {
            pathIndex++;
        } else {
            // otherwise it should move towards the target point at a constant speed
            x += dx / dist * speed;
            y += dy / dist * speed;
        }
    }

    // decrease the health of the enemy by a certain amount of damage
    public void damage(int dmg) {
        health -= dmg;
    }

    // return True if the enemy's health is 0 or less, indicating that it should be
    // removed from the game
    public boolean isDead() {
        return health <= 0;
    }

    // get x position of the enemy ( centre)
    public double getX() {
        return x;
    }

    // get y position of the enemy ( centre)
    public double getY() {
        return y;
    }

    // draw the enemy
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval((int) x - 10, (int) y - 10, 20, 20);
    }
}
