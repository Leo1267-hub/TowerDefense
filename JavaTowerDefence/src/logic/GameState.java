package logic;

import game.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GameState {

    private ArrayList<Enemy> enemies;
    private ArrayList<Tower> towers;
    private ArrayList<Projectile> projectiles;

    private Path path;
    private Level level;
    private BufferedImage mapImage;

    public GameState() {

        // Load the map image
        try {
            mapImage = ImageIO.read(getClass().getResourceAsStream("/resources/TowerDefenseMap.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // enemies currently alive in the game
        enemies = new ArrayList<>();
        // towers currently placed by the player
        towers = new ArrayList<>();
        // bullets currently active in the game
        projectiles = new ArrayList<>();

        // Each object:
        // Updates itself
        // Draws itself
        // Knows how to interact with other objects

        // Creates the enemy path
        // Where enemies go
        path = new Path();
        // Creates the level logic
        // How enemies spawn
        level = new Level();
    }

    // updates the entire game state
    // called once per frame (~60 times per second)
    public void update() {

        // level class decides to spawn enemies or not
        level.spawnEnemies(enemies, path);

        // update the enemy positions
        for (Enemy e : enemies)
            e.update(path);
        // updates towers (check if enemies in range, fire projectiles)
        for (Tower t : towers)
            t.update(enemies, projectiles);
        // updates all projectiles (move, check for hits)
        for (Projectile p : projectiles)
            p.update();

        // remove dead enemies and inactive projectiles
        enemies.removeIf(e -> e.isDead());
        projectiles.removeIf(p -> !p.isAlive());
    }

    // draws the entire game state
    public void draw(Graphics g) {
        // draw the map background
        if (mapImage != null) {
            g.drawImage(mapImage, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        }

        // draw the path
        path.draw(g);
        level.draw(g);
        for (Enemy e : enemies)
            e.draw(g);
        for (Tower t : towers)
            t.draw(g);
        for (Projectile p : projectiles)
            p.draw(g);
    }

    // INPUT HANDLING

    // places a new tower at the specified coordinates
    public void placeTower(int x, int y) {
        towers.add(new Tower(x, y));
    }
}
