package utilz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LoadSave {
    public static final String MAP = "TowerDefenseMap.png";
    public static final String ORC = "orc.png";
    public static final String PROJECTILE = "projectile.png";

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
}
