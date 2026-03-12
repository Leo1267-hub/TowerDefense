package game;

import java.awt.*;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class Projectile {
    // each projectile has:
    // target 1 enemy
    // moves independently toward that enemy
    // Disappears after hitting

    // projectile position
    private double x, y;
    private Enemy target;
    private boolean alive = true, explosion = false;
    private double speed = 10.0;

    private static final int frame_width = 32;
    private static final int frame_height = 26;

    private static final int DAMAGE = 25;

    private static final int explosion_frame_width = 78;
    private static final int explosion_frame_height = 84;
    public final BufferedImage[] EXPLOSION_SPRITES = LoadSave.loadSprites(explosion_frame_width,
            explosion_frame_height, 4, LoadSave.EXPLOSION);
    private int animationTimer = 0, currentFrame = 0, ANIMATION_SPEED = 5;

    // creates a new projectile from tower position and target enemy
    public Projectile(int x, int y, Enemy target) {
        this.x = x;
        this.y = y;
        this.target = target;
    }

    // updates the projectile
    public void update() {
        // If explosion is playing, continue the animation
        if (explosion) {
            int[] result = LoadSave.updateAnimation(animationTimer, ANIMATION_SPEED, currentFrame,
                    EXPLOSION_SPRITES);
            currentFrame = result[0];
            animationTimer = result[1];
            if (currentFrame == EXPLOSION_SPRITES.length - 1) {
                alive = false; // remove the projectile after the explosion animation finishes
            }
            return;
        }

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
            target.damage(DAMAGE); // deal damage to the enemy
            // if its collides with the target, it should play the explosion animation and
            // then disappear
            currentFrame = 0;
            explosion = true;
            // Don't set alive = false here - let the explosion animation play first
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
        if (!explosion) {
            g.drawImage(LoadSave.getSpriteAtlas(LoadSave.PROJECTILE), (int) x - 4, (int) y - 4, frame_width,
                    frame_height,
                    null);
        } else {
            g.drawImage(EXPLOSION_SPRITES[currentFrame], (int) x - 4, (int) y - 4, explosion_frame_width,
                    explosion_frame_height,
                    null);
        }

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
