package utilz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LoadSave {

    public static final String MAP = "TowerDefenseMap.png";
    public static final String ORC = "orc.png";
    public static final String PROJECTILE = "projectile.png";
    public static final String EXPLOSION = "explosion.png";
    public static final String TOWER = "tower.png";

    public static BufferedImage getSpriteAtlas(String fileName) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(LoadSave.class.getResourceAsStream("/resources/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return img;
    }

    public static BufferedImage[] loadSprites(int frame_width, int frame_height, int num_frames, String fileName) {
        BufferedImage[] sprites = new BufferedImage[num_frames];
        for (int i = 0; i < num_frames; i++) {
            sprites[i] = LoadSave.getSpriteAtlas(fileName).getSubimage(i * frame_width, 0, frame_width,
                    frame_height);
        }
        return sprites;
    }

    public static int[] updateAnimation(int animationTimer, int ANIMATION_SPEED, int currentFrame,
            BufferedImage[] SPRITES) {
        // increment the animation timer and update the current frame of the enemy's
        // animation
        animationTimer++;
        if (animationTimer >= ANIMATION_SPEED) {
            animationTimer = 0;
            currentFrame = (currentFrame + 1) % SPRITES.length;
        }
        return new int[] { currentFrame, animationTimer };
    }

}
