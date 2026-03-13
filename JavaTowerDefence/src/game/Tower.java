package game;

import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import utilz.LoadSave;

public class Tower {

    // position of the tower
    private int x, y;
    // range of the tower
    private int range = 250;
    // track time until the next shot can be fired
    private int cooldown = 60;
    private boolean shooting = false;
    private boolean hasFired = false; // track if projectile was created this animation
    private Enemy targetEnemy = null; // track the target for delayed shot
    private static final int WIDTH = 128;
    private static final int HEIGHT = 144;
    private final BufferedImage[] TOWER_SPRITES = LoadSave.loadSprites(WIDTH, HEIGHT, 19, LoadSave.TOWER);
    private int animationTimer = 0, currentFrame = 0, ANIMATION_SPEED = 4;

    // constructor to create a tower at a specific position
    public Tower(int x, int y) {
        this.x = x;
        this.y = y;

    }

    // updates the tower once per frame
    public void update(ArrayList<Enemy> enemies, ArrayList<Projectile> projectiles) {

        // Update animation every frame
        if (shooting) {
            int[] result = LoadSave.updateAnimation(animationTimer, ANIMATION_SPEED, currentFrame, TOWER_SPRITES);
            currentFrame = result[0];
            animationTimer = result[1];

            // Fire projectile at frame 7
            if (currentFrame == 7 && !hasFired && targetEnemy != null && !targetEnemy.isDead()) {
                projectiles.add(new Projectile(x, y - 80, targetEnemy));
                hasFired = true;
            }

            if (currentFrame == TOWER_SPRITES.length - 1) {
                shooting = false; // stop shooting animation after it finishes
                currentFrame = 0; // reset to idle frame
                hasFired = false;
                targetEnemy = null;
            }
        }

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

            // if it is, it starts the shooting animation and resets the cooldown timer
            if (dist <= range) {
                targetEnemy = e; // store the target
                shooting = true; // start shooting animation
                cooldown = 60;
                break;
            }
        }
    }

    // draw the tower
    public void draw(Graphics g) {
        g.drawImage(TOWER_SPRITES[currentFrame], (x - WIDTH / 2), (y - HEIGHT / 2) - 15, WIDTH - 5, HEIGHT - 5, null);
    }
}

// threads VEERY IMPORTANT FOR EXAM, it useful because it creates new path for
// the code to run, so you can have multiple things happening at once, like the
// game loop and the animation of the tower, without one blocking the other. In
// this code, we don't use threads, but we use a timer to update the game state
// and redraw the screen every 16 milliseconds, which gives us a smooth
// animation and responsive gameplay.§