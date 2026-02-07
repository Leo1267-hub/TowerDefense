package game;

import java.util.ArrayList;

public class Level {

    private int timer = 0;
    private int spawned = 0;
    private int spawnTime = 60;
    private int maxEnemies = 15; // maximum number of enemies to spawn

    public void spawnEnemies(ArrayList<Enemy> enemies, Path path) {
        timer++;

        // spawns a new enemy every 120 frames (2 seconds at 60 FPS) until 10 enemies
        // have been spawned
        if (timer % spawnTime == 0 && spawned < maxEnemies) {
            enemies.add(new Enemy(path));
            spawned++;
        }
    }
}
