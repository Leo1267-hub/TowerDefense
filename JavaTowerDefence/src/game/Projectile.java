package game;

import java.awt.*;

public class Projectile {
    // each projectile has:
    // target 1 enemy
    // moves independently toward that enemy
    // Disappears after hitting

    // projectile position
    private double x, y;
    private Enemy target;
    private boolean alive = true;
    private double speed = 10.0;

    // creates a new projectile from tower position and target enemy
    public Projectile(int x, int y, Enemy target) {
        this.x = x;
        this.y = y;
        this.target = target;
    }

    // updates the projectile
    public void update() {
        // set the alive to false (removes projectile) if target is dead
        if (target == null || target.isDead()) {
            alive = false;
            return;
        }

        // find the distance between the projectile and the target
        double dx = target.getX() - x;
        double dy = target.getY() - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (collide(target, this)) {
            target.damage(25); // deal 25 damage to the enemy
            alive = false; // remove the projectile
        } else {
            // moves the projectile toward the target
            // converts the dx and dy into a unit vector and then multiplies by speed
            x += dx / dist * speed;
            y += dy / dist * speed;
        }
    }

    // checks if the projectile is still alive (hasn't hit the target)
    public boolean isAlive() {
        return alive;
    }

    // draws the projectile on the screen
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval((int) x - 4, (int) y - 4, 8, 8);
    }

    // checking id the bullet inside the enemy hitbox
    public boolean collide(Enemy e, Projectile p) {
        double half = Enemy.SIZE / 2;
        if (e.getX() - half < p.x &&
                e.getX() + half > p.x &&
                e.getY() - half < p.y &&
                e.getY() + half > p.y) {
            return true;
        }
        return false;
    }
}
