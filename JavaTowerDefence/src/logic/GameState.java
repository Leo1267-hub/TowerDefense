package logic;

import game.*;
import ui.EndGameHUD;
import ui.MenuHUD;
import ui.GameHUD;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class GameState {

    private enum ScreenState {
        MENU,
        PLAYING,
        GAME_OVER,
        WIN
    }

    public static final int MAX_HEALTH = 100;
    public static final int STARTING_MONEY = 200;
    public static final int TOWER_COST = 50;
    public static final int ENEMY_KILL_REWARD = 15;
    public static final int ENEMY_ESCAPE_DAMAGE = 10;

    private ArrayList<Enemy> enemies;
    private ArrayList<Tower> towers;
    private ArrayList<Projectile> projectiles;

    private Path path;
    private Level level;
    private BufferedImage mapImage;
    public static int health;
    public static int money;

    public static int moneyPopupTimer = 0;
    private ScreenState screenState = ScreenState.MENU;
    private final MenuHUD menuHUD = new MenuHUD();
    private final EndGameHUD endGameHUD = new EndGameHUD();

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

        // Creates the enemy path
        // Where enemies go
        path = new Path();
        // Creates the level logic
        // How enemies spawn
        level = new Level();

        health = MAX_HEALTH;
        money = STARTING_MONEY;
    }

    // updates the entire game state
    // called once per frame (~60 times per second)
    public void update() {
        if (screenState != ScreenState.PLAYING) {
            return;
        }

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

        // Handle dead/escaped enemies and update player stats.
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        for (Enemy e : enemies) {
            if (e.isDead()) {
                money += ENEMY_KILL_REWARD;
                enemiesToRemove.add(e);
                moneyPopupTimer = 45;

            } else if (e.hasEscaped()) {
                health = Math.max(0, health - ENEMY_ESCAPE_DAMAGE);
                enemiesToRemove.add(e);
            }
        }
        if (health <= 0) {
            screenState = ScreenState.GAME_OVER;
        }
        enemies.removeAll(enemiesToRemove);

        if (screenState == ScreenState.PLAYING && level.isWaveFinished() && enemies.isEmpty()) {
            screenState = ScreenState.WIN;
        }

        if (moneyPopupTimer > 0) {
            moneyPopupTimer--;
        }

        // remove inactive projectiles
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

        towers.sort(Comparator.comparingInt(Tower::getRow));

        for (Tower t : towers)
            t.draw(g);

        for (Projectile p : projectiles)
            p.draw(g);

        if (screenState == ScreenState.PLAYING || screenState == ScreenState.WIN
                || screenState == ScreenState.GAME_OVER) {
            GameHUD.drawHud((Graphics2D) g);
        }

        if (screenState == ScreenState.MENU) {
            menuHUD.draw((Graphics2D) g);
        } else if (screenState == ScreenState.GAME_OVER) {
            endGameHUD.draw((Graphics2D) g, false);
        } else if (screenState == ScreenState.WIN) {
            endGameHUD.draw((Graphics2D) g, true);
        }
    }

    // places a new tower at the specified coordinates if on a valid tile
    public void placeTower(int x, int y) {
        if (money < TOWER_COST) {
            return;
        }

        // Convert pixel coordinates to tile coordinates
        int tileX = x / Path.TILE_SIZE;
        int tileY = y / Path.TILE_SIZE;

        // Check if tower can be placed on this tile
        if (Level.canPlaceTower(tileX, tileY)) {
            // Snap tower position to center of the tile
            int centerX = tileX * Path.TILE_SIZE + Path.TILE_SIZE;
            int centerY = tileY * Path.TILE_SIZE + Path.TILE_SIZE / 2;
            towers.add(new Tower(centerX, centerY));
            money -= TOWER_COST;
        }
    }

    public void handleMouseClick(int x, int y) {
        if (screenState == ScreenState.MENU) {
            if (menuHUD.isStartClicked(x, y)) {
                startGame();
            } else if (menuHUD.isExitClicked(x, y)) {
                System.exit(0);
            }
            return;
        }

        if (screenState == ScreenState.GAME_OVER || screenState == ScreenState.WIN) {
            if (endGameHUD.isRestartClicked(x, y)) {
                restartGame();
            }
            return;
        }

        placeTower(x, y);
    }

    public void handleMouseMove(int x, int y) {
        if (screenState == ScreenState.MENU) {
            menuHUD.updateHover(x, y);
            endGameHUD.clearHover();
        } else if (screenState == ScreenState.GAME_OVER || screenState == ScreenState.WIN) {
            endGameHUD.updateHover(x, y);
            menuHUD.clearHover();
        } else {
            menuHUD.clearHover();
            endGameHUD.clearHover();
        }
    }

    private void startGame() {
        screenState = ScreenState.PLAYING;
        menuHUD.clearHover();
    }

    private void restartGame() {
        enemies.clear();
        towers.clear();
        projectiles.clear();

        level = new Level();
        path = new Path();
        resetTowerTiles();

        health = MAX_HEALTH;
        money = STARTING_MONEY;
        moneyPopupTimer = 0;
        endGameHUD.clearHover();
        screenState = ScreenState.PLAYING;
    }

    private void resetTowerTiles() {
        for (int y = 0; y < Level.TOWER_TILES.length; y++) {
            for (int x = 0; x < Level.TOWER_TILES[y].length; x++) {
                if (Level.TOWER_TILES[y][x] != 0) {
                    Level.TOWER_TILES[y][x] = 14;
                }
            }
        }
    }

}
