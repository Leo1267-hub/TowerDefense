package game;

import java.awt.*;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class Enemy {

    private double x, y;
    private int health = 100;
    private double speed = 1.5;
    private int pathIndex = 0;
    private boolean escaped = false;
    public static final int SIZE = 100;
    private static final int frame_width = 106;
    private static final int frame_height = 79;
    public final BufferedImage[] ORC_SPRITES = LoadSave.loadSprites(frame_width, frame_height, 7,
            LoadSave.ORC);

    private int animationTimer, currentFrame, ANIMATION_SPEED = 10;

    // constructor with default to watch the first point on the path
    public Enemy(Path path) {
        x = path.getPoint(0).x;
        y = path.getPoint(0).y;
    }

    // update movement of the enemy along the path
    public void update(Path path) {
        // once the enemy reaches the end of the path, it should stop moving
        if (pathIndex >= path.length()) {
            escaped = true;
            return;
        }

        // otherwise it should move towards the next point on the path
        Point target = path.getPoint(pathIndex);
        double dx = target.x - x;
        double dy = target.y - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        // if it close enough to the target point, it should move to the next point on
        // the path
        if (dist < speed) {
            pathIndex++;
            if (pathIndex >= path.length()) {
                escaped = true;
            }
        } else {
            // otherwise it should move towards the target point at a constant speed
            x += dx / dist * speed;
            y += dy / dist * speed;
        }
        int[] result = LoadSave.updateAnimation(animationTimer, ANIMATION_SPEED, currentFrame, ORC_SPRITES);
        currentFrame = result[0];
        animationTimer = result[1];
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

    public boolean hasEscaped() {
        return escaped;
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
        // g.setColor(Color.RED);
        // g.fillRect((int) x - SIZE / 2, (int) y - SIZE / 2, SIZE, SIZE);
        drawHealthBar(g);

        g.drawImage(ORC_SPRITES[currentFrame], (int) x - SIZE / 2, (int) y - SIZE / 2, SIZE, SIZE, null);
    }

    public void drawHealthBar(Graphics g) {
        // draw the health bar above the enemy
        g.setColor(Color.red);
        g.fillRect((int) x - SIZE / 2, (int) (y - SIZE / 2) - 20, SIZE, 10);
        // draw another red rectangle on top of the green one to show the remaining
        // health
        g.setColor(Color.green);
        g.fillRect((int) x - SIZE / 2, (int) (y - SIZE / 2) - 20, (int) (health / 100.0 * SIZE), 10);
    }

    // private BufferedImage[] loadOrcSprites() {
    // BufferedImage[] sprites = new BufferedImage[7];
    // for (int i = 0; i < 7; i++) {
    // sprites[i] = LoadSave.getSpriteAtlas(LoadSave.ORC).getSubimage(i *
    // frame_width, 0, frame_width,
    // frame_height);
    // }
    // return sprites;
    // }

    // private void updateAnimation() {
    // // increment the animation timer and update the current frame of the enemy's
    // // animation
    // animationTimer++;
    // if (animationTimer >= ANIMATION_SPEED) {
    // animationTimer = 0;
    // currentFrame = (currentFrame + 1) % ORC_SPRITES.length;
    // }
    // }

}
