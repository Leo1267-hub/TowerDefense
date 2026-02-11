package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Level {

    private int timer = 0;
    private int spawned = 0;
    private static final int SPAWNTIME = 120;
    private static final int MAX_ENEMIES = 15; // maximum number of enemies to spawn

    // 12 rows x 20 columns - value 14 means tower can be placed on that tile
    public static final int[][] TOWER_TILES = {
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 0 },
            { 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 0 },
            { 0, 0, 0, 0, 0, 14, 0, 14, 0, 14, 0, 14, 0, 14, 0, 0, 0, 0, 14, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 0, 14, 0, 14, 0, 14, 0, 14, 0 },
            { 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 0, 14, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 14, 0, 14, 0, 14, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
    };

    public void spawnEnemies(ArrayList<Enemy> enemies, Path path) {
        timer++;

        // spawns a new enemy every 120 frames (2 seconds at 60 FPS) until 15 enemies
        // have been spawned
        if (timer % SPAWNTIME == 0 && spawned < MAX_ENEMIES) {
            enemies.add(new Enemy(path));
            spawned++;
        }
    }

    // Check if a tower can be placed at the given tile coordinates
    public static boolean canPlaceTower(int tileX, int tileY) {
        // check if it in bounds of the TOWER_TILES array
        if (tileY < 0 ||
                tileY >= TOWER_TILES.length ||
                tileX < 0 ||
                tileX >= TOWER_TILES[0].length) {
            return false;
        }
        if (TOWER_TILES[tileY][tileX] != 14) {
            return false;
        }
        TOWER_TILES[tileY][tileX] = 1; // mark tile as occupied
        return true;
    }

    public void draw(Graphics g) {
        Color c = new Color(1f, 1f, 1f, .15f);
        g.setColor(c);
        for (int y = 0; y < TOWER_TILES.length; y++) {
            for (int x = 0; x < TOWER_TILES[y].length; x++) {
                if (TOWER_TILES[y][x] == 14) {
                    g.fillRect(x * Path.TILE_SIZE, y * Path.TILE_SIZE, Path.TILE_SIZE,
                            Path.TILE_SIZE);
                }
            }
        }
    }
}
