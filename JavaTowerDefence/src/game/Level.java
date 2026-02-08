package game;

import java.util.ArrayList;

public class Level {

    private int timer = 0;
    private int spawned = 0;
    private static final int SPAWNTIME = 120;
    private static final int MAX_ENEMIES = 15; // maximum number of enemies to spawn

    public void spawnEnemies(ArrayList<Enemy> enemies, Path path) {
        timer++;

        // spawns a new enemy every 120 frames (2 seconds at 60 FPS) until 15 enemies
        // have been spawned
        if (timer % SPAWNTIME == 0 && spawned < MAX_ENEMIES) {
            enemies.add(new Enemy(path));
            spawned++;
        }
    }
}
